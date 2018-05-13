package com.trunk.demo.model.mongo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementStatements")
public class SettlementStmt {

	@Id
	private String id;
	
	private Date createDate;
	private String merchantID;
	private String cardPAN;
	private String cardExpiry;
	private String bankReference;
	private double principalAmount;
	private double surcharge;
	private String currency;
	private String customerName;
	private String responseText;
	private long receiptNumber;
	private String settlementDate;
	private String cardSchemeName;
	private String transactionTimeStamp;
	private String status;
	private boolean isReconciled;
	private Date reconciledDateTime;
	
	public SettlementStmt(String merchantID, String cardPAN, String cardExpiry, String bankReference, double principalAmount,
			double surcharge, String currency, String customerName, String responseText, long receiptNumber, String settlementDate,
			String cardSchemeName, String transactionTimeStamp, String status) {
		super();
		this.createDate = new Date();
		this.merchantID = merchantID;
		this.cardPAN = cardPAN;
		this.cardExpiry = cardExpiry;
		this.bankReference = bankReference;
		this.principalAmount = principalAmount;
		this.surcharge = surcharge;
		this.currency = currency;
		this.customerName = customerName;
		this.responseText = responseText;
		this.receiptNumber = receiptNumber;
		this.settlementDate = settlementDate;
		this.cardSchemeName = cardSchemeName;
		this.transactionTimeStamp = transactionTimeStamp;
		this.status = status;
		this.isReconciled = false;
		this.reconciledDateTime = null;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}

	public String getMerchantID() {
		return this.merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getCardPAN() {
		return this.cardPAN;
	}

	public void setCardPAN(String cardPAN) {
		this.cardPAN = cardPAN;
	}

	public String getCardExpiry() {
		return this.cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}
	
	public String getBankReference() {
		return this.bankReference;
	}
	
	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public double getPrincipalAmount() {
		return this.principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public double getSurcharge() {
		return this.surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getResponseText() {
		return this.responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public long getReceiptNumber() {
		return this.receiptNumber;
	}

	public void setReceiptNumber(long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getSettlementDate() {
		return this.settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getCardSchemeName() {
		return this.cardSchemeName;
	}

	public void setCardSchemeName(String cardSchemeName) {
		this.cardSchemeName = cardSchemeName;
	}

	public String getTransactionTimeStamp() {
		return this.transactionTimeStamp;
	}

	public void setTransactionTimeStamp(String transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public boolean getIsReconciled() {
		return this.isReconciled;
	}
	
	public void setIsReconciled(boolean isReconciled) {
		this.isReconciled = isReconciled;
	}
	
	public Date getReconciledDateTime() {
		return this.reconciledDateTime;
	}
	
	public void setReconciledDateTime(Date dateTime) {
		this.reconciledDateTime = dateTime;
	}
}
