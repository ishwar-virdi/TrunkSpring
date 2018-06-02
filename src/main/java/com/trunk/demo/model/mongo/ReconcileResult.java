package com.trunk.demo.model.mongo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ReconcileResults")
public class ReconcileResult {

    @Id
    private String id;

    @Field
    private String userId;
    @Field
    private Date lastModified;
    @Field
    private int isReconciled;
    @Field
    private int notReconciled;
    @Field
    private int percentage;

    public ReconcileResult(String id, String userId, int isReconciled, int notReconciled) {
        super();
        this.id = id;
        this.userId = userId;
        this.lastModified = new Date();
        this.isReconciled = isReconciled;
        this.notReconciled = notReconciled;
        if (isReconciled + notReconciled > 0)
            this.percentage = (int) ((isReconciled / (isReconciled + notReconciled)) * 100);
        else
            this.percentage = 0;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getIsReconciled() {
        return isReconciled;
    }

    public void setIsReconciled(int isReconciled) {
        this.isReconciled = isReconciled;
    }

    public int getNotReconciled() {
        return notReconciled;
    }

    public void setNotReconciled(int notReconciled) {
        this.notReconciled = notReconciled;
    }

    public String getId() {
        return id;
    }

    public ReconcileResult(String id, String userId, Date lastModified, Date startDate, Date endDate, int isReconciled,
            int notReconciled, int percentage) {
        super();
        this.id = id;
        this.userId = userId;
        this.lastModified = lastModified;
        this.isReconciled = isReconciled;
        this.notReconciled = notReconciled;
        this.percentage = percentage;
    }

    public ReconcileResult() {
        super();
        // TODO Auto-generated constructor stub
    }

}