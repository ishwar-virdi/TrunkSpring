package com.trunk.demo.model.s3;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class BankTransferTransaction extends TransactionItem {
	private double bankAccountNumber;
	private String bankStatementReference;
	
	public BankTransferTransaction(UUID uniqueId, float amount, String currency, String settlementDate, String status, double bankAccountNumber, boolean isReconciled, String bankStatementReference) {
		this.uniqueId = uniqueId;
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = isReconciled;
		this.bankAccountNumber = bankAccountNumber;
		this.bankStatementReference = bankStatementReference;
	}
	
	public BankTransferTransaction(float amount, String currency, String settlementDate, String status, double bankAccountNumber, String bankStatementReference) {
		this.uniqueId = UUID.randomUUID();
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = false;
		this.bankAccountNumber = bankAccountNumber;
		this.bankStatementReference = bankStatementReference;
	}
	
	public double getBankAccountNumber() {
		return this.bankAccountNumber;
	}
	
	public String getBankStatementReference() {
		return this.bankStatementReference;
	}

	public JSONObject getJson() {
		JSONObject response = new JSONObject();
		
		try {
			response.put("uniqueId", this.uniqueId);
			response.put("amount", this.amount);
			response.put("currency", this.currency);
			response.put("settlementDate", this.settlementDate);
			response.put("status", this.status);
			response.put("isReconciled", this.isReconciled);
			response.put("bankAccountNumber", this.bankAccountNumber);
			response.put("bankStatementReference", bankStatementReference);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
