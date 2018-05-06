package com.trunk.demo.model.s3;

import java.util.UUID;

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

}
