package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ReconcileDetails")
public class ReconcileDetail {

    private String id;
    private List<ReconcileDetailItem> list;

    public ReconcileDetail(String id, ArrayList<ReconcileDetailItem> list) {
        this.id = id;
        this.list = list;
    }

    public ReconcileDetail() {

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<ReconcileDetailItem> getList() {
        return list;
    }
    public void setList(List<ReconcileDetailItem> list) {
        this.list = list;
    }
}
