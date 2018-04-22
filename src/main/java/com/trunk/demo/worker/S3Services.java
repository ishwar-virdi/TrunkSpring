package com.trunk.demo.worker;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3Services {

	@Autowired
	private static AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private static String bucketName;

	public static JSONObject downloadFile(String keyName) {

		JSONObject result = new JSONObject();
		try {
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));

			result.put("contentType", s3object.getObjectMetadata().getContentType());
			result.put("content", s3object.getObjectContent());

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException from GET requests, rejected reasons:");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException: ");
			System.out.println("Error Message: " + ace.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject uploadFile(String keyName, String uploadFilePath) {

		JSONObject result = new JSONObject();

		try {

			File file = new File(uploadFilePath);
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));

			result.put("result", "success");

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException: ");
			System.out.println("Error Message: " + ace.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}