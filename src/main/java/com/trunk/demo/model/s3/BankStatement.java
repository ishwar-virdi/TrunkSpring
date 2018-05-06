package com.trunk.demo.model.s3;

import java.util.ArrayList;

public class BankStatement {
	private ArrayList<BankStatementTransaction> allTransactions;
	private ArrayList<BankStatementTransaction> transactions;
	
	public BankStatement(ArrayList<BankStatementTransaction> transactions) {
		this.allTransactions = transactions;
		this.transactions = new ArrayList<BankStatementTransaction>();
		int size = transactions.size();
		
		for(int i = 0; i < size; i++) {
			if (transactions.get(i).getDescription().toUpperCase().contains("MERCHANT SETTLEMENT")) {
				transactions.add(transactions.get(i));
			}
		}
		
	}
	
	public ArrayList<BankStatementTransaction> getAllTransactions() {
		return allTransactions;
	}
	
	public ArrayList<BankStatementTransaction> getTransactions() {
		return transactions;
	}
}
