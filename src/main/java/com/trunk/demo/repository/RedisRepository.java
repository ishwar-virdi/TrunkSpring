package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

public interface RedisRepository {
    public void putSettlement(int id,SettlementStmt stmt);
    public void putBankStatement(int id,BankStmt stmt);
    public void putType(String type);
    public void putFileName(String filename);
    public void deleteCache();
    public Object getTransaction(int id);
    public Object getFileName();
    public Object getType();
}

