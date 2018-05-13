package com.trunk.demo.interfaces.s3;

import java.io.File;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3RepositoryImpl implements S3Repository {

    @Autowired
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

    public JSONObject downloadFile(String keyName) {

        JSONObject result = new JSONObject();
        try {
            S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));

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

    public JSONObject uploadFile(String keyName, String uploadFilePath) {

        JSONObject result = new JSONObject();

        try {

            File file = new File(uploadFilePath);
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));

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

    @Override
    public JSONObject newUploadFile(String type, String date, String originalFilename, InputStream inputStream) {
        // String[] dateSplit = date.split("-");

        String fileLocation = type + "/" + date + "/" + originalFilename;

        s3Client.putObject(bucketName, fileLocation, inputStream, null);

        System.out.println("File Uploaded to S3");
        return null;
    }

}
