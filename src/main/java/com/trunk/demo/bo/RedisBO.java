package com.trunk.demo.bo;

public interface RedisBO {
	public void pushTransaction(int id, Object value);

	public void pushType(String type);

	public void pushFileName(String filename);

	public Object getTransaction(int id);

	public Object getFileName();

	public void deleteSpecificValue(String value);

	public void deleteCache();

	public Object getType();

	public void deleteObjectCache();
}
