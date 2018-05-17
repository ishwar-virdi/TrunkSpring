package com.trunk.demo.vo;

import com.trunk.demo.model.mongo.User;

public class LoginModelVO extends User {

    private String token;

    public LoginModelVO(String username, String password, String token) {
        super(username, password);
        this.token = token;
    }

    public LoginModelVO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
