package com.trunk.demo.model.s3;

import java.util.UUID;

public abstract class TransactionItem {
	protected UUID uniqueId;
	protected float amount;
	protected String currency;
	protected String settlementDate;
	protected String status;
	protected boolean isReconciled;
	
	public boolean compareCurrency(String otherCurrency) {
		return otherCurrency.equals(this.currency);
	}
	
	public UUID getUniqueId() {
		return this.uniqueId;
	}
	
	public float getAmount() {
		return this.amount;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String getSettlementDate() {
		return this.settlementDate;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public boolean getIsReconciled() {
		return this.isReconciled;
	}
	
	public void setIsReconciled(boolean isReconciled) {
		this.isReconciled = isReconciled;
	}
	
	public String getReversedDate() {
		String newDate = this.settlementDate.substring(6, 8) + this.settlementDate.substring(4, 6) + this.settlementDate.substring(0, 4);
		
		if (newDate.charAt(0) == '0') {
			newDate = newDate.substring(1, 8);
		}
		
		return newDate;
		
	}
}
