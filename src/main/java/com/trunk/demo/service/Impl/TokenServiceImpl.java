package com.trunk.demo.service.Impl;

import com.google.gson.JsonObject;
import com.trunk.demo.model.Token;
import com.trunk.demo.repository.TokenRepository;
import com.trunk.demo.service.TokenService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public String generateToken() {
        Token token = new Token(tokenRepository.getToken());
        JsonObject json = new JsonObject();
        json.addProperty("token", token.getToken());
        return json.toString();
    }

}
