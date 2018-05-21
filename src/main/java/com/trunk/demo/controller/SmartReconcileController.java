package com.trunk.demo.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trunk.demo.service.ReconcileFiles;
import com.trunk.demo.service.mongo.ReceiptManager;
import com.trunk.demo.service.mongo.ResultDetailManager;
import com.trunk.demo.service.mongo.UploadManager;

@RestController
public class SmartReconcileController {

	@Autowired
	private UploadManager uploadManager;
	@Autowired
	private ReconcileFiles reconcileFiles;
	@Autowired
	private ReceiptManager receiptManager;
	@Autowired
	private ResultDetailManager resultDetailManager;

	@RequestMapping(path = "/{type}/upload", method = RequestMethod.POST)
	public String uploadFile(@PathVariable("type") String type, @RequestParam("file") MultipartFile file)
			throws IOException {
		return uploadManager.newUploadFile(type, file.getOriginalFilename(), file.getInputStream());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/reconcile")
	public void reconcile() {
		reconcileFiles.reconcile();
	}

	// For testing only
	@RequestMapping(method = RequestMethod.GET, path = "/api/reset")
	public void reset() throws ParseException {
		reconcileFiles.reset();
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
	
}
