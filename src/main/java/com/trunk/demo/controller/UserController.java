package com.trunk.demo.controller;

import com.trunk.demo.model.viewModel.ViewLoginModel;
import com.trunk.demo.service.mongo.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/login")
    public String loginValidation(@RequestBody ViewLoginModel viewLoginModel, HttpServletRequest request) {
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key + " " + value);
//        }

        return usersService.loginValidator(viewLoginModel);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/register")
	public String registerUser(@RequestBody ViewLoginModel viewLoginModel) {
		return usersService.register(viewLoginModel);
	}

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/userLogin")
    public String userIsLogin() {
        return usersService.userIsLogin();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/userLogout")
    public String logout() {
        return usersService.logOut();
    }
}
