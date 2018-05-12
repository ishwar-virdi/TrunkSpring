package com.trunk.demo.model.s3;

import java.util.ArrayList;

import org.json.JSONArray;

public class SettlementDocument {
	private CreditCardTransaction[] amexTransactions;
	private CreditCardTransaction[] visaMastercardTransactions;
	private BankTransferTransaction[] bankTransferTransactions;
	
	public SettlementDocument(CreditCardTransaction[] amexTransactions, CreditCardTransaction[] visaMastercardTransactions, BankTransferTransaction[] bankTransferTransactions) {
		this.amexTransactions = amexTransactions;
		this.visaMastercardTransactions = visaMastercardTransactions;
		this.bankTransferTransactions = bankTransferTransactions;
	}
	
	public CreditCardTransaction[] getAmexTransactions() {
		return this.amexTransactions;
	}
	
	public CreditCardTransaction[] getVisaMastercardTransactions() {
		return this.visaMastercardTransactions;
	}
	
	public BankTransferTransaction[] getBankTransferTransactions() {
		return this.bankTransferTransactions;
	}
	
	public void setAmexTransactions(CreditCardTransaction[] amexTransactions) {
		this.amexTransactions = amexTransactions;
	}
	
	public void setVisaTransactions(CreditCardTransaction[] visaMastercardTransactions) {
		this.visaMastercardTransactions = visaMastercardTransactions;
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
	
	public JSONArray getVisaMastercardTransactionsJSON() {
		JSONArray response = new JSONArray();
		
		for (int i = 0; i < this.visaMastercardTransactions.length; i++) {
			response.put(this.visaMastercardTransactions[i].getJson());
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
	
	public ArrayList<String> getBankStatementStrings() {
		ArrayList<String> response = new ArrayList<String>();
				
		for (int i = 0; i < this.bankTransferTransactions.length; i++) {
			response.add(bankTransferTransactions[i].getBankStatementReference().replaceAll("\\s",""));
		}
		
		return response;
	}
}
