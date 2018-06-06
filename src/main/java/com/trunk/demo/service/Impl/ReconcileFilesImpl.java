package com.trunk.demo.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.trunk.demo.bo.BankStmtBO;
import com.trunk.demo.bo.ReconcileResultBO;
import com.trunk.demo.bo.SettlementBO;
import com.trunk.demo.service.ReconcileFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.repository.UsersRepository;

import javax.servlet.http.HttpSession;

@Async
@Service
public class ReconcileFilesImpl implements ReconcileFiles {
	private int reconciledCount;
	private int transactionCount;

	@Autowired
	private BankStmtBO bankStmtBO;

	@Autowired
	private SettlementBO settlementBO;

	@Autowired
	private ReconcileResultBO reconcileResultBO;

	@Autowired
	private UsersRepository usersRepo;

	@Value("${reconcileAlgo.limit}")
	private String limit;

	@Override
	public void reconcile(Set<Date> monthInvolved) {

		System.out.println("***Starting Reconcile Algortithm***");
		CalenderUtil calUtil = new CalenderUtil();
		Calendar cal = Calendar.getInstance();
		cal.setTime(calUtil.firstDayOfThisMonth(new Date()));
		for (int i = 0; i <= Integer.parseInt(limit); ++i) {
			monthInvolved.add(cal.getTime());
			cal.add(Calendar.MONTH, -1);
		}

		for (Date eachMonthStart : monthInvolved) {

			cal.setTime(eachMonthStart);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endOfMonth = cal.getTime();

			reconciledCount = 0;
			transactionCount = 0;

			System.out.println("***Finding Settlement Statments from " + eachMonthStart + " to " + endOfMonth + "***");

			List<SettlementStmt> amexTransactions = settlementBO.findAllByCardSchemeNameAmex(eachMonthStart,
					endOfMonth);
			List<SettlementStmt> visaMastercardTransactions = settlementBO
					.findAllByCardSchemeNameVisaOrMastercard(eachMonthStart, endOfMonth);
			List<SettlementStmt> directDebitTransactions = settlementBO
					.findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(eachMonthStart, endOfMonth);

			System.out.println("***Finding Bank Statments from " + eachMonthStart + " to " + endOfMonth + "***");
			List<BankStmt> bankStatement = bankStmtBO.findAllBetweenDates(eachMonthStart, endOfMonth);
			cal.setTime(eachMonthStart);
			cal.add(Calendar.MONTH, 1);
			Date nextMonthStart = cal.getTime();
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date nextMonthEnd = cal.getTime();
			System.out.println("***Finding Bank Statments from " + nextMonthStart + " to " + nextMonthEnd + "***");
			bankStatement.addAll(bankStmtBO.findAllBetweenDates(nextMonthStart, nextMonthEnd));

			transactionCount = amexTransactions.size() + visaMastercardTransactions.size()
					+ directDebitTransactions.size();

			if (transactionCount <= 0)
				continue;

			System.out.println("***Starting Reconcile Algortithm from " + eachMonthStart + " to " + endOfMonth + "***");

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

			insertOrUpdatingReconcileResults(eachMonthStart, allSettlementList);

			System.out.println("***Done Reconcile Algortithm from " + eachMonthStart + " to " + endOfMonth + "***");

		}

	}

	private void insertOrUpdatingReconcileResults(Date eachMonthStart, List<SettlementStmt> allSettlementList) {
		List<User> users = usersRepo.findByUsername("test@test.com");

		Calendar cal = Calendar.getInstance();
		CalenderUtil calUtil = new CalenderUtil();
		cal.setTime(eachMonthStart);

		String reconcileResultID = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).substring(0, 3)
				+ "-" + cal.get(Calendar.YEAR);

		ReconcileResult result = reconcileResultBO.findById(reconcileResultID);

		if (result != null) {
			result.setIsReconciled(reconciledCount);
			result.setNotReconciled(transactionCount - reconciledCount);
		} else{

			Date startDate = calUtil.firstDayOfMonthByString(reconcileResultID.replace("-",". "),"MMM yyyy");
			Date endDate = calUtil.EndDayOfMonthByString(reconcileResultID.replace("-",". "),"MMM yyyy");
			result = new ReconcileResult(reconcileResultID, users.get(0).getId(), reconciledCount,
					transactionCount - reconciledCount,startDate,endDate);
		}


		// Get the id from the reconcile results object
		String reconcileResultsId = result.getId();

		for (SettlementStmt eachStmt : allSettlementList) {
			eachStmt.setReconcileResultsId(reconcileResultsId);
			settlementBO.save(eachStmt);
		}

		reconcileResultBO.save(result);
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
					break;
				}
			}
		return response;
	}

	private List<SettlementStmt> matchReconciledWithSettlementItems(List<Date> reconciledDatesList,
			List<SettlementStmt> filteredSettlementStmtList) {

		List<SettlementStmt> modifiedSettlementStmtList = new ArrayList<SettlementStmt>();

		for (SettlementStmt eachStmt : filteredSettlementStmtList) {
			if (eachStmt.getReconcileStatus() == 2)
				this.reconciledCount++;
			else if (eachStmt.getReconcileStatus() != 4) {
				eachStmt.setReconcileStatus(1);
				eachStmt.setReconciledDateTime(new Date());
				for (Date eachReconciledDate : reconciledDatesList) {

					if (eachStmt.getSettlementDate().equals(eachReconciledDate)) {
						eachStmt.setReconcileStatus(3);
						eachStmt.setReconciledDateTime(new Date());
						this.reconciledCount++;
						break;
					}
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
		List<SettlementStmt> settlementDocument = settlementBO.findAll();

		for (SettlementStmt eachSettlementStmt : settlementDocument) {
			eachSettlementStmt.setReconcileStatus(0);
			eachSettlementStmt.setReconciledDateTime(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"));
		}

		this.settlementBO.saveAll(settlementDocument);
	}
}