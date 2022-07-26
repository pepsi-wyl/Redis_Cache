package com.pepsiwyl.service;

import com.pepsiwyl.pojo.User;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:19
 */

public interface UserService {
    List<User> getUsers();

    User getUserById(Integer id);

    boolean addUser(User user);

    boolean deleteUserById(Integer id);

    boolean updateUserById(User user);
}
