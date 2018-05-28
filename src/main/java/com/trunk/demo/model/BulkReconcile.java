package com.trunk.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkReconcile {

	private boolean markAsReconcile;
	private String[] items;

	public boolean isMarkAsReconcile() {
		return markAsReconcile;
	}

	public void setMarkAsReconcile(boolean markAsReconcile) {
		this.markAsReconcile = markAsReconcile;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	@JsonCreator
	public BulkReconcile(@JsonProperty("markAsReconcile") boolean markAsReconcile,@JsonProperty("items") String[] items) {
		super();
		this.markAsReconcile = markAsReconcile;
		this.items = items;
	}

}