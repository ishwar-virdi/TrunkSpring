package com.trunk.demo.service.s3;

import java.io.InputStream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.s3.S3Repository;

@Service
public class S3ServicesImpl implements S3Service {

    @Autowired
    S3Repository s3Repo;

    @Override
    public JSONObject downloadFile(String keyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONObject uploadFile(String keyName, String uploadFilePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void newUploadFile(String type, String date, String originalFilename, InputStream inputStream) {
        s3Repo.newUploadFile(type, date, originalFilename, inputStream);
    }

}