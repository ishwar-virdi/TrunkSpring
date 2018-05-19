package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ReconcileDetails")
public class ReconcileDetailItem {

    @Id
    private String id;
    @Field
    private String date;
    @Field
    private String description;
    @Field
    private double amount;
    @Field
    private long reciptNumber;
    @Field
    private String transactionType;
    @Field
    private boolean successful;
    @Field
    private int rule;

    public ReconcileDetailItem(SettlementStmt settlementStmt,String description,boolean successful){
        this.id = settlementStmt.getId();
        this.date = settlementStmt.getSettlementDate();
        this.description = description;
        this.amount = settlementStmt.getPrincipalAmount();
        this.reciptNumber = settlementStmt.getReceiptNumber();
        if("".equals(settlementStmt.getCardSchemeName())){
            this.transactionType = "directDebit";
        }else{
            this.transactionType = settlementStmt.getCardSchemeName();
        }
        this.successful = successful;
        this.rule = 1;
    }
    @PersistenceConstructor
    public ReconcileDetailItem(String id,String date, String description, double amount, long reciptNumber, String transactionType, boolean successful, int rule) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.reciptNumber = reciptNumber;
        this.transactionType = transactionType;
        this.successful = successful;
        this.rule = rule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getReciptNumber() {
        return reciptNumber;
    }

    public void setReciptNumber(long accountNumber) {
        this.reciptNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ReconcileDetailItem{");
        sb.append("id='").append(id).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", accountNumber='").append(reciptNumber).append('\'');
        sb.append(", transactionType='").append(transactionType).append('\'');
        sb.append(", successful=").append(successful);
        sb.append(", rule=").append(rule);
        sb.append('}');
        return sb.toString();
    }
}
