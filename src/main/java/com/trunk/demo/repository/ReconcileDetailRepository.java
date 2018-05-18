package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileDetail;
import com.trunk.demo.model.mongo.ReconcileDetailItem;
import com.trunk.demo.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ReconcileDetailRepository extends MongoRepository<ReconcileDetail, String> {
}
