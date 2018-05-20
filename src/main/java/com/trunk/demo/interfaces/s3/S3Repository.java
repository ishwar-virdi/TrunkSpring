package com.trunk.demo.interfaces.s3;

import java.io.InputStream;


public interface S3Repository {

	public String newUploadFile(String type, String originalFilename, InputStream inputStream);
}
