package com.trunk.demo.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.bo.ListBankStatementBO;
import com.trunk.demo.bo.ListSettlementBO;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.vo.DashDailyTransaction;
import com.trunk.demo.vo.DashMonthTotalVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.service.mongo.DashboardManager;

@Service
public class DashboardManagerImpl implements DashboardManager {

	@Value("${dashboard.limit}")
	private String limit;

	@Autowired
	private ResultsRepository reconcileResultRepo;
	@Autowired
	private SettlementRepository settlementRepository;
	@Autowired
	private BankStmtRepository bankStmtRepository;
	@Autowired
	private Gson gson;

	private CalenderUtil cal = new CalenderUtil();

	@Override
	public String getReconcileData() {
		JSONObject response = new JSONObject();
		String[] labels = new String[Integer.parseInt(limit)];

		List<ReconcileResult> reconcileResults = reconcileResultRepo.findAll();

		DataSet reconciled = new DataSet("Reconciled", "#E57373");
		DataSet notReconciled = new DataSet("Not Reconciled", "#7986CB");

		int i = 0;
		for (ReconcileResult item : reconcileResults) {
			if (i == 5)
				break;
			labels[i] = item.getId();
			reconciled.addData(i, item.getIsReconciled());
			notReconciled.addData(i, item.getNotReconciled());
		}

		JSONArray jsonArray;

		try {
			jsonArray = new JSONArray(labels);
			response.put("labels", jsonArray);
			response.put("reconciled", reconciled.getJSON());
			response.put("notReconciled", notReconciled.getJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response.toString();

	}

	@Override
	public String getMonthTotal(int page) {
		ArrayList<DashMonthTotalVO> list = new ArrayList<>();
		Date startOfDate = null;
		Date endOfDate = null;

		int period = Integer.parseInt(limit);

		for (int i = 0; i < period; i++) {
			startOfDate = cal.getFristDayOfMonth(cal.getYear(), cal.getMonth() - i - page * period);
			endOfDate = cal.getLastDayOfMonth(cal.getYear(), cal.getMonth() - i - page * period);

			Sort bankSort = new Sort(Sort.Direction.DESC, "date");
			Sort settleSort = new Sort(Sort.Direction.DESC, "settlementDate");
			ListBankStatementBO bankBO = new ListBankStatementBO(
					bankStmtRepository.findAllByDateBetween(startOfDate, endOfDate, bankSort));
			ListSettlementBO settleBO = new ListSettlementBO(
					settlementRepository.findAllBySettlementDateBetween(startOfDate, endOfDate, settleSort));
			DashMonthTotalVO dashMonthTotalVO = new DashMonthTotalVO(cal.getDateYear(startOfDate),
					cal.getDateMonth(startOfDate), settleBO.getTotalAmount(), bankBO.getTotalAmount());

			list.add(dashMonthTotalVO);
		}
		return gson.toJson(list);
	}

	@Override
	public String getDailyTransaction(int page) {
		Calendar dateDiff = null;
		Date startOfDate = null;
		Date endOfDate = null;

		Double visaBankTotal;
		Double debitBankTotal;
		Double amexBankTotal;
		Double visaSettTotal;
		Double debitSettTotal;
		Double amexSettTotal;

		List<DashDailyTransaction> dashDaliyTransactions = new ArrayList<>();
		startOfDate = cal.calcPrevDayFromCurr(-30 * (page + 1)).getTime();
		endOfDate = cal.calcPrevDayFromCurr(-30 * page + 1).getTime();

		int differenceDay = cal.differenceDay(startOfDate, endOfDate);

		Sort bankSort = new Sort(Sort.Direction.DESC, "date");
		Sort settleSort = new Sort(Sort.Direction.DESC, "settlementDate");
		ListBankStatementBO bankBO = new ListBankStatementBO(
				bankStmtRepository.findAllByDateBetween(startOfDate, endOfDate, bankSort));
		ListSettlementBO settleBO = new ListSettlementBO(
				settlementRepository.findAllBySettlementDateBetween(startOfDate, endOfDate, settleSort));

		for (int i = 0; i < differenceDay; i++) {
			dateDiff = cal.calcPrevDayFromCurr((i + 30 * page) * -1);

			visaBankTotal = bankBO.getVisaMapTotal(dateDiff.getTime());
			debitBankTotal = bankBO.getDebitMapTotal(dateDiff.getTime());
			amexBankTotal = bankBO.getAmexMapTotal(dateDiff.getTime());
			visaSettTotal = settleBO.getVisaMapTotal(dateDiff.getTime());
			debitSettTotal = settleBO.getDebitMapTotal(dateDiff.getTime());
			amexSettTotal = settleBO.getAmexMapTotal(dateDiff.getTime());
			DashDailyTransaction dashDaliyTransaction = new DashDailyTransaction(dateDiff, visaSettTotal,
					debitSettTotal, amexSettTotal, visaBankTotal, debitBankTotal, amexBankTotal);
			dashDaliyTransactions.add(dashDaliyTransaction);
		}

		return gson.toJson(dashDaliyTransactions);
	}

	@SuppressWarnings("unused")
	private class DataSet {
		private String label;
		private int data[] = new int[Integer.parseInt(limit)];
		private String backgroundColor;

		public DataSet(String label, String backgroundColor) {
			this.label = label;
			this.backgroundColor = backgroundColor;
		}

		public void addData(int index, int data) {
			this.data[index] = data;
		}

		public String getLabel() {
			return this.label;
		}

		public int[] getData() {
			return this.data;
		}

		public String getBackgroundColor() {
			return this.backgroundColor;
		}

		public JSONObject getJSON() {
			JSONObject response = new JSONObject();

			try {
				response.put("label", this.label);
				response.put("data", new JSONArray(this.data));
				response.put("backgroundColor", this.backgroundColor);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return response;
		}
	}
}
