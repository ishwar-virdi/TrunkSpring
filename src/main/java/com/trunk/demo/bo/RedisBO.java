package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.List;
import java.util.Set;

public interface RedisBO {
    public void pushTransaction(int id, Object value);

    public void pushType(String type);

    public void pushFileName(String filename);

    public Object getTransaction(int id);

    public  Object getFileName();

    Object getTransactionDate(String id);

    void deleteSpecificValue(String value);

    public void deleteCache();

    public Object getType();

    public void pushTransactionDate(Set<String> sets);

    public void deleteObjectCache();
}
