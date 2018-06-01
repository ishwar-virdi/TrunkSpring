package com.trunk.demo.service.mongo;

import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface UploadManager {

	public String newUploadFile(String type, String fileName, InputStream inputStream);

	public String retrieveUploadRecords();
}
