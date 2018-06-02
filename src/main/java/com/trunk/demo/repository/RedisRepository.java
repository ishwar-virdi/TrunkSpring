package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.Set;

public interface RedisRepository {
    public void pushObjectToHash(String h, String key, Object object);

    public void pushStringToHash(String h, String key, String value);

    public Object getObjectFromHash(String h, String key);

    public Object getStringFromHash(String h, String key);

    public void setExpireTime(String h, int seconds);

    public void setObjectExpireTime(String h,int seconds);

    public void deleteSpecificFromHash(String h, String value);

    public void deleteCache(String h);

    public void deleteObjectCache(String h);

}

