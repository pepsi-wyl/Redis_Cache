package com.pepsiwyl.service.impl;

import com.pepsiwyl.mapper.UserMapper;
import com.pepsiwyl.pojo.User;
import com.pepsiwyl.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:20
 */

// 配置事务
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.REPEATABLE_READ,
        timeout = 10,
        readOnly = false
)
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user) > 0;
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return userMapper.deleteUserById(id) > 0;
    }

    @Override
    public boolean updateUserById(User user) {
        return userMapper.updateUserById(user) > 0;
    }

}
