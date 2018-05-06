package com.trunk.demo.model.s3;

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
}
