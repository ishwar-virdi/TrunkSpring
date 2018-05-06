package com.trunk.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.s3.BankStatement;
import com.trunk.demo.model.s3.BankStatementTransaction;
import com.trunk.demo.model.s3.BankTransferTransaction;
import com.trunk.demo.model.s3.CardScheme;
import com.trunk.demo.model.s3.CreditCardTransaction;
import com.trunk.demo.model.s3.SettlementDocument;
import com.trunk.demo.model.s3.TransactionItem;

@Service
public class ReconcileFilesImpl implements ReconcileFiles {
	private SettlementDocument settlementDocument;
	private BankStatement bankStatement;
	
	
	public ReconcileFilesImpl() {
		CreditCardTransaction[] amex = {
				new CreditCardTransaction(1375, "AUD", "20180321", "Approved", CardScheme.AMEX),
				new CreditCardTransaction(4125, "AUD", "20180321", "Approved", CardScheme.AMEX)
		};
		
		CreditCardTransaction[] visa = {
				new CreditCardTransaction(100, "AUD", "20180301", "Approved", CardScheme.VISA),
				new CreditCardTransaction(140, "AUD", "20180305", "Approved", CardScheme.VISA),
				new CreditCardTransaction(140, "AUD", "20180305", "Approved", CardScheme.VISA),
				new CreditCardTransaction(140, "AUD", "20180305", "Approved", CardScheme.VISA),
				new CreditCardTransaction(140, "AUD", "20180305", "Approved", CardScheme.VISA),
				new CreditCardTransaction(140, "AUD", "20180305", "Approved", CardScheme.VISA),
				new CreditCardTransaction(275, "AUD", "20180308", "Approved", CardScheme.VISA),
				new CreditCardTransaction(275, "AUD", "20180309", "Approved", CardScheme.VISA),
				new CreditCardTransaction(250, "AUD", "20180327", "Approved", CardScheme.VISA)
		};
		
		CreditCardTransaction[] mastercard = new CreditCardTransaction[0];
		
		BankTransferTransaction[] bank = {
				new BankTransferTransaction(275, "AUD", "20180301", "Approved", 3202422713d),
				new BankTransferTransaction(140, "AUD", "20180305", "Approved", 3202422713d),
				new BankTransferTransaction(275, "AUD", "20180315", "Approved", 3202422713d)
		};
		
		ArrayList<BankStatementTransaction> bankStatement = new ArrayList<BankStatementTransaction>();
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "9032018", "MERCHANT SETTLEMENT     0796399 AILO HOLDINGS PTY LTD    SYDNEY       AU", 275));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "9032018", "DEPOSIT EVENTBRITE, INC.        38800722004", 9084.2f));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "9032018", "DEPOSIT EVENTBRITE, INC.        42110713277", 126.48f));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "8032018", "MERCHANT SETTLEMENT     0939793 AILO HOLDINGS PTY LTD    SYDNEY       AU", 275));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "8032018", "WITHDRAWAL FOR 6467073 REFUND TO M RESIDENTIAL", 0));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "7032018", "DEPOSIT Elaine Mills Pro        24 20180307", 140));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "7032018", "WITHDRAWAL FOR 6425983 CASH FLOW TOP", 0));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "7032018", "PAYMENT BY AUTHORITY TO AMERICAN EXPRESS        AUSTRALIA LIMITED AMEX FEE DEBIT          8031923303058QAQLU", 0));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "5032018", "DEPOSIT DE DRAW DECRBAL DE DRAW ID518431 APMASPHERIC DIRECT DEBIT", 140));
		bankStatement.add(new BankStatementTransaction(32024322713d, "AUD", "5032018", "MERCHANT SETTLEMENT     0808810 AILO HOLDINGS PTY LTD    SYDNEY       AU", 700));
		
		this.settlementDocument = new SettlementDocument(amex, visa, mastercard, bank);
		this.bankStatement = new BankStatement(bankStatement);
	}
	
	public void setSettlementDocument (SettlementDocument settlementDocument) {
		this.settlementDocument = settlementDocument;
	}
	
	public void setBankStatement (BankStatement bankStatement) {
		this.bankStatement = bankStatement;
	}
	
	public JSONObject reconcileDocuments() {
		JSONObject response = new JSONObject();
		
		Map<String, Float> amexTotals = this.addUpSameDayTransactions(settlementDocument.getAmexTransactions());
		Map<String, Float> visaTotals = this.addUpSameDayTransactions(settlementDocument.getVisaTransactions());
		Map<String, Float> mastercardTotals = this.addUpSameDayTransactions(settlementDocument.getMastercardTransactions());
		Map<String, Float> bankTransferTotals = this.addUpSameDayTransactions(settlementDocument.getBankTransferTransactions());
		System.out.println(amexTotals);
		System.out.println(visaTotals);
		System.out.println(mastercardTotals);
		System.out.println(bankTransferTotals);
		
		ArrayList<String> reconciledAmex = this.reconcileItems(amexTotals);
		ArrayList<String> reconciledVisa = this.reconcileItems(visaTotals);
		ArrayList<String> reconciledMastercard = this.reconcileItems(mastercardTotals);
		ArrayList<String> reconciledBankTransfer = this.reconcileItems(bankTransferTotals);
		System.out.println(reconciledAmex);
		System.out.println(reconciledVisa);
		System.out.println(reconciledMastercard);
		System.out.println(reconciledBankTransfer);
		
		settlementDocument.setAmexTransactions((CreditCardTransaction[]) this.matchReconciledWithSettlementItems(reconciledAmex, settlementDocument.getAmexTransactions()));
		settlementDocument.setVisaTransactions((CreditCardTransaction[]) this.matchReconciledWithSettlementItems(reconciledVisa, settlementDocument.getVisaTransactions()));
		settlementDocument.setMastercardTransactions((CreditCardTransaction[]) this.matchReconciledWithSettlementItems(reconciledMastercard, settlementDocument.getMastercardTransactions()));
		settlementDocument.setBankTransferTransactions((BankTransferTransaction[]) this.matchReconciledWithSettlementItems(reconciledBankTransfer, settlementDocument.getBankTransferTransactions()));
		
		try {
			response.put("Amex", settlementDocument.getAmexTransactionsJSON());
			response.put("Visa", settlementDocument.getVisaTransactionsJSON());
			response.put("Mastercard", settlementDocument.getMastercardTransactionsJSON());
			response.put("BankTransfer", settlementDocument.getBankTransactionsJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	private TransactionItem[] matchReconciledWithSettlementItems(ArrayList<String> dates, TransactionItem[] transactionItems) {
		for (int i = 0; i < transactionItems.length; i++) {
			for (int x = 0; x < dates.size(); x++) {
				if (transactionItems[i].getReversedDate().equals(dates.get(x))) {
					transactionItems[i].setIsReconciled(true);
					break;
				}
			}
		}
		
		return transactionItems;
	}
	
	private ArrayList<String> reconcileItems(Map<String, Float> totals) {
		ArrayList<BankStatementTransaction> bankTransactions = bankStatement.getTransactions();
		ArrayList<String> response = new ArrayList<String>();
		
		for (Map.Entry<String, Float> entry : totals.entrySet()) {
			System.out.println(entry);
			for (int i = 0; i < bankTransactions.size(); i++) {
				if (bankTransactions.get(i).getDate().equals(entry.getKey())) {
					response.add(bankTransactions.get(i).getDate());
					break;
				}
			}
		}
		
		return response;
	}
	
	private Map<String, Float> addUpSameDayTransactions(TransactionItem[] creditCardTransactions) {
		Map<String, Float> transactionAmountsByDate = new HashMap<String, Float>();
		
		if (creditCardTransactions == null)
			return null;
		
		for (int i = 0; i < creditCardTransactions.length; i++) {
			Float amount = transactionAmountsByDate.get(creditCardTransactions[i].getReversedDate());
			if (amount != null) {
				transactionAmountsByDate.put(creditCardTransactions[i].getReversedDate(), amount + creditCardTransactions[i].getAmount());
			} else {
				transactionAmountsByDate.put(creditCardTransactions[i].getReversedDate(), creditCardTransactions[i].getAmount());
			}
		}
		
		return transactionAmountsByDate;
	}
	
}
