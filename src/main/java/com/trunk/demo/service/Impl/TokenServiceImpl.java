package com.trunk.demo.service.Impl;

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
        JSONObject json = new JSONObject();
        Token token = new Token(tokenRepository.getToken());
        try {
            json.put("token", token.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
