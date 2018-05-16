package com.trunk.demo.model.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime lastModified;
    private LocalDate startDate;
    private LocalDate endDate;
    private int isReconciled;
    private int notReconciled;

    public ReconcileResult(String userId,String lastModified, String startDate,String endDate, int isReconciled, int notReconciled) {
        super();
        this.userId = userId;
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm").withZone(ZoneId.of(zone));
        this.lastModified = LocalDateTime.parse(lastModified, formatter);;

        startDate = startDate + " 00:00";
        endDate = endDate + " 00:00";
		formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm").withZone(ZoneId.of(zone));
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
        
        this.isReconciled = isReconciled;
        this.notReconciled = notReconciled;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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
