package com.trunk.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.repository.UsersRepository;

@Service
public class ReconcileFilesImpl implements ReconcileFiles {
	private int reconciledCount = 0;
	private int transactionCount = 0;
	
	@Autowired
	private BankStmtRepository bankStmtRepo;
	
	@Autowired
	private SettlementRepository settlementStmtRepo;
	
	@Autowired
	private ResultsRepository reconcileResultsRepo;
	
	@Autowired
	private UsersRepository usersRepo;

	@Override
	public void reconcile() {
		// Grabs the records it wants to work with from MongoDB
		List<SettlementStmt> amexTransactions = settlementStmtRepo.findAllByCardSchemeNameAmex();
		List<SettlementStmt> visaMastercardTransactions = settlementStmtRepo.findAllByCardSchemeNameVisaOrMastercard();
		List<SettlementStmt> directDebitTransactions = settlementStmtRepo.findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty();
		List<BankStmt> bankStatement = bankStmtRepo.findAll();
		this.transactionCount = amexTransactions.size() + visaMastercardTransactions.size() + directDebitTransactions.size();

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
		
		List<User> users = usersRepo.findByUsername("test@test.com");
		//Create the reconcile results object
		ReconcileResult result = new ReconcileResult(users.get(0).getId(), findEarliestDate(amexTransactions, visaMastercardTransactions, directDebitTransactions), findLatestDate(amexTransactions, visaMastercardTransactions, directDebitTransactions), 
				this.reconciledCount, this.transactionCount - this.reconciledCount);
		
		//Get the id from the reconcile results object
		String reconcileResultsId = result.getId();
		
		//Set each transaction item here with that reconcile result's object ID
		setReconcileResultsId(finalAmex, reconcileResultsId);
		setReconcileResultsId(finalVisaMastercard, reconcileResultsId);
		setReconcileResultsId(finalDirectDebit, reconcileResultsId);
		
		// Save the results to the db
		settlementStmtRepo.saveAll(finalAmex);
		settlementStmtRepo.saveAll(finalVisaMastercard);
		settlementStmtRepo.saveAll(finalDirectDebit);

		reconcileResultsRepo.save(result);
	}
	
	private void setReconcileResultsId(List<SettlementStmt> list, String id) {
		for (SettlementStmt item : list) {
			item.setReconcileResultsId(id);
		}
	}
	
	private Date findEarliestDate(List<SettlementStmt> amexTransactions, List<SettlementStmt> visaMastercardTransactions, List<SettlementStmt> directDebitTransactions) {
		Date earliestDate;
		
		if (amexTransactions.size() != 0)
			earliestDate = amexTransactions.get(0).getSettlementDate();
		else if (visaMastercardTransactions.size() != 0)
			earliestDate = visaMastercardTransactions.get(0).getSettlementDate();
		else if (directDebitTransactions.size() != 0)
			earliestDate = directDebitTransactions.get(0).getSettlementDate();
		else 
			return null;
		
		for (SettlementStmt item : amexTransactions) {
			if (item.getSettlementDate().before(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		for (SettlementStmt item : visaMastercardTransactions) {
			if (item.getSettlementDate().before(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		for (SettlementStmt item : directDebitTransactions) {
			if (item.getSettlementDate().before(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		return earliestDate;
	}
	
	private Date findLatestDate(List<SettlementStmt> amexTransactions, List<SettlementStmt> visaMastercardTransactions, List<SettlementStmt> directDebitTransactions) {
		Date earliestDate;
		
		if (amexTransactions.size() != 0)
			earliestDate = amexTransactions.get(0).getSettlementDate();
		else if (visaMastercardTransactions.size() != 0)
			earliestDate = visaMastercardTransactions.get(0).getSettlementDate();
		else if (directDebitTransactions.size() != 0)
			earliestDate = directDebitTransactions.get(0).getSettlementDate();
		else 
			return null;
		
		for (SettlementStmt item : amexTransactions) {
			if (item.getSettlementDate().after(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		for (SettlementStmt item : visaMastercardTransactions) {
			if (item.getSettlementDate().after(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		for (SettlementStmt item : directDebitTransactions) {
			if (item.getSettlementDate().after(earliestDate))
				earliestDate = item.getSettlementDate();
		}
		
		return earliestDate;
	}

	private List<Date> reconcileDirectDebitItems(List<SettlementStmt> directDebitTransactions,
			List<BankStmt> bankStatement) {
		List<Date> response = new ArrayList<Date>();

		for (SettlementStmt eachDirectDebit : directDebitTransactions)
			for (BankStmt eachStmt : bankStatement) {
				if (eachStmt.getDate().equals(eachDirectDebit.getSettlementDate()) && eachStmt.getCredits() == eachDirectDebit.getPrincipalAmount()
						&& eachStmt.getTransactionDescription().replaceAll("\\s+","").contains(eachDirectDebit.getBankReference().replaceAll("\\s+",""))) {
					response.add(eachStmt.getDate());
					this.reconciledCount++;
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
					this.reconciledCount++;
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
