package com.trunk.demo.interfaces.s3;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class S3RepositoryImpl implements S3Repository {

	/*@Autowired
	private static AmazonS3 s3Client;

	@Value("${jsa.aws.access_key_id}")
	private String awsId;
	@Value("${jsa.aws.secret_access_key}")
	private String awsKey;
	@Value("${jsa.s3.region}")
	private String region;
	@Value("${jsa.s3.bucket}")
	private String bucketName;

	@PostConstruct
	public void setup() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

	@Override
	public String newUploadFile(String type, String originalFilename, InputStream inputStream) {

		try {
			String fileLocation = type + "/" + originalFilename;
			s3Client.putObject(bucketName, fileLocation, inputStream, null);
			return "SUCCESS";
		} catch (Exception e) {
			return "{\"result\":\"fail\",\"reason\":" + e.getMessage() + "}";
		}
	}*/

}
