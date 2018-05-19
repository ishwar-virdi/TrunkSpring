package com.trunk.demo.model.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ReconcileResults")
public class ReconcileResult {

	@Value("${zone}")
	private String zone;

	@Id
	private String id;

	private String userId;
	private Date lastModified;
	private Date startDate;
	private Date endDate;
	private int isReconciled;
	private int notReconciled;

	public ReconcileResult(String userId, String lastModified, String startDate, String endDate, int isReconciled,
			int notReconciled) throws ParseException {
		super();
		this.userId = userId;

		this.lastModified = new SimpleDateFormat("yyyyMMdd HH:mm").parse(lastModified);

		this.startDate = new SimpleDateFormat("yyyyMMdd").parse(startDate);
		this.endDate = new SimpleDateFormat("yyyyMMdd").parse(endDate);

		this.isReconciled = isReconciled;
		this.notReconciled = notReconciled;
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
