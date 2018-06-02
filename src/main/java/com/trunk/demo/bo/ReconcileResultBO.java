package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReconcileResultBO {
    public List<ReconcileResult> findByUserId(String uid, Pageable page);

    public List<ReconcileResult> findByUserIdAndLastModifiedBetween(String uid, Date date, Date nextDate);

    public List<ReconcileResult> findByUserIdAndPercentage(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageGreaterThanEqual(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageLessThanEqual(String uid,int percentage);

    public List<ReconcileResult> findByUserIdAndPercentageBetween(String uid,int lessThanValue,int largerThanValue);

    public ReconcileResult findById(String _id);

    public void save(ReconcileResult reconcileResult);

    void updateIncreaseIsReconcile(int count, ReconcileResult result);

    void updateIncreaseNotReconcile(int count, ReconcileResult result);
}
