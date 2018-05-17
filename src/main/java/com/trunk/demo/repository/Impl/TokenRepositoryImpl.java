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


    @Override
    public String generateToken() {
        String random = UUID.randomUUID().toString();
        Token token = new Token(random);
        session.setAttribute(token.getName(), token.getToken());
        return token.getToken();
    }

    @Override
    public boolean destoryToken(){
        Token token = new Token();
        Object tokenSession = session.getAttribute(token.getName());
        if(tokenSession == null){
            return false;
        }else{
            session.removeAttribute(token.getName());
            return true;
        }
    }

    @Override
    public Object getToken(){
        Token token = new Token();
        return session.getAttribute(token.getName());
    }
}
