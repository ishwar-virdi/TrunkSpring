package com.trunk.demo.repository;

public interface TokenRepository {
    public String generateToken();

    public boolean destroyToken();

    public Object getToken();
}
