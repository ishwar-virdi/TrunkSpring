package com.trunk.demo.service.mongo;

import com.google.gson.JsonArray;
import org.springframework.stereotype.Service;

@Service
public interface ResultDetailManager {
	public String getResultDetail(String id);

    public String markReconcile(JsonArray items);

    public String markNotReconcile(JsonArray items);
}
