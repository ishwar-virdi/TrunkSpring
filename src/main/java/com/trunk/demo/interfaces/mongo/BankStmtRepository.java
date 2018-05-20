package com.trunk.demo.interfaces.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.BankStmt;

import java.util.Date;
import java.util.List;

@Repository
public interface BankStmtRepository extends MongoRepository<BankStmt, String> {
    List<BankStmt> findByDateBetween(Date start, Date end);
}
