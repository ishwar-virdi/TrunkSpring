package com.trunk.demo.service;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenGenerator {

	public static String generateUUID() {
		JSONObject uuid = new JSONObject();
		try {
			uuid.put("token", UUID.randomUUID().toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uuid.toString();
	}

}
