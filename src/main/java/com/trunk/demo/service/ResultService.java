package com.trunk.demo.service;

import javax.servlet.http.HttpSession;

public interface ResultService {
    public String retrieveResults(HttpSession session, int pageIndex);
    public String saveSeedData(HttpSession session);
}
