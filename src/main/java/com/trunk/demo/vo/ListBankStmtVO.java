package com.trunk.demo.vo;

import com.trunk.demo.Util.FormatUtil;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBankStmtVO {

    private List<BankStmt> list;
    private ArrayList<BankStmt> filterVisaBankStmt;
    private ArrayList<BankStmt> filterDebitBankStmt;
    private List<BankStmt> restFilterBankStmt;
    public ListBankStmtVO(List<BankStmt> list){
        filterVisaBankStmt = new ArrayList<>();
        filterDebitBankStmt = new ArrayList<>();
        restFilterBankStmt = new ArrayList<>();
        this.list = list;
    }

    /**
     * Filter Bank Statement
     */
    public void filterBankStatement(){
        filterVisaBankStmt.clear();
        restFilterBankStmt.clear();
        filterDebitBankStmt.clear();
        FormatUtil formatUtil = new FormatUtil();
        String transactionDescription;
        String[] description;
        //&& !("deposit".equals(description[0]) && !("de".equals(description[1]) && "draw".equals(description[2]))
        for(int i = 0, length = this.list.size();i < length;i++){
            transactionDescription = this.list.get(i).getTransactionDescription().toLowerCase();
            description = transactionDescription.split(" ");
            if("merchant".equals(description[0])
                    &&"settlement".equals(description[1])
                    &&this.list.get(i).getCredits() > 0.0)
            {
                filterVisaBankStmt.add(this.list.get(i));
            }else if("deposit".equals(description[0])
                    && "de".equals(description[1])
                    &&"draw".equals(description[2])
                    &&this.list.get(i).getCredits() > 0.0)
            {
                filterDebitBankStmt.add(this.list.get(i));
            }else{
                restFilterBankStmt.add(this.list.get(i));
            }
        }
    }

    public Map<String, Double> addUpSameDayTransactions(String type){
        ArrayList<BankStmt> list = new ArrayList<>();
        if("visaMaster".equals(type)){
            list = this.filterVisaBankStmt;
        }else if("debit".equals(type)){
            list = this.filterDebitBankStmt;
        }else{

        }
        Map<String, Double> transactionAmountsByDate = new HashMap<String, Double>();

        for (int i = 0; i < list.size(); i++) {
            Double amount = transactionAmountsByDate.get(list.get(i).getDate());
            if (amount != null) {
                transactionAmountsByDate.put(list.get(i).getDate(), amount + list.get(i).getCredits());
            } else {
                transactionAmountsByDate.put(list.get(i).getDate(), list.get(i).getCredits());
            }
        }
//        for (Map.Entry<String, Double> entry : transactionAmountsByDate.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        return transactionAmountsByDate;
    }

    public ArrayList<BankStmt> getBankStmtOnday(String day, String type){

        ArrayList<BankStmt> list = new ArrayList<>();
        if("visaMaster".equals(type)){
            list = this.filterVisaBankStmt;
        }else if("debit".equals(type)){
            list = this.filterDebitBankStmt;
        }else{

        }
        ArrayList<BankStmt> response= new ArrayList<>();
        for(int i = 0,length = list.size();i < length;i++){
            if(day.equals(list.get(i).getDate())){
                response.add(list.get(i));
            }
        }
        return response;
    }

    public List<BankStmt> getList() {
        return list;
    }
    public void setList(List<BankStmt> list) {
        this.list = list;
    }
    public List<BankStmt> getFilterVisaBankStmt() { return filterVisaBankStmt; }

    public List<BankStmt> getRestFilterBankStmt() { return restFilterBankStmt; }
}
