package com.trunk.demo.service.mongo;

import java.io.InputStream;

public interface UploadManager {

	public String newUploadFile(String type, String fileName, InputStream inputStream);

}
