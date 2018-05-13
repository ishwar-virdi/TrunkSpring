package com.trunk.demo.interfaces.s3;

import java.io.InputStream;

import org.json.JSONObject;

public interface S3Repository {

    public JSONObject downloadFile(String keyName);

    public JSONObject uploadFile(String keyName, String uploadFilePath);

    public JSONObject newUploadFile(String type, String date, String originalFilename, InputStream inputStream);
}
