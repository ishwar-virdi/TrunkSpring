package com.trunk.demo.model.viewModel;

import com.trunk.demo.model.mongo.User;

public class ViewLoginModel extends User {

    private String token;

    public ViewLoginModel(String username, String password, String token) {
        super(username, password);
        this.token = token;
    }

    public ViewLoginModel() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
