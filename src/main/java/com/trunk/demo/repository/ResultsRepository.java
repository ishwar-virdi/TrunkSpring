package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResultsRepository extends MongoRepository<ReconcileResult, String> {
    public List<ReconcileResult> findByUid(String uid,Sort sort);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndReconcileDate(String uid,int date);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String uid,int from,int to);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndPercentage(String uid,int percentage);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndPercentageGreaterThanEqual(String uid,int percentage);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndPercentageLessThanEqual(String uid,int percentage);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public List<ReconcileResult> findByUidAndPercentageBetween(String uid,int lessThanValue,int largerThanValue);

    @Query(fields = "{'_id':0,'reconcileDate':1,'reconcileTime':1,'startDate':1,'endDate':1,'percentage':1}")
    public Optional<ReconcileResult> findById(String _id);
}
