package com.trunk.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trunk.demo.model.BulkReconcile;
import com.trunk.demo.service.mongo.DashboardManager;
import com.trunk.demo.service.mongo.ReceiptManager;
import com.trunk.demo.service.mongo.ResultDetailManager;
import com.trunk.demo.service.mongo.UploadManager;

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

	@RequestMapping(path = "/receipt/{id}", method = RequestMethod.GET)
	public String getReceipt(@PathVariable("id") String id) {
		return receiptManager.getReceipt(id);
	}

	@RequestMapping(path = "/manualReconcile/{id}", method = RequestMethod.PUT)
	public String markAsReconciled(@PathVariable("id") String id) {
		return receiptManager.markAsReconciled(id);
	}

	@RequestMapping(path = "/api/resultDetails/{id}", method = RequestMethod.GET)
	public String getResultDetails(@PathVariable("id") String id) {
		return resultDetailManager.getResultDetail(id);
	}

	@RequestMapping(path = "/api/getChartData", method = RequestMethod.GET)
	public String getChartData() {
		return dashboardManager.getReconcileData();
	}

	@RequestMapping(path = "/api/markReconcile", method = RequestMethod.POST)
	public String bulkReconcile(@RequestBody BulkReconcile input) {
		return receiptManager.performBulkReconcile(input.isMarkAsReconcile(), input.getItems());
	}

}
