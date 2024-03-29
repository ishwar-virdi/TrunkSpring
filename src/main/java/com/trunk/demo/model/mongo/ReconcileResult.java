package com.trunk.demo.model.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.trunk.demo.Util.CalenderUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ReconcileResults")
public class ReconcileResult implements Comparable<ReconcileResult> {

	@Id
	private String id;

	@Field
	private String userId;
	@Field
	private Date lastModified;
	@Field
	private int isReconciled;
	@Field
	private int notReconciled;
	@Field
	private Date startDate;
	@Field
	private Date endDate;

	public ReconcileResult() {
		super();
	}

	@PersistenceConstructor
	public ReconcileResult(String id, String userId, int isReconciled, int notReconciled) {
		super();
		CalenderUtil cal = new CalenderUtil();
		this.id = id;
		this.userId = userId;
		this.lastModified = new Date();
		this.isReconciled = isReconciled;
		this.notReconciled = notReconciled;
		this.startDate = cal.firstDayOfMonthByString(id.replace("-",". "),"MMM yyyy");
		this.endDate = cal.EndDayOfMonthByString(id.replace("-",". "),"MMM yyyy");
	}

	public ReconcileResult(String id, String userId, Date lastModified, int isReconciled, int notReconciled, Date startDate, Date endDate) {
		this.id = id;
		this.userId = userId;
		this.lastModified = lastModified;
		this.isReconciled = isReconciled;
		this.notReconciled = notReconciled;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public int getPercentage() {
		return (isReconciled * 100) / (isReconciled + notReconciled);
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReconcileResult other = (ReconcileResult) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(ReconcileResult arg0) {

		try {
			Date currentDate = new SimpleDateFormat("MMM-YYYY", Locale.ENGLISH).parse(this.id);
			Date otherDate = new SimpleDateFormat("MMM-YYYY", Locale.ENGLISH).parse(arg0.id);
			if (currentDate.after(otherDate))
				return 1;
			else if (currentDate.before(otherDate))
				return -1;
			else
				return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

}