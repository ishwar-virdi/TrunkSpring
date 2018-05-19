package com.trunk.demo.controller;

import com.trunk.demo.service.DetailService;
import com.trunk.demo.service.mongo.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class DetailController {

    @Autowired
    private DetailService detailService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/details")
    public String Results(@RequestParam String page, HttpSession session) {
        System.out.println("aaa");
        return "a";
    }
}
