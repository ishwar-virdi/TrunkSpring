package com.trunk.demo.repository;

public interface TokenRepository {
    public String generateToken();

    public boolean destoryToken();

    public Object getToken();
}
