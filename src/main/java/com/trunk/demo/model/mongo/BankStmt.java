package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankStatements")
public class BankStmt {

	@Id
	private String id;
	
	private String accountDescription;
	private long accountNumber;
	private String currency;
	private String date;
	private String transactionDescription;
	private double debits;
	private double credits;
	private double balance;
	
	public BankStmt(String accountDescription, long accountNumber, String currency, String date,
			String transactionDescription, double debits, double credits, double balance) {
		super();
		this.accountDescription = accountDescription;
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.date = date;
		this.transactionDescription = transactionDescription;
		this.debits = debits;
		this.credits = credits;
		this.balance = balance;
	}

	public String getAccountDescription() {
		return this.accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	public long getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTransactionDescription() {
		return this.transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public double getDebits() {
		return this.debits;
	}

	public void setDebits(double debits) {
		this.debits = debits;
	}

	public double getCredits() {
		return this.credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}
	
	
	
}
