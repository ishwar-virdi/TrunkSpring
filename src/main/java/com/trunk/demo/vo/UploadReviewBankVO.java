package com.trunk.demo.vo;

import com.google.gson.JsonObject;

import java.util.Date;

public class UploadReviewBankVO {
    private String date;
    private String transactionDescription;
    private double credits;
    private double debits;


    public UploadReviewBankVO(String date, String transactionDescription, double credits, double debits) {
        this.date = date;
        this.transactionDescription = transactionDescription;
        this.credits = credits;
        this.debits = debits;
    }

    public UploadReviewBankVO(JsonObject jsonObject){
        this.date = String.valueOf(jsonObject.get("date").toString());
        this.transactionDescription = jsonObject.get("transactionDescription").toString();
        this.credits = Double.parseDouble(jsonObject.get("credits").toString());
        this.debits = Double.parseDouble(jsonObject.get("debits").toString());
    }
    public String getDate() {
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
