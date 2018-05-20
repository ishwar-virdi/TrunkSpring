package com.trunk.demo.controller;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trunk.demo.model.MatchFiles;
import com.trunk.demo.service.FileMatcher;
import com.trunk.demo.service.ReconcileFiles;
import com.trunk.demo.service.mongo.ReceiptManager;
import com.trunk.demo.service.mongo.UploadManager;

@RestController
// Use 2nd one for Local Testing. Do Not commit the 2nd active.
//@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
@CrossOrigin(origins = "http://localhost:3000")
public class SmartReconcileController {

//	@Autowired
//	private UserManager userManager;
	@Autowired
	private UploadManager uploadManager;
	@Autowired
	private ReconcileFiles reconcileFiles;
	@Autowired
	private ReceiptManager receiptManager;

//	@RequestMapping("/api/token")
//	public String tokenCreator() {
//		return TokenGenerator.generateUUID();
//	}

//	@RequestMapping(method = RequestMethod.POST, value = "/api/register")
//	public String registerUser(@RequestBody LoginDetails ld) {
//		return userManager.register(ld.getUsername(), ld.getPassword());
//	}

//	@RequestMapping(method = RequestMethod.POST, value = "/api/login")
//	public String loginChecker(@RequestBody LoginDetails ld) {
//		return userManager.loginValidator(ld);
//	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/matchFile")
	public JSONObject matchFile(@RequestBody MatchFiles mf) {
		return FileMatcher.matchTheFiles(mf);
	}

	@RequestMapping(path = "/{type}/upload", method = RequestMethod.POST)
	public String uploadFile(@PathVariable("type") String type, @RequestParam("file") MultipartFile file)
			throws IOException {
		return uploadManager.newUploadFile(type, file.getOriginalFilename(), file.getInputStream());
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/api/reconcile")
	public void reconcile() {
		reconcileFiles.reconcile();
	}
	
	//For testing only
	@RequestMapping(method = RequestMethod.GET, path = "/api/reset")
	public void reset() throws ParseException {
		reconcileFiles.reset();
	}

	@RequestMapping(path = "/receipt/{id}", method = RequestMethod.GET)
	public String getReceipt(@PathVariable("id") String id) {
		return receiptManager.getReceipt(id);
	}

	
	/*
	 * Upload File to S3 (No Longer Using. But Useful to Have)
	 * 
	 * @RequestMapping(path = "/{type}/{date}/upload", method = RequestMethod.POST)
	 * public void uploadFile(@PathVariable("type") String
	 * type, @PathVariable("date") String date,
	 * 
	 * @RequestParam("file") MultipartFile file) throws IOException {
	 * s3Service.newUploadFile(type, date, file.getOriginalFilename(),
	 * file.getInputStream()); }
	 * 
	 * @RequestMapping(method = RequestMethod.POST, value = "/api/uploadFile")
	 * public JSONObject uploadFile(@RequestBody FileUpload fu) { return
	 * s3Service.uploadFile(fu.getKeyName(), fu.getUploadFilePath()); }
	 *
	 * @RequestMapping(value = "/api/readFile/{keyName}") public JSONObject
	 * uploadFile(@PathVariable String keyName) { return
	 * s3Service.downloadFile(keyName); }
	 */

	/*
	 * COULD BE USEFULL LATER
	 * 
	 * @CrossOrigin(origins = "http://localhost:3000")
	 * 
	 * @RequestMapping("/book/{name}") public Book get(@PathVariable String name) {
	 * return bs.getByName(name); }
	 * 
	 * @CrossOrigin(origins = "http://localhost:3000")
	 * 
	 * @RequestMapping(method = RequestMethod.DELETE, value = "/book/{id}") public
	 * void delete(@PathVariable long id) { bs.delete(id); }
	 */
}
