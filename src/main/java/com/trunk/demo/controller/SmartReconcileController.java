package com.trunk.demo.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trunk.demo.worker.S3Services;
import com.trunk.demo.pojo.FileUpload;
import com.trunk.demo.pojo.LoginDetails;
import com.trunk.demo.pojo.MatchFiles;
import com.trunk.demo.worker.FileMatcher;
import com.trunk.demo.worker.UserManager;
import com.trunk.demo.worker.TokenGenerator;

@RestController
@CrossOrigin(origins = "https://trunksmartreconcilereact.herokuapp.com")
public class SmartReconcileController {

	@Autowired
	private UserManager userManager;
	
	
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
		return S3Services.uploadFile(fu.getKeyName(), fu.getUploadFilePath());
	}

	@RequestMapping(value = "/api/readFile/{keyName}")
	public JSONObject uploadFile(@PathVariable String keyName) {
		return S3Services.downloadFile(keyName);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/matchFile")
	public JSONObject matchFile(@RequestBody MatchFiles mf) {
		return FileMatcher.matchTheFiles(mf);
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
