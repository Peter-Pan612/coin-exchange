package com.ce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: Peter
 * @description:
 * @date: 2024/1/22 21:28
 */
@RestController
public class UserInfoController {

    /**
     * 获取改用户的对象
     */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal){ //此处的principal由OAuth2.0框架自动注入
        //原理就是：利用Context概念，将授权用户放在线程里面，利用ThreadLocal来获取当前的用户对象
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }
}
