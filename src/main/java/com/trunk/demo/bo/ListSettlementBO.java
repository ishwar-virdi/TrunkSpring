package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.*;

public class ListSettlementBO {
    private enum cardType{
        VISA,AMEX
    }

    private List<SettlementStmt> list;
    private List<SettlementStmt> visaList;
    private List<SettlementStmt> debitList;
    private List<SettlementStmt> amexList;
    private Map<Date,Double> visaMap;
    private Map<Date,Double> debitMap;
    private Map<Date,Double> amexMap;


    public ListSettlementBO(List<SettlementStmt> list) {
        this.list = list;
        this.visaList = new ArrayList<>();
        this.debitList = new ArrayList<>();
        this.amexList = new ArrayList<>();
        separateSettle();
        this.visaMap = addUpSameDayTransactions(visaList);
        this.debitMap = addUpSameDayTransactions(debitList);
        this.amexMap = addUpSameDayTransactions(amexList);
    }

    public List<SettlementStmt> getList() {
        return list;
    }


    public Double getVisaMapTotal(Date date) {
        if(visaMap.get(date) == null){
            return 0.0;
        }else{
            return visaMap.get(date);
        }
    }
    public Double getDebitMapTotal(Date date) {
        if(debitMap.get(date) == null){
            return 0.0;
        }else{
            return debitMap.get(date);
        }
    }
    public Double getAmexMapTotal(Date date) {
        if(amexMap.get(date) == null){
            return 0.0;
        }else{
            return amexMap.get(date);
        }
    }

    public double getTotalAmount(){
        double totalAmount = 0;
        for(int i = 0,length = list.size();i < length; i++){
            if("Approved".equals(list.get(i).getStatus())){
                totalAmount += list.get(i).getPrincipalAmount();
                totalAmount += list.get(i).getSurcharge();
            }
        }
        return totalAmount;
    }

    private void separateSettle(){
        for(int i = 0; i < list.size();i++){
            if(cardType.VISA.toString().equals(list.get(i).getCardSchemeName())){
                visaList.add(list.get(i));
            }else if(cardType.AMEX.toString().equals(list.get(i).getCardSchemeName())){
                amexList.add(list.get(i));
            }else{
                debitList.add(list.get(i));
            }
        }
    }


    private Map<Date, Double> addUpSameDayTransactions(List<SettlementStmt> list){
        Map<Date, Double> map = new HashMap<Date, Double>();
        for (int i = 0; i < list.size(); i++) {
            Double amount = map.get(list.get(i).getSettlementDate());
            if (amount != null) {
                map.put(list.get(i).getSettlementDate(), amount + list.get(i).getPrincipalAmount());
            } else {
                map.put(list.get(i).getSettlementDate(), list.get(i).getPrincipalAmount());
            }
        }
        return map;
    }
}
