package com.trunk.demo.vo;

import com.trunk.demo.Util.FormatUtil;
import com.trunk.demo.model.mongo.BankStmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBankStmtVO {

    private List<BankStmt> list;
    private List<BankStmt> filterBankStmt;
    private List<BankStmt> restFilterBankStmt;
    public ListBankStmtVO(List<BankStmt> list){
        filterBankStmt = new ArrayList<>();
        restFilterBankStmt = new ArrayList<>();
        this.list = list;
    }

    /**
     * Filter Bank Statement
     */
    public void filterBankStatement(){
        filterBankStmt.clear();
        restFilterBankStmt.clear();
        FormatUtil formatUtil = new FormatUtil();
        String transactionDescription;
        String[] description;
        for(int i = 0, length = this.list.size();i < length;i++){

            transactionDescription = this.list.get(i).getTransactionDescription().toLowerCase();
            description = transactionDescription.split(" ");
            if(this.list.get(i).getCredits() > 0.0    //not withdraw
                    && !("deposit".equals(description[0]) && !("DE".equals(description[1]) && "DRAW".equals(description[2]))))
            {
                filterBankStmt.add(this.list.get(i));
            }else{
                restFilterBankStmt.add(this.list.get(i));
            }
        }
    }

    public Map<String, Double> addUpSameDayTransactions(){
        Map<String, Double> transactionAmountsByDate = new HashMap<String, Double>();

        for (int i = 0; i < this.filterBankStmt.size(); i++) {
            Double amount = transactionAmountsByDate.get(this.filterBankStmt.get(i).getDate());
            if (amount != null) {
                transactionAmountsByDate.put(this.filterBankStmt.get(i).getDate(), amount + this.filterBankStmt.get(i).getCredits());
            } else {
                transactionAmountsByDate.put(this.filterBankStmt.get(i).getDate(), this.filterBankStmt.get(i).getCredits());
            }
        }
//        for (Map.Entry<String, Double> entry : transactionAmountsByDate.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        return transactionAmountsByDate;
    }

    public List<BankStmt> getList() {
        return list;
    }
    public void setList(List<BankStmt> list) {
        this.list = list;
    }
    public List<BankStmt> getFilterBankStmt() { return filterBankStmt; }
    public List<BankStmt> getRestFilterBankStmt() { return restFilterBankStmt; }
}
