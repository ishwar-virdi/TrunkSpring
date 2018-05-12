package com.trunk.demo.model.s3;

import java.util.ArrayList;

public class BankStatement {
	private ArrayList<BankStatementTransaction> allTransactions;
	//Only contains the credit card transactions that we care about
	private ArrayList<BankStatementTransaction> creditCardTransactions;
	//Only contains the direct debit transactions that we care about
	private ArrayList<BankStatementTransaction> bankTransferTransactions;
	//Used to find the relevant credit card transaction lump sums
	private static final String creditCardDescription = "MERCHANT SETTLEMENT";
	
	public BankStatement(ArrayList<BankStatementTransaction> transactions) {
		this.allTransactions = transactions;
		this.creditCardTransactions = new ArrayList<BankStatementTransaction>();
		this.bankTransferTransactions = new ArrayList<BankStatementTransaction>();
		
		int size = transactions.size();
		
		for(int i = 0; i < size; i++) {
			if (transactions.get(i).getDescription().toUpperCase().contains(creditCardDescription))
				this.creditCardTransactions.add(transactions.get(i));
		}
	}
	
	public void extractBankTransferItems(ArrayList<String> transactionDescription) {
		int size = this.allTransactions.size();
		
		for(int i = 0; i < size; i++) {
			for (int x = 0; x < transactionDescription.size(); x++) {
				if (this.allTransactions.get(i).getDescription().toUpperCase().replaceAll("\\s","").contains(transactionDescription.get(x).toUpperCase())) 
					this.bankTransferTransactions.add(this.allTransactions.get(i));
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
