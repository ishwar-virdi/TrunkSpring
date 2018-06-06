package com.trunk.demo.bo.Impl;

import com.trunk.demo.bo.ReconcileResultBO;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ReconcileResultBOImpl implements ReconcileResultBO {

    @Autowired
    private ResultsRepository resultsRepository;

    @Override
    public List<ReconcileResult> findAllByUserId(String uid){
        return resultsRepository.findAllByUserId(uid);
    }

    @Override
    public List<ReconcileResult> findByUserId(String uid, Pageable page){
        return resultsRepository.findByUserId(uid, page);
    }

    @Override
    public List<ReconcileResult> findByUserIdAndLastModifiedBetween(String uid, Date date, Date nextDate){
        return resultsRepository.findByUserIdAndLastModifiedBetween(uid, date, nextDate);
    }


    @Override
    public List<ReconcileResult> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(String uid, Date startDate, Date endDate) {
        return resultsRepository.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(uid, startDate, endDate);
    }

    @Override
    public ReconcileResult findById(String _id){
        Optional<ReconcileResult> reconcile = resultsRepository.findById(_id);
        if(reconcile.isPresent()){
            return reconcile.get();
        }
        return null;
    }

    @Override
    public void save(ReconcileResult result){
        result.setLastModified(new Date());
        resultsRepository.save(result);
    }

    @Override
    public void updateIncreaseIsReconcile(int increaseCount, ReconcileResult result){
        result.setIsReconciled(result.getIsReconciled() + increaseCount);
        result.setNotReconciled(result.getNotReconciled() - increaseCount);
        result.setLastModified(new Date());
        resultsRepository.save(result);
    }

    @Override
    public void updateIncreaseNotReconcile(int increaseCount, ReconcileResult result){
        result.setIsReconciled(result.getIsReconciled() - increaseCount);
        result.setNotReconciled(result.getNotReconciled() + increaseCount);
        result.setLastModified(new Date());
        resultsRepository.save(result);
    }
}
