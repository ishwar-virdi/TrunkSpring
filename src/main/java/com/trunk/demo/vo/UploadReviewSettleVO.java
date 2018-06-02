package com.trunk.demo.vo;

import com.google.gson.JsonObject;
import com.trunk.demo.model.mongo.SettlementStmt;

public class UploadReviewSettleVO {

    private long receiptNumber;
    private String cardSchemeName;
    private double principalAmount;
    private String customerName;

    public UploadReviewSettleVO(long receiptNumber, String cardSchemeName, double principalAmount, String customerName) {
        this.receiptNumber = receiptNumber;
        this.cardSchemeName = cardSchemeName;
        this.principalAmount = principalAmount;
        this.customerName = customerName;
    }

    public UploadReviewSettleVO(SettlementStmt settle){
        String type = String.valueOf(settle.getCardSchemeName());
        if("".equals(type)){
            type = "Debit";
        }
        this.receiptNumber = settle.getReceiptNumber();
        this.cardSchemeName = type;
        this.principalAmount = settle.getPrincipalAmount();
        this.customerName = settle.getCustomerName();
    }
    public UploadReviewSettleVO() {
    }
    public long getReceiptNumber() {
        return receiptNumber;
    }

    public String getCardSchemeName() {
        return cardSchemeName;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setReceiptNumber(long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public void setCardSchemeName(String cardSchemeName) {
        this.cardSchemeName = cardSchemeName;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UploadReviewSettleVO{");
        sb.append("ReceiptNumber=").append(receiptNumber);
        sb.append(", cardSchemeName='").append(cardSchemeName).append('\'');
        sb.append(", principalAmount=").append(principalAmount);
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
