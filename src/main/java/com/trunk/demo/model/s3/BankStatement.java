package com.trunk.demo.model.s3;

import java.util.ArrayList;

public class BankStatement {
	private ArrayList<BankStatementTransaction> allTransactions;
	//Only contains the credit card transactions that we care about
	private ArrayList<BankStatementTransaction> creditCardTransactions;
	//Only contains the direct debit transactions that we care about
	private ArrayList<BankStatementTransaction> bankTransferTransactions;
	//I would like to replace this hard coded string with pulling it out of the settlement file instead
	private String bankStatementDescription = "DE DRAW ID518431";
	
	public BankStatement(ArrayList<BankStatementTransaction> transactions) {
		this.allTransactions = transactions;
		this.creditCardTransactions = new ArrayList<BankStatementTransaction>();
		this.bankTransferTransactions = new ArrayList<BankStatementTransaction>();
		int size = transactions.size();
		
		for(int i = 0; i < size; i++) {
			if (transactions.get(i).getDescription().toUpperCase().contains("MERCHANT SETTLEMENT")) {
				this.creditCardTransactions.add(transactions.get(i));
			} else if (transactions.get(i).getDescription().toUpperCase().contains(bankStatementDescription)) {
				this.bankTransferTransactions.add(transactions.get(i));
			}
		}
	}
	
	public ArrayList<BankStatementTransaction> getAllTransactions() {
		return allTransactions;
	}
	
	public ArrayList<BankStatementTransaction> getCreditCardTransactions() {
		return this.creditCardTransactions;
	}
	
	public ArrayList<BankStatementTransaction> getBankTransferTransactions() {
		return this.bankTransferTransactions;
	}
}
