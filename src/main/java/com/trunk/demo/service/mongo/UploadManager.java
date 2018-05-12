package com.trunk.demo.service.mongo;

import java.io.InputStream;

public interface UploadManager {

	public void newUploadFile(String type, InputStream inputStream);

}
