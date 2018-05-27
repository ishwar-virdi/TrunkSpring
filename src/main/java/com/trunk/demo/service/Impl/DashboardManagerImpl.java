package com.trunk.demo.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.service.mongo.DashboardManager;

@Service
public class DashboardManagerImpl implements DashboardManager {
	private final int DATA_SIZE = 5;
	@Autowired
	private ResultsRepository reconcileResultRepo;
	
	@SuppressWarnings("deprecation")
	@Override
	public String getReconcileData() {
		JSONObject response = new JSONObject();
		String[] labels = new String[DATA_SIZE];
		Date[] dates = new Date[5];
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		
		
		calendar.setTime(new Date(today.getYear(), today.getMonth() + 1, 1));
		DateFormat df = new SimpleDateFormat("dd MMM yy");
		
		for (int i = 0; i < labels.length; i++) {
			calendar.add(Calendar.MONTH, -1);
			labels[i] = df.format(calendar.getTime());
			dates[i] = calendar.getTime();
		}
		
		List<ReconcileResult> reconcileResults = reconcileResultRepo.findAll();
		
		DataSet reconciled = new DataSet("Reconciled", "#E57373");
		DataSet notReconciled = new DataSet("Not Reconciled", "#7986CB");
		
		for (ReconcileResult item : reconcileResults) {
			for (int i = 0; i < dates.length; i++) {
				if (item.getStartDate().getYear() == dates[i].getYear() && item.getStartDate().getMonth() == dates[i].getMonth()) {
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
	
	class DataSet {
		private String label;
		private int data[] = new int[DATA_SIZE];
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
