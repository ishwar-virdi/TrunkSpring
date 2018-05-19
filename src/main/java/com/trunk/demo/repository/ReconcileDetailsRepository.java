package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReconcileDetailsRepository extends MongoRepository<ReconcileDetail, String> {
    public Optional<ReconcileDetail> findById(String id);
}
