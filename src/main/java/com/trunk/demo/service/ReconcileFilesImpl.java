package com.trunk.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.repository.UsersRepository;

@Async
@Service
public class ReconcileFilesImpl implements ReconcileFiles {
	private int reconciledCount;
	private int transactionCount;

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
		reconciledCount = 0;
		transactionCount = 0;

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);

		List<SettlementStmt> amexTransactions = settlementStmtRepo.findAllByCardSchemeNameAmex(cal.getTime());
		List<SettlementStmt> visaMastercardTransactions = settlementStmtRepo
				.findAllByCardSchemeNameVisaOrMastercard(cal.getTime());
		List<SettlementStmt> directDebitTransactions = settlementStmtRepo
				.findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(cal.getTime());
		List<BankStmt> bankStatement = bankStmtRepo.findAll();

		transactionCount = amexTransactions.size() + visaMastercardTransactions.size() + directDebitTransactions.size();

		if (transactionCount <= 0 || bankStatement.size() <= 0)
			return;

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
		List<SettlementStmt> finalDirectDebit = matchReconciledWithSettlementItems(reconciledDirectDebit,
				directDebitTransactions);

		List<SettlementStmt> allSettlementList = new ArrayList<SettlementStmt>(finalAmex);
		allSettlementList.addAll(finalVisaMastercard);
		allSettlementList.addAll(finalDirectDebit);

		Map<Date, ArrayList<SettlementStmt>> monthBasedSettlementList = segregateBasedOnMonth(allSettlementList);

		insertOrUpdatingReconcileResults(monthBasedSettlementList);

	}

	private void insertOrUpdatingReconcileResults(Map<Date, ArrayList<SettlementStmt>> monthBasedSettlementList) {

		List<User> users = usersRepo.findByUsername("test@test.com");

		for (Date eachMonth : monthBasedSettlementList.keySet()) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(eachMonth);

			String reconcileResultID = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + "-"
					+ cal.get(Calendar.YEAR);

			if (reconcileResultsRepo.findById(reconcileResultID).isPresent()) {
				//
				// Need to update the Counters Here :(
				//
			} else {
				// Create the reconcile results object
				ReconcileResult result = new ReconcileResult(reconcileResultID, users.get(0).getId(),
						this.reconciledCount, this.transactionCount - this.reconciledCount);
				// Get the id from the reconcile results object
				String reconcileResultsId = result.getId();

				for (SettlementStmt eachStmt : monthBasedSettlementList.get(eachMonth)) {
					eachStmt.setReconcileResultsId(reconcileResultsId);
					settlementStmtRepo.save(eachStmt);
				}

				reconcileResultsRepo.save(result);
			}

		}

	}

	private Map<Date, ArrayList<SettlementStmt>> segregateBasedOnMonth(List<SettlementStmt> allSettlementList) {

		Map<Date, ArrayList<SettlementStmt>> segregatedList = new HashMap<Date, ArrayList<SettlementStmt>>();

		Calendar cal = Calendar.getInstance();
		for (SettlementStmt eachStmt : allSettlementList) {
			cal.setTime(eachStmt.getSettlementDate());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			if (segregatedList.get(cal.getTime()) == null) {
				ArrayList<SettlementStmt> tmp = new ArrayList<SettlementStmt>();
				tmp.add(eachStmt);
				segregatedList.put(cal.getTime(), tmp);
			} else {
				segregatedList.get(cal.getTime()).add(eachStmt);
			}
		}
		return segregatedList;
	}

	private List<Date> reconcileDirectDebitItems(List<SettlementStmt> directDebitTransactions,
			List<BankStmt> bankStatement) {
		List<Date> response = new ArrayList<Date>();

		for (SettlementStmt eachDirectDebit : directDebitTransactions)
			for (BankStmt eachStmt : bankStatement) {
				if (eachStmt.getDate().equals(eachDirectDebit.getSettlementDate())
						&& eachStmt.getCredits() == eachDirectDebit.getPrincipalAmount()
						&& eachStmt.getTransactionDescription().replaceAll("\\s+", "")
								.contains(eachDirectDebit.getBankReference().replaceAll("\\s+", ""))) {
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
			if (eachStmt.getReconcileStatus() != 3) {
				eachStmt.setReconcileStatus(1);
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