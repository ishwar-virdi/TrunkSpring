package com.trunk.demo.vo;

import java.util.List;

public class UploadReviewListVO<T> {
    private String fileName;
    private String type;
    private List<T> transactions;

    public UploadReviewListVO(String fileName, String type,List<T> list) {
        this.fileName = fileName;
        this.type = type;
        this.transactions = list;
    }

    public String getFileName() {
        return fileName;
    }

    public List<T> getList() {
        return transactions;
    }

    public String getType() {
        return type;
    }
}
