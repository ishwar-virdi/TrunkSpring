package com.trunk.demo.service;

import java.util.*;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.google.gson.Gson;
import com.trunk.demo.Util.DateUtil;
import com.trunk.demo.Util.FormatUtil;
import com.trunk.demo.model.mongo.*;
import com.trunk.demo.repository.ReconcileDetailRepository;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.vo.ListBankStmtVO;
import com.trunk.demo.vo.ListSettlementStmtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.BankStmtRepository;
import com.trunk.demo.interfaces.mongo.SettlementRepository;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class ReconcileFilesImpl implements ReconcileFiles {

	@Autowired
	private BankStmtRepository bankStmtRepo;
	@Autowired
	private SettlementRepository settlementStmtRepo;
	@Autowired
	private Gson gson;
	@Autowired
	private ResultsRepository resultsRepository;
	@Autowired
	private ReconcileDetailRepository reconcileDetailRepository;

	private FormatUtil formatUtil = new FormatUtil();
	@Override
	public void reconcile() {

		String month = "201803";

		//Grabs the records it wants to work with from MongoDB
		ListBankStmtVO listBankStmtVO = new ListBankStmtVO(bankStmtRepo.findAllByDateLike("^" + month + "\\d*"));
		ListSettlementStmtVO listSettlementStmtVO = new ListSettlementStmtVO(settlementStmtRepo.findAllBySettlementDateLike("^" + month + "\\d*"));

		//Filter BankStatement
		listBankStmtVO.filterBankStatement();
		Map<String, Double> bankTotalsVisa = listBankStmtVO.addUpSameDayTransactions("visaMaster");
		Map<String, Double> bankTotalsDebit = listBankStmtVO.addUpSameDayTransactions("debit");

		listSettlementStmtVO.filterTransactionInRange(bankTotalsVisa,bankTotalsDebit);
		//Separates the amex, visa/mastercard and direct debit transactions from each other
		listSettlementStmtVO.SeparateSettlement();
//
//		//Work out transaction totals for different card types for each day
		Map<String, Double> settleVisaTotals = listSettlementStmtVO.addUpSameDayTransactions("visaMaster");
		Map<String, Double> settleDebitTotals = listSettlementStmtVO.addUpSameDayTransactions("debit");
		/**
		 * Amex
		 * */
		//Map<String, Double> settleAmexTotals = listSettlementStmtVO.addUpSameDayTransactions("amax");

		Map<String, Double> visaMasterReconciled = notReconciledDate(bankTotalsVisa,settleVisaTotals);
		Map<String, Double> debitReconciled = notReconciledDate(bankTotalsDebit,settleDebitTotals);

		finishOverViewReconcile(visaMasterReconciled,debitReconciled,
				listBankStmtVO,listSettlementStmtVO);
	}

		private Map<String, Double> notReconciledDate(Map<String, Double> bank, Map<String, Double> settle) {
			Map<String,Double> notReconciledMap = new HashMap<>();
			for (Map.Entry<String, Double> bankEntry : bank.entrySet()) {
				for (Map.Entry<String, Double> settleEntry : settle.entrySet()) {
					if(bankEntry.getKey().equals(settleEntry.getKey())
							&& bankEntry.getValue().equals(settleEntry.getValue())){
					}else if(bankEntry.getKey().equals(settleEntry.getKey())
							&& !bankEntry.getValue().equals(settleEntry.getValue())){
						double diffValue = settleEntry.getValue() - bankEntry.getValue();
						notReconciledMap.put(bankEntry.getKey(),diffValue); //date, difference Value
					}else if(!bank.containsKey(settleEntry.getKey())){
						notReconciledMap.put(settleEntry.getKey(),settleEntry.getValue());
					}
				}
			}
		return notReconciledMap;
	}

		private void finishOverViewReconcile(Map<String, Double> visaMasterNotReconciled,Map<String, Double> debitNotReconciled,
											 ListBankStmtVO listBankStmtVO,ListSettlementStmtVO listSettlementStmtVO){
			DateUtil dateUtil = new DateUtil();
			ArrayList<SettlementStmt> setttleList = listSettlementStmtVO.getTransactionInRange();
			//result
			double percentage = 0;
			double totalTransaction = setttleList.size();
			double reconciledTransaction = totalTransaction;
			double notReconciled = 0;
			Date startDate = dateUtil.getCurrMonthDate(listBankStmtVO.getFilterVisaBankStmt().get(0).getDate());
			Date endDate = dateUtil.getNextMonthDate(listBankStmtVO.getFilterVisaBankStmt().get(0).getDate());

			//resultDetail
			String resultDate = dateUtil.getDetailID(listBankStmtVO.getFilterVisaBankStmt().get(0).getDate());
			Map<String,String> description = new HashMap<>();
			ReconcileDetail detail = new ReconcileDetail();
			detail.setId(resultDate);
			ArrayList<ReconcileDetailItem> items = new ArrayList<>();
			ArrayList<SettlementStmt> issuedStmt = new ArrayList<>();
			if(visaMasterNotReconciled.size() != 0 || debitNotReconciled.size() != 0){  // not success
				//add issued Statement to list
				//visa
				for (Map.Entry<String, Double> visaNotEntry : visaMasterNotReconciled.entrySet()) {
					ArrayList<SettlementStmt> settleDate =listSettlementStmtVO.getSettleOnday(visaNotEntry.getKey(),"visaMaster");
					for(int i = 0,length = settleDate.size();i< length;i++){
						if(isIssuedStmt(description,settleDate.get(i), visaNotEntry.getValue(), issuedStmt)){
							reconciledTransaction--;
							notReconciled++;
						}
					}
				}
				//debit
				for (Map.Entry<String, Double> debitNoEntry : debitNotReconciled.entrySet()) {
					ArrayList<SettlementStmt> settleDate =listSettlementStmtVO.getSettleOnday(debitNoEntry.getKey(),"debit");
					for(int i = 0,length = settleDate.size();i< length;i++){
						if(isIssuedStmt(description,settleDate.get(i), debitNoEntry.getValue(), issuedStmt)){
							reconciledTransaction--;
							notReconciled++;
						}
					}
				}
			}

			//set Detail list
			for(int i= 0,length=setttleList.size();i<length;i++){
				for(int j= 0,issuedlength = issuedStmt.size();j<issuedlength;j++){
					if(setttleList.get(i).getSettlementDate().equals(issuedStmt.get(j).getSettlementDate())
							&&setttleList.get(i).getPrincipalAmount() == issuedStmt.get(j).getPrincipalAmount()){
						setttleList.get(i).setIsReconciled(false);
					}else{
						setttleList.get(i).setIsReconciled(true);
					}
				}
			}
			for(int i= 0,length=setttleList.size();i<length;i++){
				ReconcileDetailItem reconcileDetailItem;
				if(setttleList.get(i).getIsReconciled()){
					reconcileDetailItem = new ReconcileDetailItem(setttleList.get(i),"",true);
				}else{
					System.out.println(description.get(setttleList.get(i).getId()));
					reconcileDetailItem = new ReconcileDetailItem(setttleList.get(i),description.get(setttleList.get(i).getId()),false);
				}
				items.add(reconcileDetailItem);
			}
			detail.setList(items);

			percentage = (reconciledTransaction/totalTransaction)*100;
			ReconcileResult existResult = resultsRepository.findReconcileResultByUserIdAndStartDateAndEndDate("5af8786c1aad206af400a4b1",startDate,endDate);
			ReconcileResult result;
			if(existResult == null){ //not exist
				result = new ReconcileResult("5af8786c1aad206af400a4b1",startDate,endDate, (int) percentage,(int)totalTransaction,(int)reconciledTransaction,(int)notReconciled);
			}else{ //is exist
				result = existResult;
				result.setPercentage( (int) percentage);
				result.setTotalTransaction((int)totalTransaction);
				result.setReconciledTransaction((int)reconciledTransaction);
				result.setNotReconciled((int)notReconciled);
			}
			resultsRepository.save(result);
			reconcileDetailRepository.save(detail);
		}


	private boolean isIssuedStmt(Map<String,String> description,SettlementStmt settle, double mapValue, ArrayList<SettlementStmt> issuedStmt){
		double amount = settle.getPrincipalAmount();
		if(amount == mapValue){
			description.put(settle.getId(),"Difference value is " + mapValue);
			issuedStmt.add(settle);
			return true;
		}
		return false;
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
