package com.trunk.demo.bo;

public interface RedisBO {
	public void pushTransaction(int id, Object value);

	public void pushType(String type);

	public void pushFileName(String filename);

	public Object getTransaction(int id);

	public Object getFileName();

	// Object getTransactionDate(String id);

	void deleteSpecificValue(String value);

	public void deleteCache();

	public Object getType();

	// public void pushTransactionDate(Set<String> sets);

	public void deleteObjectCache();
}
