package com.trunk.demo.controller;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trunk.demo.service.mongo.DashboardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.trunk.demo.service.mongo.ReceiptManager;
import com.trunk.demo.service.mongo.ResultDetailManager;
import com.trunk.demo.service.mongo.UploadManager;

import javax.servlet.http.HttpSession;

@RestController
public class SmartReconcileController {

	@Autowired
	private UploadManager uploadManager;
	@Autowired
	private ReceiptManager receiptManager;
	@Autowired
	private ResultDetailManager resultDetailManager;
	@Autowired
	private DashboardManager dashboardManager;

	@RequestMapping(path = "/{type}/upload", method = RequestMethod.POST)
	public String uploadFile(@PathVariable("type") String type, @RequestParam("file") MultipartFile file)
			throws IOException {
		return uploadManager.newUploadFile(type, file.getOriginalFilename(), file.getInputStream());
	}

	@RequestMapping(path = "/api/v1/uploadRecords", method = RequestMethod.GET)
	public String uploadRecords() {
		return uploadManager.retrieveUploadRecords();
	}

	@RequestMapping(path = "/api/v1/receipt/{id}", method = RequestMethod.GET)
	public String getReceipt(@PathVariable("id") String id) {
		return receiptManager.getReceipt(id);
	}

	@RequestMapping(path = "/api/v1/manualReconcile/{option}/{id}", method = RequestMethod.PUT)
	public String markAsReconciled(@PathVariable("option") String option, @PathVariable("id") String id) {
		return receiptManager.markAsReconciledOrNot(id, Boolean.parseBoolean(option));
	}

	@RequestMapping(path = "/api/resultDetails/{id}", method = RequestMethod.GET)
	public String getResultDetails(@PathVariable("id") String id) {
		return resultDetailManager.getResultDetail(id);
	}

	@RequestMapping(path = "/api/markReconcile", method = RequestMethod.POST)
	public String getResultDetails(@RequestBody String param, HttpSession session) {
		String json = "";
		JsonObject params = new JsonParser().parse(param).getAsJsonObject();
		String markAsReconcile = params.get("markAsReconcile").toString();
		JsonArray items = params.get("items").getAsJsonArray();
		if ("true".equals(markAsReconcile)) {
			json = resultDetailManager.markReconcile(items);
		} else if ("false".equals(markAsReconcile)) {
			json = resultDetailManager.markNotReconcile(items);
		}

		return json;
	}

	@RequestMapping(path = "/api/getChartData", method = RequestMethod.GET)
	public String getChartData() {
		return dashboardManager.getReconcileData();
	}

	@RequestMapping(path = "/api/v1/monthTotalAmount", method = RequestMethod.GET)
	public String getMonthTotal(@RequestParam String page) {
		int pageIndex;
		try {
			pageIndex = Integer.parseInt(page);
		} catch (Exception e) {
			pageIndex = 0;
		}
		return dashboardManager.getMonthTotal(pageIndex);
	}

	@RequestMapping(path = "/api/v1/getDailyTransaction", method = RequestMethod.GET)
	public String getDailyTransaction(@RequestParam String page) {
		int pageIndex;
		try {
			pageIndex = Integer.parseInt(page);
		} catch (Exception e) {
			pageIndex = 0;
		}
		return dashboardManager.getDailyTransaction(pageIndex);
	}

}
