package com.pepsiwyl.controller;

import com.pepsiwyl.pojo.User;
import com.pepsiwyl.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:33
 */

@RestController
public class TestController {

    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    public TestController(RedisTemplate<String, Object> redisTemplate, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @GetMapping("/redisTest")
    public String redisTest() {
        redisTemplate.opsForValue().set("user", new User().setId(1L).setName("pepsi-wyl").setAge(10).setEmail("12321"));
        return Objects.requireNonNull(redisTemplate.opsForValue().get("user")).toString();
    }

    @GetMapping("/mybatisTest")
    public String mybatisTest() {
        List<User> users = userService.getUsers();
        return users.toString();
    }

}

