package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ResultsRepository extends MongoRepository<ReconcileResult, String> {
    @Query(value = "{}", fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'dateRange':1,'percentage':1}")
    public List<ReconcileResult> findAll(Sort sort);
}
