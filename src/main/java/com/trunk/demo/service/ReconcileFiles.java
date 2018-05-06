package com.trunk.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.trunk.demo.model.s3.SettlementDocument;
import com.trunk.demo.model.s3.TransactionItem;

public class ReconcileFiles {
	private SettlementDocument settlementDocument;
	private BankStatement bankStatement;
	
	public ReconcileFiles() {
		
	}
	
	public void setSettlementDocument (SettlementDocument settlementDocument) {
		this.settlementDocument = settlementDocument;
	}
	
	public void setBankStatement (BankStatement bankStatement) {
		this.bankStatement = bankStatement;
	}
	
	public void reconcileDocuments() {
		Map<String, Float> amexTotals = this.addUpSameDayTransactions(settlementDocument.getAmexTransactions());
		Map<String, Float> visaTotals = this.addUpSameDayTransactions(settlementDocument.getVisaTransactions());
		Map<String, Float> mastercardTotals = this.addUpSameDayTransactions(settlementDocument.getMastercardTransactions());
		Map<String, Float> bankTransferTotals = this.addUpSameDayTransactions(settlementDocument.getBankTransferTransactions());
		
		
	}
	
	private void reconcileItems(Map<String, Float> totals) {
		
	}
	
	private Map<String, Float> addUpSameDayTransactions(TransactionItem[] creditCardTransactions) {
		Map<String, Float> transactionAmountsByDate = new HashMap<String, Float>();
		
		if (creditCardTransactions == null)
			return null;
		
		for (int i = 0; i < creditCardTransactions.length; i++) {
			Float amount = transactionAmountsByDate.get(creditCardTransactions[i].getSettlementDate());
			if (amount != null) {
				transactionAmountsByDate.put(creditCardTransactions[i].getSettlementDate(), amount + creditCardTransactions[i].getAmount());
			} else {
				transactionAmountsByDate.put(creditCardTransactions[i].getSettlementDate(), creditCardTransactions[i].getAmount());
			}
		}
		
		return transactionAmountsByDate;
	}
	
}
