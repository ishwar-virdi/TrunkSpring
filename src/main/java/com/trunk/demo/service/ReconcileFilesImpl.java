package com.trunk.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.BankStmtRepository;
import com.trunk.demo.interfaces.mongo.SettlementRepository;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class ReconcileFilesImpl implements ReconcileFiles {

	@Autowired
	private BankStmtRepository bankStmtRepo;
	@Autowired
	private SettlementRepository settlementStmtRepo;
	
	private List<SettlementStmt> settlementDocument;
	private List<BankStmt> bankStatement;
	
	@Override
	public void reconcileDocuments() {
		String month = "201803";
		JSONObject response = new JSONObject();
		
		//Grabs the records it wants to work with from MongoDB
		System.out.println("Bank Statement");
		bankStatement = bankStmtRepo.findAllByDateLike("^" + month + "\\d*");
		for (int i = 0; i < bankStatement.size(); i++)
			System.out.println(bankStatement.get(i).getTransactionDescription());
		
		System.out.println("Settlement Document");
		settlementDocument = settlementStmtRepo.findAllBySettlementDateLike("^" + month + "\\d*");
		for (int i = 0; i < settlementDocument.size(); i++)
			System.out.println(settlementDocument.get(i).getPrincipalAmount());
		
		ArrayList<SettlementStmt> amexTransactions = new ArrayList<SettlementStmt>();
		ArrayList<SettlementStmt> visaMastercardTransactions = new ArrayList<SettlementStmt>();
		
		
		//Separates the amex and visa/mastercard transactions from each other
		for (int i = 0; i < settlementDocument.size(); i++) {
			if (settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Amex"))
				amexTransactions.add(settlementDocument.get(i));
		}
		
		for (int i = 0; i < settlementDocument.size(); i++) {
			if (settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Visa") || settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Mastercard"))
				visaMastercardTransactions.add(settlementDocument.get(i));
		}
		
		
		//Work out transaction totals for different card types for each day
		Map<String, Double> amexTotals = this.addUpSameDayTransactions(amexTransactions);
		Map<String, Double> visaMastercardTotals = this.addUpSameDayTransactions(visaMastercardTransactions);
		
		System.out.println(amexTotals);
		System.out.println(visaMastercardTotals);
		
		
		//Reconciles the items
		ArrayList<String> reconciledAmex = this.reconcileItems(amexTotals, bankStatement);
		ArrayList<String> reconciledVisaMastercard = this.reconcileItems(visaMastercardTotals, bankStatement);
		
		System.out.println(reconciledAmex);
		System.out.println(reconciledVisaMastercard);
		
		ArrayList<SettlementStmt> finalAmex = this.matchReconciledWithSettlementItems(reconciledAmex, amexTransactions);
		ArrayList<SettlementStmt> finalVisaMastercard = this.matchReconciledWithSettlementItems(reconciledVisaMastercard, visaMastercardTransactions);	
		
		
		//Save the results to the db
		this.settlementStmtRepo.saveAll(finalAmex);
		this.settlementStmtRepo.saveAll(finalVisaMastercard);
	}
	
	private ArrayList<SettlementStmt> matchReconciledWithSettlementItems(ArrayList<String> dates, ArrayList<SettlementStmt> items) {
		for (int i = 0; i < items.size(); i++) {
			for (int x = 0; x < dates.size(); x++) {
				if (items.get(i).getSettlementDate().equals(dates.get(x))) {
					items.get(i).setIsReconciled(true);
					items.get(i).setReconciledDateTime(new Date());
					break;
				}
			}
		}
		
		return items;
	}
	
	private ArrayList<String> reconcileItems(Map<String, Double> totals, List<BankStmt> bankStatement2) {
		ArrayList<String> response = new ArrayList<String>();
		
		for (Map.Entry<String, Double> entry : totals.entrySet()) {
			for (int i = 0; i < bankStatement2.size(); i++) {
				if (bankStatement2.get(i).getDate().equals(entry.getKey())) {
					response.add(bankStatement2.get(i).getDate());
					break;
				}
			}
		}
		
		return response;
	}

	private Map<String, Double> addUpSameDayTransactions(ArrayList<SettlementStmt> transactions) {
		Map<String, Double> transactionAmountsByDate = new HashMap<String, Double>();
		
		for (int i = 0; i < transactions.size(); i++) {
			Double amount = transactionAmountsByDate.get(transactions.get(i).getSettlementDate());
			if (amount != null) {
				transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), amount + transactions.get(i).getPrincipalAmount());
			} else {
				transactionAmountsByDate.put(transactions.get(i).getSettlementDate(), transactions.get(i).getPrincipalAmount());
			}
		}
		
		return transactionAmountsByDate;
	}
	
/*

	public JSONObject reconcileDocuments() {
		JSONObject response = new JSONObject();
		
		this.bankStatement.extractBankTransferItems(this.settlementDocument.getBankStatementStrings());
		
		Map<String, Float> amexTotals = this.addUpSameDayTransactions(settlementDocument.getAmexTransactions());
		Map<String, Float> visaMastercardTotals = this.addUpSameDayTransactions(settlementDocument.getVisaMastercardTransactions());
		//Don't need this
		Map<String, Float> bankTransferTotals = this.addUpSameDayTransactions(settlementDocument.getBankTransferTransactions());
		
		ArrayList<String> reconciledAmex = this.reconcileItems(amexTotals, bankStatement.getCreditCardTransactions());
		ArrayList<String> reconciledVisa = this.reconcileItems(visaMastercardTotals, bankStatement.getCreditCardTransactions());
		ArrayList<String> reconciledBankTransfer = this.reconcileItems(bankTransferTotals, bankStatement.getBankTransferTransactions());
		
		settlementDocument.setAmexTransactions((CreditCardTransaction[]) this.matchReconciledWithSettlementItems(reconciledAmex, settlementDocument.getAmexTransactions()));
		settlementDocument.setVisaTransactions((CreditCardTransaction[]) this.matchReconciledWithSettlementItems(reconciledVisa, settlementDocument.getVisaMastercardTransactions()));		
		settlementDocument.setBankTransferTransactions((BankTransferTransaction[]) this.matchReconciledWithSettlementItems(reconciledBankTransfer, settlementDocument.getBankTransferTransactions()));
		
		try {
			response.put("Amex", settlementDocument.getAmexTransactionsJSON());
			response.put("VisaMastercard", settlementDocument.getVisaMastercardTransactionsJSON());
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
	}*/

	@Override
	public void setBankStatement(BankStmt bankStatement) {
		
	}

	@Override
	public void setSettlementDocument(SettlementStmt settlementDocument) {
		// TODO Auto-generated method stub
		
	}
	
}
