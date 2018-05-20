package com.trunk.demo.service.s3;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.s3.S3Repository;

@Service
public class S3ServicesImpl implements S3Service {

	//@Autowired
	//S3Repository s3Repo;

	//@Override
	//public String newUploadFile(String type, String originalFilename, InputStream inputStream) {
	//	return s3Repo.newUploadFile(type, originalFilename, inputStream);
	//}

}