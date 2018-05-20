package com.trunk.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.BankStmt;

@Repository
public interface BankStmtRepository extends MongoRepository<BankStmt, String> {

}