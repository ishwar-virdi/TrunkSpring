package com.trunk.demo.controller;

import com.trunk.demo.Util.BCryptText;
import com.trunk.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptText bCryptText;
    @RequestMapping("/api/v1/token")
    public String token(HttpServletRequest request) {
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key + " " + value);
//        }
        return tokenService.generateToken();
    }
}
