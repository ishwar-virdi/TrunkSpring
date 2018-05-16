package com.trunk.demo.model.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankStatements")
public class BankStmt {

	@Value("${zone}")
	private String zone;
	
	@Id
	private String id;
	
	private LocalDateTime createDateTime;
	private String accountDescription;
	private long accountNumber;
	private String currency;
	private LocalDate date;
	private String transactionDescription;
	private double debits;
	private double credits;
	private double balance;
	
	public BankStmt(String accountDescription, long accountNumber, String currency, String date,
			String transactionDescription, double debits, double credits, double balance) {
		super();
		this.id = UUID.randomUUID().toString();
		this.createDateTime = LocalDateTime.now();
		this.accountDescription = accountDescription;
		this.accountNumber = accountNumber;
		this.currency = currency;
		date = date + " 00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm").withZone(ZoneId.of(zone));
		this.date = LocalDate.parse(date, formatter);
		this.transactionDescription = transactionDescription;
		this.debits = debits;
		this.credits = credits;
		this.balance = balance;
	}
	
	public LocalDateTime getCreateDate() {
		return this.createDateTime;
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
	
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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
