package com.trunk.demo.service.Impl;

import com.google.gson.JsonObject;
import com.trunk.demo.repository.TokenRepository;
import com.trunk.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public String generateToken() {
        JsonObject json = new JsonObject();
        json.addProperty("token", tokenRepository.generateToken());
        return json.toString();
    }

}
