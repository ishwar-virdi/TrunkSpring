package com.trunk.demo.model.s3;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class CreditCardTransaction extends TransactionItem {
	private CardScheme cardScheme;
	
	public CreditCardTransaction(UUID uniqueId, float amount, String currency, String settlementDate, String status, CardScheme cardScheme, boolean isReconciled) {
		this.uniqueId = uniqueId;
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = isReconciled;
		this.cardScheme = cardScheme;
	}
	
	public CreditCardTransaction(float amount, String currency, String settlementDate, String status, CardScheme cardScheme) {
		this.uniqueId = UUID.randomUUID();
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = false;
		this.cardScheme = cardScheme;
	}
	
	public CardScheme getCardScheme() {
		return this.cardScheme;
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
			response.put("cardScheme", this.cardScheme);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
