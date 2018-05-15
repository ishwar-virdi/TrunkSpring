package com.trunk.demo.interfaces.mongo;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.SettlementStmt;

@Repository
public interface SettlementRepository extends MongoRepository<SettlementStmt, Long> {
	@Query("{ 'SettlementDate' : {$regex:?0 }}")
	List<SettlementStmt> findAllBySettlementDateLike(String SettlementDate);
}
