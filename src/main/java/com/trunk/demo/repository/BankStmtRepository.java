package com.trunk.demo.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.BankStmt;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankStmtRepository extends MongoRepository<BankStmt, String> {
    List<BankStmt> findAllByDateBetween(Date startDate, Date endDate,Sort sort);

    Optional<BankStmt> findAllById(String id);
}
