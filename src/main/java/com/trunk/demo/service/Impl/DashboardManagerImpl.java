package com.trunk.demo.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.bo.*;

import com.trunk.demo.bo.Impl.ListBankStatementBO;
import com.trunk.demo.bo.Impl.ListSettlementBO;
import com.trunk.demo.vo.DashDailyTransaction;
import com.trunk.demo.vo.DashMonthTotalVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.service.mongo.DashboardManager;

@Service
public class DashboardManagerImpl implements DashboardManager {

	@Value("${dashboard.limit}")
	private String limit;

	@Autowired
	private ReconcileResultBO reconcileResultBO;
	@Autowired
	private SettlementBO settlementBO;
	@Autowired
	private BankStmtBO bankStmtBO;
	@Autowired
	private Gson gson;

	@Override
	public String getReconcileData(String userId) {
		JSONObject response = new JSONObject();
		String[] labels = new String[Integer.parseInt(limit)];

		List<ReconcileResult> reconcileResults = reconcileResultBO.findAllByUserId(userId);

		DataSet reconciled = new DataSet("Reconciled", "#7986CB");
		DataSet notReconciled = new DataSet("Not Reconciled", "#E57373");

		int dashboardLimit = Integer.parseInt(limit);

		CalenderUtil calUtil = new CalenderUtil();
		Calendar cal = Calendar.getInstance();
		cal.setTime(calUtil.firstDayOfThisMonth(new Date()));
		for (int i = 0; i < dashboardLimit; ++i) {
			cal.add(Calendar.MONTH, -1);	
			labels[i] = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).substring(0, 3) + "-"
					+ cal.get(Calendar.YEAR);
		}

		for (ReconcileResult item : reconcileResults) {
			for (int i = 0; i < labels.length; ++i) {
				if (item.getId().equals(labels[i])) {
					reconciled.addData(i, item.getIsReconciled());
					notReconciled.addData(i, item.getNotReconciled());
				}
			}
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

		int period = Integer.parseInt(limit);
		CalenderUtil calUtil = new CalenderUtil();
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal.setTime(calUtil.firstDayOfThisMonth(new Date()));
		cal2.setTime(calUtil.firstDayOfThisMonth(new Date()));

		cal.add(Calendar.MONTH, (-1 * page * period));
		cal2.add(Calendar.MONTH, (-1 * page * period));

		for (int i = 0; i < period; i++) {

			cal.add(Calendar.MONTH, -1);
			cal2.add(Calendar.MONTH, -1);

			Date startOfMonth = cal.getTime();
			cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endOfMonth = cal2.getTime();

			ListBankStatementBO bankBO = new ListBankStatementBO(
					bankStmtBO.findAllBetweenDates(startOfMonth, endOfMonth));
			ListSettlementBO settleBO = new ListSettlementBO(
					settlementBO.findAllBySettlementDateBetweenValues(startOfMonth, endOfMonth));

			String id = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).substring(0, 3) + "-"
					+ cal.get(Calendar.YEAR);

			DashMonthTotalVO dashMonthTotalVO = new DashMonthTotalVO(id, settleBO.getTotalAmount(),
					bankBO.getTotalAmount());

			list.add(dashMonthTotalVO);
		}
		return gson.toJson(list);
	}

	@Override
	public String getDailyTransaction(int page) {

		Double visaBankTotal;
		Double debitBankTotal;
		Double amexBankTotal;
		Double visaSettTotal;
		Double debitSettTotal;
		Double amexSettTotal;

		List<DashDailyTransaction> dashDaliyTransactions = new ArrayList<>();

		CalenderUtil calUtil = new CalenderUtil();
		Calendar cal = Calendar.getInstance();
		cal.setTime(calUtil.firstDayOfThisMonth(new Date()));

		cal.add(Calendar.MONTH, (-1 * page));
		Date startOfMonth = cal.getTime();

		for (int i = cal.get(Calendar.DAY_OF_MONTH); i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

			Calendar eachDayCal = Calendar.getInstance();
			eachDayCal.setTime(startOfMonth);
			eachDayCal.set(Calendar.DAY_OF_MONTH, i);

			Date eachDayStart = eachDayCal.getTime();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
			Date eachDayEnd = cal.getTime();

			ListBankStatementBO bankBO = new ListBankStatementBO(
					bankStmtBO.findAllBetweenDates(eachDayStart, eachDayEnd));


			ListSettlementBO settleBO = new ListSettlementBO(
					settlementBO.findAllBySettlementDateBetweenValues(eachDayStart, eachDayEnd));

			visaBankTotal = bankBO.getVisaMapTotal(eachDayStart);
			debitBankTotal = bankBO.getDebitMapTotal(eachDayStart);
			amexBankTotal = bankBO.getAmexMapTotal(eachDayStart);
			visaSettTotal = settleBO.getVisaMapTotal(eachDayStart);
			debitSettTotal = settleBO.getDebitMapTotal(eachDayStart);
			amexSettTotal = settleBO.getAmexMapTotal(eachDayStart);

			String dateAsString = cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).substring(0, 3) + "-"
					+ cal.get(Calendar.YEAR);
			DashDailyTransaction dashDaliyTransaction = new DashDailyTransaction(dateAsString, visaSettTotal,
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
