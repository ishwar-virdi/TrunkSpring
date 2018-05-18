package com.trunk.demo.vo;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSettlementStmtVO {
    private List<SettlementStmt> list;
    private ArrayList<SettlementStmt> amexTransactions;
    private ArrayList<SettlementStmt> visaMastercardTransactions;
    private ArrayList<SettlementStmt> directDebitTransactions;
    private ArrayList<SettlementStmt> transactionInRange;

    public ListSettlementStmtVO(List<SettlementStmt> list){
        //init
        amexTransactions = new ArrayList<>();
        visaMastercardTransactions = new ArrayList<>();
        directDebitTransactions = new ArrayList<>();
        transactionInRange = new ArrayList<>();
        this.list = list;
    }

    //Separate the amex, visa/mastercard and direct debit transactions from each other
    public void SeparateSettlement(){
        for (int i = 0; i < this.transactionInRange.size(); i++) {
            if (this.transactionInRange.get(i).getCardSchemeName().equalsIgnoreCase("Amex"))
                this.amexTransactions.add(this.transactionInRange.get(i));
            else if (this.transactionInRange.get(i).getCardSchemeName().equalsIgnoreCase("Visa") || this.transactionInRange.get(i).getCardSchemeName().equalsIgnoreCase("Mastercard"))
                this.visaMastercardTransactions.add(this.transactionInRange.get(i));
            else if (this.transactionInRange.get(i).getCardSchemeName().equalsIgnoreCase("") && !this.transactionInRange.get(i).getBankReference().equalsIgnoreCase(""))
                this.directDebitTransactions.add(this.transactionInRange.get(i));
        }
    }

    public void filterTransactionInRange(Map<String, Double> visa,Map<String, Double> debit){
        int minDate = 99999999;
        int maxDate = 0;
        int tempDate;
        for (Map.Entry<String, Double> visaEntry : visa.entrySet()) {
            tempDate = Integer.parseInt(visaEntry.getKey());
            if(tempDate < minDate){
                minDate = tempDate;
            }
            if(tempDate > maxDate){
                maxDate = tempDate;
            }
        }
        for (Map.Entry<String, Double> debitEntry : debit.entrySet()) {
            tempDate = Integer.parseInt(debitEntry.getKey());
            if(tempDate < minDate){
                minDate = tempDate;
            }
            if(tempDate > maxDate){
                maxDate = tempDate;
            }
        }
        for (int i = 0; i < this.list.size(); i++) {
            int listDate = Integer.parseInt(this.list.get(i).getSettlementDate());
            if(listDate >= minDate && listDate <= maxDate){
                transactionInRange.add(this.list.get(i));
            }
        }
    }

    public Map<String, Double> addUpSameDayTransactions(String transactionName){
        Map<String, Double> transactionAmountsByDate = new HashMap<String, Double>();
        ArrayList<SettlementStmt> transactions = new ArrayList<>();
        if("amax".equals(transactionName)){
            transactions = this.amexTransactions;
        }else if("visaMaster".equals(transactionName)){
            transactions = this.visaMastercardTransactions;
        }else{
            transactions = this.directDebitTransactions;
        }

        for (int i = 0; i < transactions.size(); i++) {
            Double amount = transactionAmountsByDate.get(transactions.get(i).getSettlementDate());
            if (amount != null) {
                transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), amount + transactions.get(i).getPrincipalAmount());
            } else {
                transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), transactions.get(i).getPrincipalAmount());
            }
        }
//        for (Map.Entry<String, Double> entry : transactionAmountsByDate.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        return transactionAmountsByDate;
    }

    public ArrayList<SettlementStmt> getSettleOnday(String day,String transactionName){
        ArrayList<SettlementStmt> response= new ArrayList<>();
        ArrayList<SettlementStmt> listInRange = new ArrayList<>();
        if("amax".equals(transactionName)){
            listInRange = this.amexTransactions;
        }else if("visaMaster".equals(transactionName)){
            listInRange = this.visaMastercardTransactions;
        }else{
            listInRange = this.directDebitTransactions;
        }
        for(int i = 0,length = listInRange.size();i < length;i++){
            if(day.equals(listInRange.get(i).getSettlementDate())){
                response.add(listInRange.get(i));
            }
        }
        return response;
    }

    public ArrayList<SettlementStmt> getTransactionInRange() {
        return transactionInRange;
    }

    public List<SettlementStmt> getList() {
        return list;
    }
    public void setList(List<SettlementStmt> list) {
        this.list = list;
    }

    public ArrayList<SettlementStmt> getAmexTransactions() {
        return amexTransactions;
    }

    public ArrayList<SettlementStmt> getVisaMastercardTransactions() {
        return visaMastercardTransactions;
    }

    public ArrayList<SettlementStmt> getDirectDebitTransactions() {
        return directDebitTransactions;
    }


}
