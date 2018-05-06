package com.trunk.demo.model.s3;

import java.util.UUID;

public class BankTransferTransaction extends TransactionItem {
	private int bankAccountNumber;
	
	public BankTransferTransaction(UUID uniqueId, float amount, String currency, String settlementDate, String status, int bankAccountNumber, boolean isReconciled) {
		this.uniqueId = uniqueId;
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = isReconciled;
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public BankTransferTransaction(float amount, String currency, String settlementDate, String status, int bankAccountNumber) {
		this.uniqueId = UUID.randomUUID();
		this.amount = amount;
		this.currency = currency;
		this.settlementDate = settlementDate;
		this.status = status;
		this.isReconciled = false;
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public int getBankAccountNumber() {
		return this.bankAccountNumber;
	}
}
