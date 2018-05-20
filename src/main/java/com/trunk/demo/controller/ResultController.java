package com.trunk.demo.controller;

import com.trunk.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/results")
    public String Results(@RequestParam String page, HttpSession session) {
        try{
            int pageIndex = Integer.parseInt(page);
            return resultService.retrieveResults(session,pageIndex);
        }catch (Exception e){
            return "";
        }
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/seedResults")
//    public String Results(HttpSession session) {
//        System.out.println("aaa");
//        return "a";
//    }
    @RequestMapping(method = RequestMethod.GET,value="/api/v1/seedResults")
    public String saveSeedData(HttpSession session) {
        System.out.println("aa");
        return resultService.saveSeedData(session);
    }
}
