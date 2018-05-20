package com.trunk.demo.vo;

import java.util.Date;

public class ReconcileResultDetailsVO {
	private String id;
	private boolean isReconciled;
	private String customerName;
	private Date transactionDate;
	private double amount;
	private double accountNumber;
	private String rule;
	
	public ReconcileResultDetailsVO(String id, boolean isReconciled, String customerName, Date transactionDate,
			double amount, double accountNumber, String rule) {
		super();
		this.id = id;
		this.isReconciled = isReconciled;
		this.customerName = customerName;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.rule = rule;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isReconciled() {
		return isReconciled;
	}
	public void setReconciled(boolean isReconciled) {
		this.isReconciled = isReconciled;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(double accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
}
