package com.trunk.demo.vo;

import com.google.gson.JsonObject;
import com.trunk.demo.model.mongo.BankStmt;

import java.util.Date;

public class UploadReviewBankVO {
    private Date date;
    private String transactionDescription;
    private double credits;
    private double debits;


    public UploadReviewBankVO(Date date, String transactionDescription, double credits, double debits) {
        this.date = date;
        this.transactionDescription = transactionDescription;
        this.credits = credits;
        this.debits = debits;
    }

    public UploadReviewBankVO(BankStmt bankStmt){
        this.date = bankStmt.getDate();
        this.transactionDescription = bankStmt.getTransactionDescription();
        this.credits = bankStmt.getCredits();
        this.debits = bankStmt.getDebits();
    }
    public Date getDate() {
        return date;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public double getCredits() {
        return credits;
    }

    public double getDebits() {
        return debits;
    }


}
