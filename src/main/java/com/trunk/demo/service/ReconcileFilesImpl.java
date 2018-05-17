package com.trunk.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.trunk.demo.Util.FormatUtil;
import com.trunk.demo.vo.ListBankStmtVO;
import com.trunk.demo.vo.ListSettlementStmtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.BankStmtRepository;
import com.trunk.demo.interfaces.mongo.SettlementRepository;
import com.trunk.demo.model.mongo.SettlementStmt;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class ReconcileFilesImpl implements ReconcileFiles {

	@Autowired
	private BankStmtRepository bankStmtRepo;
	@Autowired
	private SettlementRepository settlementStmtRepo;
	@Autowired
	private Gson gson;
	private FormatUtil formatUtil = new FormatUtil();
	@Override
	public void reconcile() {

		String month = "201803";

		//Grabs the records it wants to work with from MongoDB
		ListBankStmtVO listBankStmtVO = new ListBankStmtVO(bankStmtRepo.findAllByDateLike("^" + month + "\\d*"));
		ListSettlementStmtVO listSettlementStmtVO = new ListSettlementStmtVO(settlementStmtRepo.findAllBySettlementDateLike("^" + month + "\\d*"));

		//Filter BankStatement
		listBankStmtVO.filterBankStatement();

		//Separates the amex, visa/mastercard and direct debit transactions from each other
		listSettlementStmtVO.SeparateSettlement();

		//Work out transaction totals for different card types for each day
		Map<String, Double> bankStmtTotals = listBankStmtVO.addUpSameDayTransactions();
		Map<String, Double> amexTotals = listSettlementStmtVO.addUpSameDayTransactions("amax");
		Map<String, Double> visaMastercardTotals = listSettlementStmtVO.addUpSameDayTransactions("visaMaster");

		//get settlements in the date range
		Map<String, Double> visaTotalsInRange = listSettlementStmtVO.getOverviewRange(visaMastercardTotals,bankStmtTotals);
		Map<String, Double> amexTotalsInRange = listSettlementStmtVO.getOverviewRange(amexTotals,bankStmtTotals);

//		for (Map.Entry<String, Double> bankEntry : bankStmtTotals.entrySet()) {
//			System.out.println(bankEntry.getKey() + " " + bankEntry.getValue());
//		}

		Map<String, Double> visaMasterNotReonciled = notReconciledDate(bankStmtTotals,visaTotalsInRange);
		Map<String, Double> amexNotReonciled = notReconciledDate(bankStmtTotals,amexTotalsInRange);

		finishedReconcile(visaMasterNotReonciled,amexNotReonciled, listBankStmtVO);

		//Reconciles the items
//		ArrayList<String> reconciledAmex = this.reconcileItems(amexTotals, bankStatement);
//		ArrayList<String> reconciledVisaMastercard = this.reconcileItems(visaMastercardTotals, bankStatement);

//		ArrayList<SettlementStmt> finalAmex = this.matchReconciledWithSettlementItems(reconciledAmex, amexTransactions);
//		ArrayList<SettlementStmt> finalVisaMastercard = this.matchReconciledWithSettlementItems(reconciledVisaMastercard, visaMastercardTransactions);

		//Save the results to the db
		//this.settlementStmtRepo.saveAll(finalAmex);
		//this.settlementStmtRepo.saveAll(finalVisaMastercard);
	}

		private Map<String, Double> notReconciledDate(Map<String, Double> bank, Map<String, Double> settle) {
		Map<String,Double> notReconciledMap = new HashMap<>();
			for (Map.Entry<String, Double> bankEntry : bank.entrySet()) {
				for (Map.Entry<String, Double> settleEntry : settle.entrySet()) {
					if(bankEntry.getKey().equals(settleEntry.getKey())
						&& !bankEntry.getValue().equals(settleEntry.getValue())){
						//total price is not match on each day.
						double diffValue = settleEntry.getValue() - bankEntry.getValue();
//						System.out.println("not reconciled");
						notReconciledMap.put(bankEntry.getKey(),diffValue); //date, difference Value
					}else{
//						System.out.println("reconciled");
//						System.out.println(bankEntry.getKey()+ " " + bankEntry.getValue());
//						System.out.println(settleEntry.getKey()+ " " + settleEntry.getValue());

					}
				}
			}
		return notReconciledMap;
	}

		private void finishedReconcile(Map<String, Double> visaMasterNotReconciled,Map<String, Double> amexNotReconciled,ListBankStmtVO listBankStmtVO){
			int totalTransaction = listBankStmtVO.getFilterBankStmt().size();
			int reconciledTransaction = 0;
			int notReconciled = 0;
			if(visaMasterNotReconciled.size() == 0 && amexNotReconciled.size() == 0){

			}

			System.out.println(listBankStmtVO.getFilterBankStmt().size());
			System.out.println();
		}


//	private ArrayList<SettlementStmt> matchReconciledWithSettlementItems(ArrayList<String> dates, ArrayList<SettlementStmt> items) {
//		for (int i = 0; i < items.size(); i++) {
//			for (int x = 0; x < dates.size(); x++) {
//				if (items.get(i).getSettlementDate().equals(dates.get(x))) {
//					items.get(i).setIsReconciled(true);
//					items.get(i).setReconciledDateTime(LocalDateTime.now());
//					break;
//				}
//			}
//		}
//		return items;
//	}

	//get Date Range
//	private ArrayList<String> reconcileItems(Map<String, Double> totals, List<BankStmt> bankStatement) {
//		ArrayList<String> response = new ArrayList<String>();
//
//		for (Map.Entry<String, Double> entry : totals.entrySet()) {
//			for (int i = 0; i < bankStatement.size(); i++) {
//				if (bankStatement.get(i).getDate().equals(entry.getKey()) && bankStatement.get(i).getCredits() == entry.getValue()) {
//					response.add(bankStatement.get(i).getDate());
//					break;
//				}
//			}
//		}
//		return response;
//	}

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
