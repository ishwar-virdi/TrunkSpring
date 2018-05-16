package com.trunk.demo.model.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementStatements")
public class SettlementStmt {

	@Value("${zone}")
	private String zone;

	@Id
	private Long receiptNumber;

	private String merchantID;
	private String cardPAN;
	private String cardExpiry;
	private String bankReference;
	private double principalAmount;
	private double surcharge;
	private String currency;
	private String customerName;
	private String responseText;
	private LocalDate settlementDate;
	private String cardSchemeName;
	private LocalDateTime transactionTimeStamp;
	private String status;
	private int reconcileStatus;
	private LocalDateTime reconciledDateTime;

	public SettlementStmt(String merchantID, String cardPAN, String cardExpiry, String bankReference,
			double principalAmount, double surcharge, String currency, String customerName, String responseText,
			long receiptNumber, String settlementDate, String cardSchemeName, String transactionTimeStamp,
			String status) {
		super();

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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of(zone));
		this.transactionTimeStamp = LocalDateTime.parse(transactionTimeStamp, formatter);

		settlementDate = settlementDate + " 00:00";
		formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm").withZone(ZoneId.of(zone));
		this.settlementDate = LocalDate.parse(settlementDate, formatter);

		this.cardSchemeName = cardSchemeName;
		this.status = status;
		this.reconcileStatus = 0;

		String tmpDate = new String("19900101 00:00");
		this.reconciledDateTime = LocalDateTime.parse(tmpDate, formatter);
	}

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
		this.receiptNumber = receiptNumber;
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

	public String getCardSchemeName() {
		return cardSchemeName;
	}

	public void setCardSchemeName(String cardSchemeName) {
		this.cardSchemeName = cardSchemeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReconciledDateTime() {
		return reconciledDateTime.toString();
	}

	public void setReconciledDateTime(LocalDateTime reconciledDateTime) {
		this.reconciledDateTime = reconciledDateTime;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public LocalDateTime getTransactionTimeStamp() {
		return transactionTimeStamp;
	}

	public void setTransactionTimeStamp(LocalDateTime transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public int getReconcileStatus() {
		return reconcileStatus;
	}

	public void setReconcileStatus(int reconcileStatus) {
		this.reconcileStatus = reconcileStatus;
	}

}
