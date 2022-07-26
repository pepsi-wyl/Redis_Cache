package com.pepsiwyl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author by pepsi-wyl
 * @date 2022-07-25 17:07
 */

@SuppressWarnings("all")

@RestController
public class SessionController {

    @GetMapping("/sessionTest")
    public String sessionTest(HttpSession httpSession) {

        // 获取Session中的数据 -------没有则创建 存在则遍历
        List<String> list = (List<String>) httpSession.getAttribute("list");
        if (list == null) list = new ArrayList<String>();
        else list.forEach(System.out::println);

        // 对Session中的数据进行修改
        list.add(UUID.randomUUID().toString());

       /**
        * 传统的session开发中，获取的是Session中的地址引用，需改一个即可
        * Redis分布式Session中，只是缓存的数据，相当于获取到了副本，而不是引用
        */
        // 将数据写回Session中
        httpSession.setAttribute("list", list);

        // 返回 集合大小 和 SessionID
        return "size: " + list.size() + "     " + "ID: " + httpSession.getId();
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        // Session失效
        httpSession.invalidate();
        return "OK~";
    }

}