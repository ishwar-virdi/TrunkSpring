package com.trunk.demo.controller;

import com.trunk.demo.service.mongo.UserManager;
import com.trunk.demo.vo.LoginModelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/login")
    public String loginValidation(@RequestBody LoginModelVO loginModelVO, HttpSession session) {
        return userManager.loginValidator(loginModelVO, session);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/register")
    public String registerUser(@RequestBody LoginModelVO loginModelVO, HttpSession session) {
        return userManager.register(loginModelVO,session);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/userLogin")
    public String userIsLogin(HttpSession session) {
        return userManager.userIsLogin(session);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/userLogout")
    public String logout(HttpSession session) {
        return userManager.logOut(session);
    }
}
