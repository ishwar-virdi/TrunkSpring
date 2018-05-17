package com.trunk.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public void reconcile() {
		List<SettlementStmt> settlementDocument;
		List<BankStmt> bankStatement;
		String month = "201803";
		
		//Grabs the records it wants to work with from MongoDB
		bankStatement = bankStmtRepo.findAllByDateLike("^" + month + "\\d*");
		settlementDocument = settlementStmtRepo.findAllBySettlementDateLike("^" + month + "\\d*");
		
		ArrayList<SettlementStmt> amexTransactions = new ArrayList<SettlementStmt>();
		ArrayList<SettlementStmt> visaMastercardTransactions = new ArrayList<SettlementStmt>();
		ArrayList<SettlementStmt> directDebitTransactions = new ArrayList<SettlementStmt>();
		
		
		//Separates the amex, visa/mastercard and direct debit transactions from each other
		for (int i = 0; i < settlementDocument.size(); i++) {
			if (settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Amex"))
				amexTransactions.add(settlementDocument.get(i));
			else if (settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Visa") || settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("Mastercard"))
				visaMastercardTransactions.add(settlementDocument.get(i));
			else if (settlementDocument.get(i).getCardSchemeName().equalsIgnoreCase("") && !settlementDocument.get(i).getBankReference().equalsIgnoreCase(""))
				directDebitTransactions.add(settlementDocument.get(i));
		}
		
		
		//Work out transaction totals for different card types for each day
		Map<String, Double> amexTotals = this.addUpSameDayTransactions(amexTransactions);
		Map<String, Double> visaMastercardTotals = this.addUpSameDayTransactions(visaMastercardTransactions);
		
		
		//Reconciles the items
		ArrayList<String> reconciledAmex = this.reconcileItems(amexTotals, bankStatement);
		ArrayList<String> reconciledVisaMastercard = this.reconcileItems(visaMastercardTotals, bankStatement);
		
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
					items.get(i).setReconciledDateTime(LocalDateTime.now());
					break;
				}
			}
		}
		
		return items;
	}
	
	private ArrayList<String> reconcileItems(Map<String, Double> totals, List<BankStmt> bankStatement) {
		ArrayList<String> response = new ArrayList<String>();
		
		for (Map.Entry<String, Double> entry : totals.entrySet()) {
			for (int i = 0; i < bankStatement.size(); i++) {
				if (bankStatement.get(i).getDate().equals(entry.getKey()) && bankStatement.get(i).getCredits() == entry.getValue()) {
					response.add(bankStatement.get(i).getDate());
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

	@Override
	public void reset() {
		List<SettlementStmt> settlementDocument = settlementStmtRepo.findAllBySettlementDateLike("^2018\\d*");
		
		for (int i = 0; i < settlementDocument.size(); i++) {
			settlementDocument.get(i).setIsReconciled(false);
			settlementDocument.get(i).setReconciledDateTime(null);
		}
		
		this.settlementStmtRepo.saveAll(settlementDocument);
	}
}
