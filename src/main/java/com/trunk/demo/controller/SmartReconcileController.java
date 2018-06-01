package com.trunk.demo.controller;

import java.io.IOException;

import com.trunk.demo.service.mongo.DashboardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@RequestMapping(path= "/api/v1/uploadRecords",method = RequestMethod.GET)
	public String uploadRecords(){
		return uploadManager.retrieveUploadRecords();
	}

	@RequestMapping(path = "/api/v1/receipt/{id}", method = RequestMethod.GET)
	public String getReceipt(@PathVariable("id") String id) {
		return receiptManager.getReceipt(id);
	}

	@RequestMapping(path = "/api/v1/manualReconcile/{id}", method = RequestMethod.PUT)
	public String markAsReconciled(@PathVariable("id") String id) {
		return receiptManager.markAsReconciled(id);
	}

	@RequestMapping(path = "/api/v1/manualNotReconcile/{id}", method = RequestMethod.PUT)
	public String markAsNotReconciled(@PathVariable("id") String id) {
		return receiptManager.markAsNotReconciled(id);
	}

	@RequestMapping(path = "/api/resultDetails/{id}", method = RequestMethod.GET)
	public String getResultDetails(@PathVariable("id") String id) {
		return resultDetailManager.getResultDetail(id);
	}

	@RequestMapping(path = "/api/getChartData", method = RequestMethod.GET)
	public String getChartData() {
		return dashboardManager.getReconcileData();
	}

	@RequestMapping(path = "/api/v1/monthTotalAmount", method = RequestMethod.GET)
	public String getMonthTotal(@RequestParam String page) {
		int pageIndex;
		try{
			pageIndex = Integer.parseInt(page);
		}catch (Exception e){
			pageIndex = 0;
		}
		return dashboardManager.getMonthTotal(pageIndex);
	}

	@RequestMapping(path = "/api/v1/getDailyTransaction", method = RequestMethod.GET)
	public String getDailyTransaction(@RequestParam String page) {
		int pageIndex;
		try{
			pageIndex = Integer.parseInt(page);
		}catch (Exception e){
			pageIndex = 0;
		}
		return dashboardManager.getDailyTransaction(pageIndex);
	}

}
