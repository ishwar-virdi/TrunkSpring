package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultsRepository extends MongoRepository<ReconcileResult, String> {
    public List<ReconcileResult> findByUserId(String uid, Pageable page);

    public List<ReconcileResult> findByUserIdAndLastModifiedBetween(String uid,Date date,Date nextDate);

    public List<ReconcileResult> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(String uid,Date date,Date nextDate);

    public List<ReconcileResult> findByUserIdAndPercentage(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageGreaterThanEqual(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageLessThanEqual(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageBetween(String uid,int lessThanValue,int largerThanValue);

    public Optional<ReconcileResult> findById(String _id);

}
