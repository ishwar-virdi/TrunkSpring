package com.trunk.demo.vo;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSettlementStmtVO {
    private List<SettlementStmt> list;
    private ArrayList<SettlementStmt> amexTransactions;
    private ArrayList<SettlementStmt> visaMastercardTransactions;
    private ArrayList<SettlementStmt> directDebitTransactions;


    public ListSettlementStmtVO(List<SettlementStmt> list){
        //init
        amexTransactions = new ArrayList<>();
        visaMastercardTransactions = new ArrayList<>();
        directDebitTransactions = new ArrayList<>();

        this.list = list;
    }

    //Separate the amex, visa/mastercard and direct debit transactions from each other
    public void SeparateSettlement(){
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getCardSchemeName().equalsIgnoreCase("Amex"))
                this.amexTransactions.add(this.list.get(i));
            else if (this.list.get(i).getCardSchemeName().equalsIgnoreCase("Visa") || this.list.get(i).getCardSchemeName().equalsIgnoreCase("Mastercard"))
                this.visaMastercardTransactions.add(this.list.get(i));
            else if (this.list.get(i).getCardSchemeName().equalsIgnoreCase("") && !this.list.get(i).getBankReference().equalsIgnoreCase(""))
                this.directDebitTransactions.add(this.list.get(i));
        }
    }

    public Map<String, Double> addUpSameDayTransactions(String transactionName){
        Map<String, Double> transactionAmountsByDate = new HashMap<String, Double>();
        ArrayList<SettlementStmt> transactions = new ArrayList<>();
        if("amax".equals(transactionName)){
            transactions = this.amexTransactions;
        }else if("visaMaster".equals(transactionName)){
            transactions = this.visaMastercardTransactions;
        }else{}

        for (int i = 0; i < transactions.size(); i++) {
            Double amount = transactionAmountsByDate.get(transactions.get(i).getSettlementDate());
            if (amount != null) {
                transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), amount + transactions.get(i).getPrincipalAmount());
            } else {
                transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), transactions.get(i).getPrincipalAmount());
            }
        }
        return transactionAmountsByDate;
    }

    public Map<String, Double> getOverviewRange(Map<String, Double> settleTotals,Map<String, Double> bankStmtTotals){
        Map<String, Double> settleMap = new HashMap<String, Double>();
		for (Map.Entry<String, Double> bankEntry : bankStmtTotals.entrySet()) {
            for (Map.Entry<String, Double> settleEntry : settleTotals.entrySet()) {
                if(bankEntry.getKey().equals(settleEntry.getKey())){
                    settleMap.put(settleEntry.getKey(),settleEntry.getValue());
                    break;
                }
            }
		}
        return settleMap;
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
