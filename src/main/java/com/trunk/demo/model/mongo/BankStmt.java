package com.trunk.demo.model.mongo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankStatements")
public class BankStmt implements Serializable {

	@Id
	private int id;

	private Date createDateTime;
	private String accountDescription;
	private long accountNumber;
	private String currency;
	@Indexed
	private Date date;
	private String transactionDescription;
	private double debits;
	private double credits;
	private double balance;

	public BankStmt(String accountDescription, long accountNumber, String currency, String date,
			String transactionDescription, double debits, double credits, double balance) throws ParseException {
		super();
		this.createDateTime = new Date();
		this.accountDescription = accountDescription;
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.date = new SimpleDateFormat("yyyyMMdd").parse(date);
		this.transactionDescription = transactionDescription;
		this.debits = debits;
		this.credits = credits;
		this.balance = balance;
		this.id = this.hashCode();
	}

	public Date getCreateDate() {
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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public int getId() {
		return id;
	}

	public BankStmt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankStmt(int id, Date createDateTime, String accountDescription, long accountNumber, String currency,
			Date date, String transactionDescription, double debits, double credits, double balance) {
		super();
		this.id = id;
		this.createDateTime = createDateTime;
		this.accountDescription = accountDescription;
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.date = date;
		this.transactionDescription = transactionDescription;
		this.debits = debits;
		this.credits = credits;
		this.balance = balance;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BankStmt [id=" + id + ", createDateTime=" + createDateTime + ", accountDescription="
				+ accountDescription + ", accountNumber=" + accountNumber + ", currency=" + currency + ", date=" + date
				+ ", transactionDescription=" + transactionDescription + ", debits=" + debits + ", credits=" + credits
				+ ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		return new StringBuilder().append(accountDescription).append(accountNumber).append(currency).append(date)
				.append(transactionDescription).append(debits).append(credits).append(balance).toString().hashCode();
	}

}
