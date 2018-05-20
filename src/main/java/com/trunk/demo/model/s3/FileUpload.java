package com.trunk.demo.model.s3;

public class FileUpload {
    private String keyName;
    private String uploadFilePath;

    public FileUpload(String keyName, String uploadFilePath) {
        super();
        this.keyName = keyName;
        this.uploadFilePath = uploadFilePath;
    }

    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }


}
