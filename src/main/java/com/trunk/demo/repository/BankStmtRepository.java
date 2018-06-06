package com.trunk.demo.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.BankStmt;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankStmtRepository extends MongoRepository<BankStmt, Integer> {
    List<BankStmt> findAllByDateBetween(Date startDate, Date endDate,Sort sort);

	@Query("{ $and : [ { 'date' : { $gte : ?0 } }, { 'date' : { $lte : ?1 } } ] }")
    List<BankStmt> findAllBetweenDates(Date startDate, Date endDate);

}
