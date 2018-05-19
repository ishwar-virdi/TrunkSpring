package com.trunk.demo.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ReconcileDetails")
public class ReconcileDetail {

    @Id
    @Field
    private String id;
    @Field
    private List<ReconcileDetailItem> details;

    public ReconcileDetail(String id, ArrayList<ReconcileDetailItem> details) {
        this.id = id;
        this.details = details;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<ReconcileDetailItem> getList() {
        return details;
    }
    public void setList(List<ReconcileDetailItem> list) {
        this.details = list;
    }
}
