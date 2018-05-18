package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "ReconcileResults")

public class ReconcileResult {
    @Id
    private String id;
    private String userId;
    private Date reconcileDate;
    private Date startDate;
    private Date endDate;
    private int percentage;
    private int totalTransaction;
    private int reconciledTransaction;
    private int notReconciled;

    public ReconcileResult(String userId, Date startDate, Date endDate, int percentage, int totalTransaction, int reconciledTransaction, int notReconciled) {
        this.userId = userId;
        this.reconcileDate = new Date();
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
        this.totalTransaction = totalTransaction;
        this.reconciledTransaction = reconciledTransaction;
        this.notReconciled = notReconciled;
    }

//        public ReconcileResult(String id, String userId, Date startDate, Date endDate, int percentage, int totalTransaction, int reconciledTransaction, int notReconciled) {
//        this.id = id;
//        this.userId = userId;
//        this.reconcileDate = new Date();
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.percentage = percentage;
//        this.totalTransaction = totalTransaction;
//        this.reconciledTransaction = reconciledTransaction;
//        this.notReconciled = notReconciled;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getReconcileDate() {
        return reconcileDate;
    }

    public void setReconcileDate(Date reconcileDate) {
        this.reconcileDate = reconcileDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
