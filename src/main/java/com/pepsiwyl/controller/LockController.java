package com.pepsiwyl.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author by pepsi-wyl
 * @date 2022-07-25 20:18
 */

// ab -n 1000 -c 100 http://192.168.131.1:8080/testLock
// ab -n 1000 -c 100 http://192.168.131.1:8080/lock
// ab -n 1000 -c 100 http://192.168.131.1:8080/tryLock
// ab -n 1000 -c 100 http://192.168.131.1:8080/tryLockWithBlock

// 分布式锁
@Slf4j
@RestController
public class LockController {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedissonClient redissonClient;

    public LockController(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }


    @SneakyThrows
    @GetMapping("/testLock")
    public String testLock() {

        // UUID防误删
        String uuid = UUID.randomUUID().toString();

        // 获取锁，setnx 设置过期时间
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 1, TimeUnit.SECONDS);

        // 获取锁成功
        if (Boolean.TRUE.equals(lock)) {

            if (StringUtils.isEmpty(redisTemplate.opsForValue().get("num"))) redisTemplate.opsForValue().set("num", 0);
            redisTemplate.opsForValue().increment("num");

            // LUA脚本 释放锁
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), List.of("lock"), uuid);

            return "OK~";
        } else {

            // 获取锁失败、每隔0.1秒再获取
            Thread.sleep(100);
            testLock();

            return "No OK~";
        }

    }


    /**
     * 没获取到锁的线程阻塞等待获取锁
     */
    @GetMapping("/lock")
    public String lock() {
        log.info("进入了测试方法～");
        RLock lock = null;
        try {
            lock = redissonClient.getLock("lock");
            lock.lock();
            log.info("获取到锁~");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 如果当前线程保持锁定则解锁
            if (null != lock && lock.isHeldByCurrentThread()) lock.unlock();
        }
        return "OK~";
    }

    /**
     * 没获取到锁的线程直接返回锁状态
     */
    @GetMapping("tryLock")
    public String tryLock() {
        log.info("进入了测试方法～");
        RLock lock = null;
        try {
            lock = redissonClient.getLock("lock");
            if (lock.tryLock()) {
                log.info("获取到锁~");
                Thread.sleep(6000);
            } else log.error("获取锁失败~");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 如果当前线程保持锁定则解锁
            if (null != lock && lock.isHeldByCurrentThread()) lock.unlock();
        }
        return "OK~";
    }

    /**
     * 没获取到锁的线程尝试获取锁
     */
    @GetMapping("tryLockWithBlock")
    public String tryLockWithBlock() {
        log.info("进入了测试方法～");
        RLock lock = null;
        try {

            // 非公平锁,随机取一个等待中的线程分配锁
            lock = redissonClient.getLock("lock");
            // 公平锁,按照先后顺序依次分配锁
            // lock=redissonClient.getFairLock("lock");

            //最多等待锁3秒，5秒后强制解锁
            if (lock.tryLock(3, 5, TimeUnit.SECONDS)) {
                log.info("获取到锁~");
                Thread.sleep(1000);
            } else log.error("获取锁失败~");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 如果当前线程保持锁定则解锁
            if (null != lock && lock.isHeldByCurrentThread()) lock.unlock();
        }
        return "OK~";
    }


}
