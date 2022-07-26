package com.pepsiwyl.cache;

import com.pepsiwyl.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 19:33
 */

public class RedisCache implements Cache {

    // mapper的namespace 放入缓存的ID
    private final String id;

    // 必须要存在一个以String id作为参数有参构造
    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    // 放入缓存
    @Override
    public void putObject(Object key, Object value) {
        ApplicationContextUtils.getRedisTemplate().opsForHash().put(this.id, keyToMD5String(key.toString()), value);

        // 设置缓存时间 尽量避免 缓存雪崩
        if (id.equals("com.pepsiwyl.mapper.UserMapper")) {
            ApplicationContextUtils.getRedisTemplate().expire(this.id, 1, TimeUnit.HOURS);
        }

    }

    // 取出缓存
    @Override
    public Object getObject(Object key) {
        return ApplicationContextUtils.getRedisTemplate().opsForHash().get(this.id, keyToMD5String(key.toString()));
    }

    // 根据key删除缓存  无效的方法
    @Override
    public Object removeObject(Object key) {
        return null;
    }

    // 清空缓存
    @Override
    public void clear() {
        ApplicationContextUtils.getRedisTemplate().delete(this.id);
    }

    // 计算缓存的数量
    @Override
    public int getSize() {
        return Math.toIntExact(ApplicationContextUtils.getRedisTemplate().opsForHash().size(this.id));
    }

    // MD5处理
    private String keyToMD5String(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
