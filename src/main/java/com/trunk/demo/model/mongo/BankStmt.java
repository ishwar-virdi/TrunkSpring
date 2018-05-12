package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankStatements")
public class BankStmt {

	@Id
	private String id;
	
	private String AccountDescription;
	private long AccountNumber;
	private String Currency;
	private String Date;
	private String TransactionDescription;
	private double Debits;
	private double Credits;
	private double Balance;
	
	public BankStmt(String accountDescription, long accountNumber, String currency, String date,
			String transactionDescription, double debits, double credits, double balance) {
		super();
		AccountDescription = accountDescription;
		AccountNumber = accountNumber;
		Currency = currency;
		Date = date;
		TransactionDescription = transactionDescription;
		Debits = debits;
		Credits = credits;
		Balance = balance;
	}

	public String getAccountDescription() {
		return AccountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		AccountDescription = accountDescription;
	}

	public long getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		AccountNumber = accountNumber;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getTransactionDescription() {
		return TransactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		TransactionDescription = transactionDescription;
	}

	public double getDebits() {
		return Debits;
	}

	public void setDebits(double debits) {
		Debits = debits;
	}

	public double getCredits() {
		return Credits;
	}

	public void setCredits(double credits) {
		Credits = credits;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public String getId() {
		return id;
	}
	
	
	
}
