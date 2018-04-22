package com.trunk.demo.worker;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.trunk.demo.pojo.MatchFiles;


public class FileMatcher {

	public static JSONObject matchTheFiles (MatchFiles fileNames) {
		
		List<JSONObject> files = new ArrayList<JSONObject>();

		for(String eachFileKey : fileNames.getFileKeyNames())
			files.add(S3Services.downloadFile(eachFileKey));
		
		
		return null;
	}
	
}
