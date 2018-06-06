package com.trunk.demo.bo.Impl;

import com.trunk.demo.bo.TokenBO;
import com.trunk.demo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenBOImpl implements TokenBO {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public String generateToken() {
        return tokenRepository.generateToken();
    }

    @Override
    public boolean destroyToken() {
        return tokenRepository.destroyToken();
    }

    @Override
    public Object getToken() {
        return tokenRepository.getToken();
    }
}
