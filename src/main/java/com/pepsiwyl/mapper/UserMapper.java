package com.pepsiwyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pepsiwyl.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:16
 */

@Repository(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {

    List<User> getUsers();

    User getUserById(Integer id);

    int addUser(User user);

    int deleteUserById(Integer id);

    int updateUserById(User user);

}
