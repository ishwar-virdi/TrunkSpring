package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementStatements")
public class SettlementStmt {

	@Id
	private String id;
	
	private String MerchantID;
	private String CardPAN;
	private String CardExpiry;
	private String BankReference;
	private double PrincipalAmount;
	private double Surcharge;
	private String Currency;
	private String CustomerName;
	private String ResponseText;
	private long ReceiptNumber;
	private String SettlementDate;
	private String CardSchemeName;
	private String TransactionTimeStamp;
	private String Status;
	
	public SettlementStmt(String merchantID, String cardPAN, String cardExpiry, String bankReference, double principalAmount,
			double surcharge, String currency, String customerName, String responseText, long receiptNumber, String settlementDate,
			String cardSchemeName, String transactionTimeStamp, String status) {
		super();
		MerchantID = merchantID;
		CardPAN = cardPAN;
		CardExpiry = cardExpiry;
		BankReference = bankReference;
		PrincipalAmount = principalAmount;
		Surcharge = surcharge;
		Currency = currency;
		CustomerName = customerName;
		ResponseText = responseText;
		ReceiptNumber = receiptNumber;
		SettlementDate = settlementDate;
		CardSchemeName = cardSchemeName;
		TransactionTimeStamp = transactionTimeStamp;
		Status = status;
	}

	public String getMerchantID() {
		return MerchantID;
	}

	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}

	public String getCardPAN() {
		return CardPAN;
	}

	public void setCardPAN(String cardPAN) {
		CardPAN = cardPAN;
	}

	public String getCardExpiry() {
		return CardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		CardExpiry = cardExpiry;
	}
	
	public String getBankReference() {
		return BankReference;
	}
	
	public void setBankReference(String bankReference) {
		BankReference = bankReference;
	}

	public double getPrincipalAmount() {
		return PrincipalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		PrincipalAmount = principalAmount;
	}

	public double getSurcharge() {
		return Surcharge;
	}

	public void setSurcharge(double surcharge) {
		Surcharge = surcharge;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getResponseText() {
		return ResponseText;
	}

	public void setResponseText(String responseText) {
		ResponseText = responseText;
	}

	public long getReceiptNumber() {
		return ReceiptNumber;
	}

	public void setReceiptNumber(long receiptNumber) {
		ReceiptNumber = receiptNumber;
	}

	public String getSettlementDate() {
		return SettlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		SettlementDate = settlementDate;
	}

	public String getCardSchemeName() {
		return CardSchemeName;
	}

	public void setCardSchemeName(String cardSchemeName) {
		CardSchemeName = cardSchemeName;
	}

	public String getTransactionTimeStamp() {
		return TransactionTimeStamp;
	}

	public void setTransactionTimeStamp(String transactionTimeStamp) {
		TransactionTimeStamp = transactionTimeStamp;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getId() {
		return id;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	
	
	
}
