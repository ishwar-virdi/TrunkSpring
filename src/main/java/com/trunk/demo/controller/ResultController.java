package com.trunk.demo.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trunk.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ResultController {

	@Autowired
	private ResultService resultService;

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/results")
	public String Results(@RequestParam String page, HttpSession session) {
		int pageIndex;
		try {
			pageIndex = Integer.parseInt(page);
		} catch (Exception e) {
			pageIndex = 0;
		}
		return resultService.retrieveResults(session, pageIndex);
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET,value="/api/v1/seedResults")
	 * public String saveSeedData(HttpSession session) throws ParseException {
	 * return resultService.saveSeedData(session); }
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/search")
	public String SearchResult(@RequestBody String param, HttpSession session) {
		JsonObject jsonObject = new JsonObject();
		JsonObject params = new JsonParser().parse(param).getAsJsonObject();
		String jsonPages = params.get("page").toString();
		String jsonValue = params.get("value").toString();
		String page = jsonPages.substring(1, jsonPages.length() - 1);
		String value = jsonValue.substring(1, jsonValue.length() - 1);
		Object userSession = session.getAttribute(session.getId());
		if (userSession == null) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		}
		String result = resultService.resultSearch(userSession.toString(), page, value);

		if ("fail".equals(result)) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		} else {
			return result;
		}
	}
}
