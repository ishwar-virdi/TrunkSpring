package com.trunk.demo.service.Impl;

import com.google.gson.JsonObject;
import com.trunk.demo.bo.TokenBO;
import com.trunk.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenBO tokenBO;

    @Override
    public String generateToken() {
        JsonObject json = new JsonObject();
        json.addProperty("token", tokenBO.generateToken());
        return json.toString();
    }

}
