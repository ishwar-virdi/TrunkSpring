package com.trunk.demo.repository;

public interface TokenRepository {
    public String getToken();

    public boolean isEquals(String inputToken);


}
