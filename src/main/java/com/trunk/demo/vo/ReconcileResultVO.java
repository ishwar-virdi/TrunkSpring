package com.trunk.demo.vo;

import java.text.SimpleDateFormat;

import com.trunk.demo.model.mongo.ReconcileResult;

public class ReconcileResultVO {

	private String userId;
	private String reconcileDate;
	private String startDate;
	private String endDate;
	private int percentage;

	public ReconcileResultVO(String userId, String reconcileDate, String startDate, String endDate, int percentage) {
		this.userId = userId;
		this.reconcileDate = reconcileDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.percentage = percentage;
	}

	public ReconcileResultVO(ReconcileResult result) {
		this.userId = result.getUserId();
		this.reconcileDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(result.getLastModified());
		this.startDate = new SimpleDateFormat("yyyyMMdd").format(result.getStartDate());
		this.endDate = new SimpleDateFormat("yyyyMMdd").format(result.getEndDate());
		this.percentage = result.getPercentage();
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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

}
