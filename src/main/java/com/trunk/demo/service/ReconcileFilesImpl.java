package com.trunk.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		// Grabs the records it wants to work with from MongoDB
		List<SettlementStmt> amexTransactions = settlementStmtRepo.findAllByCardSchemeNameAmex();
		List<SettlementStmt> visaMastercardTransactions = settlementStmtRepo.findAllByCardSchemeNameVisaOrMastercard();
		List<SettlementStmt> directDebitTransactions = settlementStmtRepo.findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty();
		List<BankStmt> bankStatement = bankStmtRepo.findAll();

		// Work out transaction totals for different card types for each day
		Map<Date, Double> amexTotals = addUpSameDayTransactions(amexTransactions);
		Map<Date, Double> visaMastercardTotals = addUpSameDayTransactions(visaMastercardTransactions);

		// Reconciles the items
		List<Date> reconciledAmex = reconcileItems(amexTotals, bankStatement);
		List<Date> reconciledVisaMastercard = reconcileItems(visaMastercardTotals, bankStatement);
		List<Date> reconciledDirectDebit = reconcileDirectDebitItems(directDebitTransactions, bankStatement);

		List<SettlementStmt> finalAmex = matchReconciledWithSettlementItems(reconciledAmex, amexTransactions);
		List<SettlementStmt> finalVisaMastercard = matchReconciledWithSettlementItems(reconciledVisaMastercard,
				visaMastercardTransactions);
		List<SettlementStmt> finalDirectDebit = matchReconciledWithSettlementItems(reconciledDirectDebit, directDebitTransactions);
		
		// Save the results to the db
		settlementStmtRepo.saveAll(finalAmex);
		settlementStmtRepo.saveAll(finalVisaMastercard);
		settlementStmtRepo.saveAll(finalDirectDebit);
	}

	private List<Date> reconcileDirectDebitItems(List<SettlementStmt> directDebitTransactions,
			List<BankStmt> bankStatement) {
		List<Date> response = new ArrayList<Date>();

		for (SettlementStmt eachDirectDebit : directDebitTransactions)
			for (BankStmt eachStmt : bankStatement) {
				if (eachStmt.getDate().equals(eachDirectDebit.getSettlementDate())) {
					System.out.println("MATCH");
					System.out.println(eachDirectDebit.getBankReference().replaceAll("\\s+",""));
					System.out.println(eachStmt.getTransactionDescription().replaceAll("\\s+",""));
					System.out.println(eachStmt.getCredits());
					System.out.println(eachDirectDebit.getPrincipalAmount());
				}
				if (eachStmt.getDate().equals(eachDirectDebit.getSettlementDate()) && eachStmt.getCredits() == eachDirectDebit.getPrincipalAmount()
						&& eachStmt.getTransactionDescription().replaceAll("\\s+","").contains(eachDirectDebit.getBankReference().replaceAll("\\s+",""))) {
					response.add(eachStmt.getDate());
					break;
				}
			}
		return response;
	}

	private List<SettlementStmt> matchReconciledWithSettlementItems(List<Date> reconciledDatesList,
			List<SettlementStmt> filteredSettlementStmtList) {

		List<SettlementStmt> modifiedSettlementStmtList = new ArrayList<SettlementStmt>();

		for (SettlementStmt eachStmt : filteredSettlementStmtList) {
			for (Date eachReconciledDate : reconciledDatesList) {
				if (eachStmt.getSettlementDate().equals(eachReconciledDate)) {
					eachStmt.setReconcileStatus(3);
					eachStmt.setReconciledDateTime(new Date());
					break;
				}
			}
			if (eachStmt.getReconcileStatus() != 3) {
				eachStmt.setReconcileStatus(2);
				eachStmt.setReconciledDateTime(new Date());
			}
			modifiedSettlementStmtList.add(eachStmt);
		}

		return modifiedSettlementStmtList;
	}

	private List<Date> reconcileItems(Map<Date, Double> totals, List<BankStmt> bankStatementList) {
		List<Date> response = new ArrayList<Date>();

		for (Date eachDate : totals.keySet())
			for (BankStmt eachStmt : bankStatementList) {
				if (eachStmt.getDate().equals(eachDate)
						&& eachStmt.getCredits() == totals.get(eachDate).doubleValue()) {
					response.add(eachStmt.getDate());
					break;
				}
			}
		return response;
	}

	private Map<Date, Double> addUpSameDayTransactions(List<SettlementStmt> filteredSettlementStmtList) {
		Map<Date, Double> transactionAmountsByDate = new HashMap<Date, Double>();

		for (SettlementStmt eachStmt : filteredSettlementStmtList) {
			Double amount = transactionAmountsByDate.get(eachStmt.getSettlementDate());
			if (amount != null)
				transactionAmountsByDate.put(eachStmt.getSettlementDate(), amount + eachStmt.getPrincipalAmount());
			else
				transactionAmountsByDate.put(eachStmt.getSettlementDate(), eachStmt.getPrincipalAmount());
		}

		return transactionAmountsByDate;
	}

	@Override
	public void reset() throws ParseException {
		List<SettlementStmt> settlementDocument = settlementStmtRepo.findAll();

		for (SettlementStmt eachSettlementStmt : settlementDocument) {
			eachSettlementStmt.setReconcileStatus(0);
			eachSettlementStmt.setReconciledDateTime(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"));
		}

		this.settlementStmtRepo.saveAll(settlementDocument);
	}
}
