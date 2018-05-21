package com.trunk.demo.model;

public class Token {
    private final String name = "token";
    private String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "name=" + this.name + '\n' +
                "token='" + token + '\'' +
                '}';
    }
}