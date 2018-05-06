package com.trunk.demo.model.s3;

public class BankStatementTransaction {
	private double accountNumber;
	private String currency;
	private String date;
	private String description;
	private float credits;
	
	public BankStatementTransaction(double accountNumber, String currency, String date, String description, float credits) {
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.date = date;
		this.description = description;
		this.credits = credits;
	}
	
	public double getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public float getCredits() {
		return this.credits;
	}
}
