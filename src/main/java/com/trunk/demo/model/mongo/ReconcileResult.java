package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ReconcileResults")

public class ReconcileResult {
    @Id
    private String id;
    private String uid;
    private int reconcileDate;
    private String reconcileTime;
    private String dateRange;
    private int percentage;

    public ReconcileResult(String uid,int reconcileDate, String reconcileTime, String dateRange, int percentage) {
        super();
        this.uid = uid;
        this.reconcileDate = reconcileDate;
        this.reconcileTime = reconcileTime;
        this.dateRange = dateRange;
        this.percentage = percentage;
    }

    public ReconcileResult() {
        super();
    }

    public String getId() {
        return id;
    }

    public String getReconcileTime() {
        return reconcileTime;
    }

    public void setReconcileTime(String reconcileTime) {
        this.reconcileTime = reconcileTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getReconcileDate() {
        return reconcileDate;
    }

    public void setReconcileDate(int reconcileDate) {
        this.reconcileDate = reconcileDate;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ReconcileResult{");
        sb.append("id='").append(id).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", reconcileDate=").append(reconcileDate);
        sb.append(", reconcileTime='").append(reconcileTime).append('\'');
        sb.append(", dateRange='").append(dateRange).append('\'');
        sb.append(", percentage=").append(percentage);
        sb.append('}');
        return sb.toString();
    }
}
