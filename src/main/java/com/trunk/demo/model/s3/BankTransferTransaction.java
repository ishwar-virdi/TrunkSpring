package com.trunk.demo.model.s3;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class BankTransferTransaction extends TransactionItem {
	private double bankAccountNumber;
	
	public BankTransferTransaction(UUID uniqueId, float amount, String currency, String settlementDate, String status, double bankAccountNumber, boolean isReconciled) {
		this.uniqueId = uniqueId;
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = isReconciled;
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public BankTransferTransaction(float amount, String currency, String settlementDate, String status, double bankAccountNumber) {
		this.uniqueId = UUID.randomUUID();
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = false;
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public double getBankAccountNumber() {
		return this.bankAccountNumber;
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
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
