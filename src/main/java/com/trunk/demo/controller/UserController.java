package com.trunk.demo.controller;

import com.trunk.demo.model.viewModel.ViewLoginModel;
import com.trunk.demo.service.mongo.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Thread.sleep;

@RestController
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/login")
    public String loginValidation(@RequestBody ViewLoginModel viewLoginModel, HttpServletRequest request) {
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key + " " + value);
//        }
        return userManager.loginValidator(viewLoginModel);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/register")
	public String registerUser(@RequestBody ViewLoginModel viewLoginModel) {
		return userManager.register(viewLoginModel);
	}

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/userLogin")
    public String userIsLogin() {
        return userManager.userIsLogin();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/userLogout")
    public String logout() {
        return userManager.logOut();
    }
}
