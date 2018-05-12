package com.trunk.demo.interfaces.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.BankStmt;

@Repository
public interface BankStmtRepository extends MongoRepository<BankStmt, String> {
	@Query("{ 'Date' : {$regex:?0 }}")
	List<BankStmt> findAllByDateLike(String date);
}
