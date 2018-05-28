package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.BankStmt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListBankStatementBO {
    private List<BankStmt> list;
    private List<BankStmt> visas;
    private List<BankStmt> debits;
    private List<BankStmt> amexs;

    private Map<Date,Double> visaMap;
    private Map<Date,Double> debitMap;
    private Map<Date,Double> amexMap;
    public ListBankStatementBO(List<BankStmt> bankList) {
        this.list = bankList;
        this.visas = new ArrayList<>();
        this.debits = new ArrayList<>();
        this.amexs = new ArrayList<>();
        separateDocuemtns();
        this.visaMap = this.addUpSameDayTransactions(visas);
        this.debitMap = this.addUpSameDayTransactions(debits);
        this.amexMap = this.addUpSameDayTransactions(amexs);
    }

    public void separateDocuemtns(){
        String[] description = null;
        for(int i = 0,length = list.size();i < length; i++){
            description = list.get(i).getTransactionDescription().toLowerCase().split(" ");
            double credits = list.get(i).getCredits();
            int descripLen = description.length;

            if(credits <= 0){
            }else if(isNum(description[descripLen-1]) && "deposit".equals(description[0])){
            }else if("merchant".equals(description[0])
                    &&"settlement".equals(description[1])){
                visas.add(list.get(i));
            }else if("direct".equals(description[descripLen-2])
                    && "debit".equals(description[descripLen-1])){
                debits.add(list.get(i));
            }else if("deposit".equals(description[0])){
            }else{
                amexs.add(list.get(i));
            }
        }
    }

    private Map<Date, Double> addUpSameDayTransactions(List<BankStmt> list){
        Map<Date, Double> map = new HashMap<Date, Double>();
        for (int i = 0; i < list.size(); i++) {
            Double amount = map.get(list.get(i).getDate());
            if (amount != null) {
                map.put(list.get(i).getDate(), amount + list.get(i).getCredits());
            } else {
                map.put(list.get(i).getDate(), list.get(i).getCredits());
            }
        }
        return map;
    }

    public double getTotalAmount(){
        double totalAmount = 0;
        for(int i = 0,length = visas.size();i < length; i++){
            totalAmount += visas.get(i).getCredits();
        }
        for(int i = 0,length = debits.size();i < length; i++){
            totalAmount += debits.get(i).getCredits();
        }
        for(int i = 0,length = amexs.size();i < length; i++){
            totalAmount += amexs.get(i).getCredits();
        }
        return totalAmount;
    }
    public List<BankStmt> getList() {
        return list;
    }
    public List<BankStmt> getVisas() {
        return visas;
    }

    public List<BankStmt> getDebits() {
        return debits;
    }

    public List<BankStmt> getAmexs() {
        return amexs;
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

    private boolean isNum(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
