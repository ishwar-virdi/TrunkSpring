package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReconcileResultBO {
    List<ReconcileResult> findAllByUserId(String uid);

    public List<ReconcileResult> findByUserId(String uid, Pageable page);

    public List<ReconcileResult> findByUserIdAndLastModifiedBetween(String uid, Date date, Date nextDate);

    public List<ReconcileResult> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(String uid,Date startDate,Date endDate);

    public ReconcileResult findById(String _id);

    public void save(ReconcileResult reconcileResult);

    void updateIncreaseIsReconcile(int count, ReconcileResult result);

    void updateIncreaseNotReconcile(int count, ReconcileResult result);
}
