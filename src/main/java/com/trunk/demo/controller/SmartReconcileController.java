package com.trunk.demo.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trunk.demo.model.LoginDetails;
import com.trunk.demo.model.MatchFiles;
import com.trunk.demo.model.s3.FileUpload;
import com.trunk.demo.service.FileMatcher;
import com.trunk.demo.service.ReconcileFiles;
import com.trunk.demo.service.TokenGenerator;
import com.trunk.demo.service.mongo.UserManager;
import com.trunk.demo.service.s3.S3Service;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SmartReconcileController {

	@Autowired
	private UserManager userManager;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private ReconcileFiles reconcileFiles;
	
	@RequestMapping("/api/token")
	public String tokenCreator() {
		return TokenGenerator.generateUUID();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/register")
	public String registerUser(@RequestBody LoginDetails ld) {
		return userManager.register(ld.getUsername(),ld.getPassword());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/login")
	public String loginChecker(@RequestBody LoginDetails ld) {
		return userManager.loginValidator(ld);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/uploadFile")
	public JSONObject uploadFile(@RequestBody FileUpload fu) {
		return s3Service.uploadFile(fu.getKeyName(), fu.getUploadFilePath());
	}

	@RequestMapping(value = "/api/readFile/{keyName}")
	public JSONObject uploadFile(@PathVariable String keyName) {
		return s3Service.downloadFile(keyName);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/matchFile")
	public JSONObject matchFile(@RequestBody MatchFiles mf) {
		return FileMatcher.matchTheFiles(mf);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/reconcile")
	public String reconcile() {
		return reconcileFiles.reconcileDocuments().toString();
	}

	
	/* COULD BE USEFULL LATER
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
