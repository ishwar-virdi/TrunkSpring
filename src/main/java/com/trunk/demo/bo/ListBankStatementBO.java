package com.trunk.demo.bo;

import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.model.mongo.BankStmt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListBankStatementBO {
    private List<BankStmt> list;
    private List<BankStmt> visaMasters;
    private List<BankStmt> debits;
    private List<BankStmt> amexs;

    private Map<Date,Double> visaMasterMap;
    private Map<Date,Double> debitMap;
    private Map<Date,Double> amexMap;
    private CalenderUtil cal = new CalenderUtil();
    public ListBankStatementBO(List<BankStmt> bankList) {
        this.list = bankList;
        this.visaMasters = new ArrayList<>();
        this.debits = new ArrayList<>();
        this.amexs = new ArrayList<>();
        separateDocuments();
        this.visaMasterMap = this.addUpSameDayTransactions(visaMasters);
        this.debitMap = this.addUpSameDayTransactions(debits);
        this.amexMap = this.addUpSameDayTransactions(amexs);
    }

    public void separateDocuments(){
        String[] descriptions = null;
        String description;
        for(int i = 0,length = this.list.size();i < length; i++){
            description = this.list.get(i).getTransactionDescription().toLowerCase();
            descriptions = description.split(" ");
            double credits = this.list.get(i).getCredits();
            int descripLen = descriptions.length;
            if(credits <= 0){
                /*
                 * ignore debits transactions
                 */
            }else if(description.contains("merchant settlement")){
                //Add visa master
                visaMasters.add(this.list.get(i));

            }else if(description.contains("direct debit")
                    && "deposit".equals(descriptions[0])){
                //Add direct debit

                debits.add(this.list.get(i));
            }else if(isNum(descriptions[descripLen-1]) && "deposit".equals(descriptions[0])){
                /*
                 * ignore transaction : DEPOSIT Elaine Mills Pro        24 20180307
                 */
            }else if("deposit".equals(descriptions[0])){
                /*
                 * ignore DEPOSIT ONLINE 2615297 PYMT Top Level Real E   INV-2943
                 */
            }else{
                //Amexs
                amexs.add(this.list.get(i));
            }
        }
    }

    private Map<Date, Double> addUpSameDayTransactions(List<BankStmt> list){
        Map<Date, Double> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDate(cal.setDateToInit(list.get(i).getDate()));
            Double amount = map.get(list.get(i).getDate());
            if (amount != null) {
                map.put(cal.setDateToInit(list.get(i).getDate()), amount + list.get(i).getCredits());
            } else {
                map.put(cal.setDateToInit(list.get(i).getDate()), list.get(i).getCredits());
            }
        }
        return map;
    }

    public double getTotalAmount(){
        double totalAmount = 0;
        for(int i = 0,length = visaMasters.size();i < length; i++){
            totalAmount += visaMasters.get(i).getCredits();
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
        return visaMasters;
    }

    public List<BankStmt> getDebits() {
        return debits;
    }

    public List<BankStmt> getAmexs() {
        return amexs;
    }

    public Double getVisaMapTotal(Date date) {
        if(visaMasterMap.get(date) == null){
            return 0.0;
        }else{
            return visaMasterMap.get(date);
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
