package com.trunk.demo.controller;


import com.trunk.demo.service.mongo.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/results")
    public String Results() {
        return resultService.retrieveResults();
    }

    @RequestMapping("/api/v1/seedResults")
    public String saveSeedData() {
        return resultService.saveSeedData();
    }
}
