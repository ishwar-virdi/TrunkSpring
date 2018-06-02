package com.trunk.demo.vo;

import java.text.SimpleDateFormat;

import com.trunk.demo.model.mongo.ReconcileResult;

public class ReconcileResultVO {

	private String userId;
	private String reconcileDate;
	private String startDate;
	private int percentage;
	private String reconcileResultId;

	public ReconcileResultVO(String userId, String reconcileDate, String startDate, String endDate, int percentage, String reconcileResultId) {
		this.userId = userId;
		this.reconcileDate = reconcileDate;
		this.startDate = startDate;
		this.percentage = percentage;
		this.reconcileResultId = reconcileResultId;
	}

	public ReconcileResultVO(ReconcileResult result) {
		this.userId = result.getUserId();
		this.reconcileDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(result.getLastModified());
		this.startDate = result.getId();
		this.percentage = result.getPercentage();
		this.reconcileResultId = result.getId();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReconcileDate() {
		return reconcileDate;
	}

	public void setReconcileDate(String reconcileDate) {
		this.reconcileDate = reconcileDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String getReconcileResultId() {
		return this.reconcileResultId;
	}
	
	public void setReconcileResultId(String id) {
		this.reconcileResultId = id;
	}

}
