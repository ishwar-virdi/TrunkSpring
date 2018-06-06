package com.trunk.demo.bo.Impl;

import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.Util.CardType;
import com.trunk.demo.Util.SettleType;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

import java.util.*;

public class ListSettlementBO {

	private List<SettlementStmt> list;
	private List<SettlementStmt> visaMasterList;
	private List<SettlementStmt> debitList;
	private List<SettlementStmt> amexList;
	private Map<Date, Double> visaMasterMap;
	private Map<Date, Double> debitMap;
	private Map<Date, Double> amexMap;
	private CalenderUtil cal = new CalenderUtil();

	public ListSettlementBO(List<SettlementStmt> list) {
		this.list = list;
		this.visaMasterList = new ArrayList<>();
		this.debitList = new ArrayList<>();
		this.amexList = new ArrayList<>();
		this.separateSettle();
		this.visaMasterMap = addUpSameDayTransactions(visaMasterList);
		this.debitMap = addUpSameDayTransactions(debitList);
		this.amexMap = addUpSameDayTransactions(amexList);
	}

	public List<SettlementStmt> getList() {
		return list;
	}

	public Double getVisaMapTotal(Date date) {
		if (visaMasterMap.get(date) == null) {
			return 0.0;
		} else {
			return visaMasterMap.get(date);
		}
	}

	public Double getDebitMapTotal(Date date) {
		if (debitMap.get(date) == null) {
			return 0.0;
		} else {
			return debitMap.get(date);
		}
	}

	public Double getAmexMapTotal(Date date) {
		if (amexMap.get(date) == null) {
			return 0.0;
		} else {
			return amexMap.get(date);
		}
	}

	public double getTotalAmount() {
		double totalAmount = 0;
		for (int i = 0, length = list.size(); i < length; i++) {
			totalAmount += list.get(i).getPrincipalAmount();
			totalAmount += list.get(i).getSurcharge();
		}
		return totalAmount;
	}

	private void separateSettle() {
		SettlementStmt settle = null;
		for (int i = 0; i < list.size(); i++) {
			settle = list.get(i);
			// unified settlement date
			settle.setSettlementDate(cal.setDateToInit(list.get(i).getSettlementDate()));
			if (list.get(i).getStatus().contains("Approved")) {
				// separate document
				if (SettleType.VISA.toString().equals(settle.getCardSchemeName())) {
					// System.out.println("VISA" + settle.getSettlementDate() + " " +
					// settle.getPrincipalAmount());
					visaMasterList.add(settle);
				} else if (SettleType.MASTERCARD.toString().equals(settle.getCardSchemeName())) {
					// System.out.println("MASTERCARD" + settle.getSettlementDate() + " " +
					// settle.getPrincipalAmount());
					visaMasterList.add(settle);
				} else if (SettleType.AMEX.toString().equals(settle.getCardSchemeName())) {
					amexList.add(settle);
				} else {
					debitList.add(settle);
				}
			}
		}
	}

	private Map<Date, Double> addUpSameDayTransactions(List<SettlementStmt> list) {
		Map<Date, Double> map = new HashMap<Date, Double>();
		for (int i = 0; i < list.size(); i++) {
			Double amount = map.get(list.get(i).getSettlementDate());

			if (amount != null) {
				map.put(list.get(i).getSettlementDate(), amount + list.get(i).getPrincipalAmount());
			} else {
				map.put(list.get(i).getSettlementDate(), list.get(i).getPrincipalAmount());
			}
		}
		return map;
	}

	public ListSettlementBO(List<SettlementStmt> list, List<SettlementStmt> visaMasterList,
			List<SettlementStmt> debitList, List<SettlementStmt> amexList, Map<Date, Double> visaMasterMap,
			Map<Date, Double> debitMap, Map<Date, Double> amexMap, CalenderUtil cal) {
		super();
		this.list = list;
		this.visaMasterList = visaMasterList;
		this.debitList = debitList;
		this.amexList = amexList;
		this.visaMasterMap = visaMasterMap;
		this.debitMap = debitMap;
		this.amexMap = amexMap;
		this.cal = cal;
	}

	public ListSettlementBO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
