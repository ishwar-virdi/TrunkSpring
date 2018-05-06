package com.trunk.demo.model.s3;

import org.json.JSONArray;

public class SettlementDocument {
	private CreditCardTransaction[] amexTransactions;
	private CreditCardTransaction[] visaTransactions;
	private CreditCardTransaction[] mastercardTransactions;
	private BankTransferTransaction[] bankTransferTransactions;
	
	public SettlementDocument(CreditCardTransaction[] amexTransactions, CreditCardTransaction[] visaTransactions, CreditCardTransaction[] mastercardTransactions, BankTransferTransaction[] bankTransferTransactions) {
		this.amexTransactions = amexTransactions;
		this.visaTransactions = visaTransactions;
		this.mastercardTransactions = mastercardTransactions;
		this.bankTransferTransactions = bankTransferTransactions;
	}
	
	public CreditCardTransaction[] getAmexTransactions() {
		return this.amexTransactions;
	}
	
	public CreditCardTransaction[] getVisaTransactions() {
		return this.visaTransactions;
	}
	
	public CreditCardTransaction[] getMastercardTransactions() {
		return this.mastercardTransactions;
	}
	
	public BankTransferTransaction[] getBankTransferTransactions() {
		return this.bankTransferTransactions;
	}
	
	public void setAmexTransactions(CreditCardTransaction[] amexTransactions) {
		this.amexTransactions = amexTransactions;
	}
	
	public void setVisaTransactions(CreditCardTransaction[] visaTransactions) {
		this.visaTransactions = visaTransactions;
	}
	
	public void setMastercardTransactions(CreditCardTransaction[] mastercardTransactions) {
		this.mastercardTransactions = mastercardTransactions;
	}
	
	public void setBankTransferTransactions(BankTransferTransaction[] bankTransferTransactions) {
		this.bankTransferTransactions = bankTransferTransactions;
	}
	
	public JSONArray getAmexTransactionsJSON() {
		JSONArray response = new JSONArray();
		
		for (int i = 0; i < this.amexTransactions.length; i++) {
			response.put(this.amexTransactions[i].getJson());
		}
		
		return response;
	}
	
	public JSONArray getVisaTransactionsJSON() {
		JSONArray response = new JSONArray();
		
		for (int i = 0; i < this.visaTransactions.length; i++) {
			response.put(this.visaTransactions[i].getJson());
		}
		
		return response;
	}
	
	public JSONArray getMastercardTransactionsJSON() {
		JSONArray response = new JSONArray();
		
		for (int i = 0; i < this.mastercardTransactions.length; i++) {
			response.put(this.mastercardTransactions[i].getJson());
		}
		
		return response;
	}
	
	public JSONArray getBankTransactionsJSON() {
		JSONArray response = new JSONArray();
		
		for (int i = 0; i < this.bankTransferTransactions.length; i++) {
			response.put(this.bankTransferTransactions[i].getJson());
		}
		
		return response;
	}
}
