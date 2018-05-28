package com.trunk.demo.controller;

import com.trunk.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

@RestController
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/results")
    public String Results(@RequestParam String page, HttpSession session) {
        try{
            int pageIndex = Integer.parseInt(page);
            return resultService.retrieveResults(session,pageIndex);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/api/v1/seedResults")
    public String saveSeedData(HttpSession session) throws ParseException {
        return resultService.saveSeedData(session);
    }
}
