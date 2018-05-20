package com.trunk.demo.model.mongo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ReconcileResults")
public class ReconcileResult {

	@Value("${zone}")
	private String zone;

	@Id
	private String id;

	@Field
	private String userId;
	@Field
	private Date lastModified;
	@Field
	private Date startDate;
	@Field
	private Date endDate;
	@Field
	private int isReconciled;
	@Field
	private int notReconciled;
	@Field
	private int percentage;

	public ReconcileResult(String zone, String id, String userId, Date lastModified, Date startDate, Date endDate,
			int isReconciled, int notReconciled, int percentage) {
		super();
		this.zone = zone;
		this.id = id;
		this.userId = userId;
		this.lastModified = lastModified;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isReconciled = isReconciled;
		this.notReconciled = notReconciled;
		this.percentage = percentage;
	}

	public ReconcileResult(String userId, Date startDate, Date endDate, int isReconciled, int notReconciled) {
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isReconciled = isReconciled;
		this.notReconciled = notReconciled;
		if (isReconciled + notReconciled > 0)
			this.percentage = (int)((isReconciled / (double) (isReconciled + notReconciled) ) * 100);
		else
			this.percentage = 0;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getIsReconciled() {
		return isReconciled;
	}

	public void setIsReconciled(int isReconciled) {
		this.isReconciled = isReconciled;
	}

	public int getNotReconciled() {
		return notReconciled;
	}

	public void setNotReconciled(int notReconciled) {
		this.notReconciled = notReconciled;
	}

	public String getId() {
		return id;
	}

}
