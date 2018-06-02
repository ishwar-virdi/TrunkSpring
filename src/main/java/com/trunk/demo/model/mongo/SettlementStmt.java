package com.trunk.demo.model.mongo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementStatements")
public class SettlementStmt implements Serializable {

	@Id
	private String id;
	
	@Indexed
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
	private Date settlementDate;
	private String cardSchemeName;
	private Date transactionTimeStamp;
	private String status;
	private int reconcileStatus;
	private Date reconciledDateTime;
	private String reconcileResultsId;

	public SettlementStmt(String merchantID, String cardPAN, String cardExpiry, String bankReference,
			double principalAmount, double surcharge, String currency, String customerName, String responseText,
			long receiptNumber, String settlementDate, String cardSchemeName, String transactionTimeStamp,
			String status, String reconcileResultsId) throws ParseException {
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

		this.transactionTimeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(transactionTimeStamp);
		this.settlementDate = new SimpleDateFormat("yyyyMMdd").parse(settlementDate);

		this.cardSchemeName = cardSchemeName;
		this.status = status;
		this.reconcileStatus = 0;

		this.reconciledDateTime = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990");
		this.reconcileResultsId = reconcileResultsId;
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

	public void setReconciledDateTime(Date reconciledDateTime) {
		this.reconciledDateTime = reconciledDateTime;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Date getTransactionTimeStamp() {
		return transactionTimeStamp;
	}

	public void setTransactionTimeStamp(Date transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public int getReconcileStatus() {
		return reconcileStatus;
	}

	public void setReconcileStatus(int reconcileStatus) {
		this.reconcileStatus = reconcileStatus;
	}

	public SettlementStmt(String id, Long receiptNumber, String merchantID, String cardPAN, String cardExpiry,
			String bankReference, double principalAmount, double surcharge, String currency, String customerName,
			String responseText, Date settlementDate, String cardSchemeName, Date transactionTimeStamp, String status,
			int reconcileStatus, Date reconciledDateTime, String reconcileResultsId) {
		super();
		this.id = id;
		this.receiptNumber = receiptNumber;
		this.merchantID = merchantID;
		this.cardPAN = cardPAN;
		this.cardExpiry = cardExpiry;
		this.bankReference = bankReference;
		this.principalAmount = principalAmount;
		this.surcharge = surcharge;
		this.currency = currency;
		this.customerName = customerName;
		this.responseText = responseText;
		this.settlementDate = settlementDate;
		this.cardSchemeName = cardSchemeName;
		this.transactionTimeStamp = transactionTimeStamp;
		this.status = status;
		this.reconcileStatus = reconcileStatus;
		this.reconciledDateTime = reconciledDateTime;
		this.reconcileResultsId = reconcileResultsId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SettlementStmt() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getReconcileResultsId() {
		return this.reconcileResultsId;
	}
	
	public void setReconcileResultsId(String reconcileResultsId) {
		this.reconcileResultsId = reconcileResultsId;
	}

	@Override
	public String toString() {
		return "SettlementStmt [id=" + id + ", receiptNumber=" + receiptNumber + ", merchantID=" + merchantID
				+ ", cardPAN=" + cardPAN + ", cardExpiry=" + cardExpiry + ", bankReference=" + bankReference
				+ ", principalAmount=" + principalAmount + ", surcharge=" + surcharge + ", currency=" + currency
				+ ", customerName=" + customerName + ", responseText=" + responseText + ", settlementDate="
				+ settlementDate + ", cardSchemeName=" + cardSchemeName + ", transactionTimeStamp="
				+ transactionTimeStamp + ", status=" + status + ", reconcileStatus=" + reconcileStatus
				+ ", reconciledDateTime=" + reconciledDateTime + "]";
	}
	
}
