package com.trunk.demo.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trunk.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/search")
    public String SearchResult(@RequestBody String param, HttpSession session) {
        String json = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject params = new JsonParser().parse(param).getAsJsonObject();
        Object userSession = session.getAttribute(session.getId());
        String jsonPages = params.get("page").toString();
        String jsonValue = params.get("value").toString();
        String page = jsonPages.substring(1,jsonPages.length()-1);
        String value = jsonValue.substring(1,jsonValue.length()-1);
        if(userSession == null){
            jsonObject.addProperty("result","expired");
            return jsonObject.toString();
        }
        switch (page){
            case "result":
                json = searchService.resultSearch(userSession.toString(),value);
                break;
            case "detail":
                json = searchService.resultSearch(userSession.toString(),value);
                break;
            default:
                json = "fail";
                break;
        }
        if("fail".equals(json)){
            jsonObject.addProperty("result","fail");
            json = jsonObject.toString();
        }
        return json;
    }
}
