package com.trunk.demo.service;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

public interface ResultService {
    public String retrieveResults(HttpSession session, int pageIndex);
    public String resultSearch(String userId, String page, String value);
    public String saveSeedData(HttpSession session) throws ParseException;
}
