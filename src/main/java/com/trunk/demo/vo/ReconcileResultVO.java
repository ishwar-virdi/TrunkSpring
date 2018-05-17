package com.trunk.demo.vo;

import com.trunk.demo.Util.DateUtil;
import com.trunk.demo.model.mongo.ReconcileResult;

public class ReconcileResultVO {

    private String userId;
    private String reconcileDate;
    private String startDate;
    private String endDate;
    private int percentage;
    private int totalTransaction;
    private int reconciledTransaction;
    private int notReconciled;

    public ReconcileResultVO(String userId, String reconcileDate, String startDate, String endDate, int percentage) {
        this.userId = userId;
        this.reconcileDate = reconcileDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
    }

    public ReconcileResultVO(ReconcileResult result) {
        DateUtil dateUtil = new DateUtil();
        this.userId = result.getUserId();
        this.reconcileDate = dateUtil.convCurrentToString(result.getReconcileDate());
        this.startDate =  dateUtil.convSettleToString(result.getStartDate());
        this.endDate = dateUtil.convSettleToString(result.getEndDate());
        this.percentage = result.getPercentage();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReconcileDate() {
        return reconcileDate;
    }

    public void setReconcileDate(String reconcileDate) {
        this.reconcileDate = reconcileDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(int totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public int getReconciledTransaction() {
        return reconciledTransaction;
    }

    public void setReconciledTransaction(int reconciledTransaction) {
        this.reconciledTransaction = reconciledTransaction;
    }

    public int getNotReconciled() {
        return notReconciled;
    }

    public void setNotReconciled(int notReconciled) {
        this.notReconciled = notReconciled;
    }
}
