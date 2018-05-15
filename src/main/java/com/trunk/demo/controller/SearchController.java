package com.trunk.demo.controller;

import com.trunk.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/search")
    public String Results(@RequestParam("page") String page,@RequestParam("value") String value) {
        return searchService.search(page,value);
    }
}
