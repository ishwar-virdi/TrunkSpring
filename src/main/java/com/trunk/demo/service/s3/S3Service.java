package com.trunk.demo.service.s3;

import java.io.InputStream;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface S3Service {

	public JSONObject downloadFile(String keyName);
	
	public JSONObject uploadFile(String keyName, String uploadFilePath);

	public void newUploadFile(String type, String date, String originalFilename, InputStream inputStream);
	
}
