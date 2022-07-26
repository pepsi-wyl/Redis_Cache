package com.pepsiwyl;

import com.pepsiwyl.pojo.User;
import com.pepsiwyl.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@SpringBootTest(classes = RedisCacheApplication.class)
@RunWith(SpringRunner.class)
class RedisCacheApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void MybatisTest() {
        System.out.println(userService.getUsers());
    }

    @Test
    public void RedisTest() {
        redisTemplate.opsForValue().set("user", new User().setId(1L).setName("pepsi-wyl").setAge(10).setEmail("12321"));
        System.out.println(Objects.requireNonNull(redisTemplate.opsForValue().get("user")).toString());
    }

    @Test
    public void CacheTest() {
        System.out.println(userService.getUsers());
        System.out.println(userService.getUserById(10));
//        System.out.println(userService.addUser(new User().setAge(21).setName("pepsi-wyl").setEmail("2322535585@qq.com")));
//        System.out.println(userService.deleteUserById(8));
//        System.out.println(userService.updateUserById(new User().setId(5L).setName("pepsi-wyl").setAge(10).setEmail("12321")));
    }

}
