package com.trunk.demo.controller;

import com.trunk.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/api/v1/token")
    public String token(HttpServletRequest request) {
        return tokenService.generateToken();
    }
}
