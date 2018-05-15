package com.trunk.demo.model.mongo;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementStatements")
public class SettlementStmt {

	@Id
	private Long receiptNumber;
	
	private LocalDateTime createDateTime;
	private String merchantID;
	private String cardPAN;
	private String cardExpiry;
	private String bankReference;
	private double principalAmount;
	private double surcharge;
	private String currency;
	private String customerName;
	private String responseText;
	private String settlementDate;
	private String cardSchemeName;
	private String transactionTimeStamp;
	private String status;
	private boolean isReconciled;
	private LocalDateTime reconciledDateTime;
	
	public SettlementStmt(String merchantID, String cardPAN, String cardExpiry, String bankReference, double principalAmount,
			double surcharge, String currency, String customerName, String responseText, long receiptNumber, String settlementDate,
			String cardSchemeName, String transactionTimeStamp, String status) {
		super();
		this.createDateTime = LocalDateTime.now();
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
		this.reconciledDateTime = LocalDateTime.of(1900, 1, 1, 0, 0);
	}

	public String getTransactionDate() {
		return transactionTimeStamp.substring(0, 10);
	}

	public String getTransactionTime() {
		return transactionTimeStamp.substring(11, transactionTimeStamp.length());
	}

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getCreateDateTime() {
		return createDateTime.toString();
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getCardPAN() {
		return cardPAN;
	}

	public void setCardPAN(String cardPAN) {
		this.cardPAN = cardPAN;
	}

	public String getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getBankReference() {
		return bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getCardSchemeName() {
		return cardSchemeName;
	}

	public void setCardSchemeName(String cardSchemeName) {
		this.cardSchemeName = cardSchemeName;
	}

	public String getTransactionTimeStamp() {
		return transactionTimeStamp;
	}

	public void setTransactionTimeStamp(String transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isReconciled() {
		return isReconciled;
	}

	public void setReconciled(boolean isReconciled) {
		this.isReconciled = isReconciled;
	}

	public String getReconciledDateTime() {
		return reconciledDateTime.toString();
	}

	public void setReconciledDateTime(LocalDateTime reconciledDateTime) {
		this.reconciledDateTime = reconciledDateTime;
	}

	
	
	
}
