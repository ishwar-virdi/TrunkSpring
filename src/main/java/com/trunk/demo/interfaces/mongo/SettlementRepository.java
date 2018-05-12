package com.trunk.demo.interfaces.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.SettlementStmt;

@Repository
public interface SettlementRepository extends MongoRepository<SettlementStmt, String> {
	SettlementStmt findBySettlementDateLike(String settlementDate);
}
