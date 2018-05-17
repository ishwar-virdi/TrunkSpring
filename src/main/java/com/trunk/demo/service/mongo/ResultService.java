package com.trunk.demo.service.mongo;

import javax.servlet.http.HttpSession;

public interface ResultService {
    public String retrieveResults(HttpSession session);
    public String saveSeedData(HttpSession session);
}
