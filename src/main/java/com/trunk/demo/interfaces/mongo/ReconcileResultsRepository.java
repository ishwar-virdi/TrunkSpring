package com.trunk.demo.interfaces.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trunk.demo.model.mongo.ReconcileResult;

public interface ReconcileResultsRepository extends MongoRepository<ReconcileResult, String>{
	
}
