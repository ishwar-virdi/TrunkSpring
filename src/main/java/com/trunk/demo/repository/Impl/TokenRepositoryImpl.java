package com.trunk.demo.repository.Impl;

import java.util.UUID;

import com.trunk.demo.model.Token;
import com.trunk.demo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component("tokenRepositoryImpl")
public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    private HttpSession session;

    private Token token;

    @Override
    public String getToken() {
        String random = UUID.randomUUID().toString();
        token = new Token(random);
        session.setAttribute(token.getName(), token.getToken());
        return token.getToken();
    }

    @Override
    public boolean isEquals(String inputToken) {
        token = new Token();
        Object sessionToken = session.getAttribute(token.getName());
        if (sessionToken != null && sessionToken.toString().equals(inputToken)) {
            this.removeToken();
            return true;
        }
        return false;
    }

    public void removeToken() {
        session.removeAttribute(token.getName());
    }
}
