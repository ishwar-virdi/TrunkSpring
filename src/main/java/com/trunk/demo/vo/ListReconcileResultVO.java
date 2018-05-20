package com.trunk.demo.vo;

import com.trunk.demo.model.mongo.ReconcileResult;

import java.util.ArrayList;
import java.util.List;

public class ListReconcileResultVO {
    private List<ReconcileResultVO> list;

    public ListReconcileResultVO(List<ReconcileResult> results) {
        list = new ArrayList<>();
        for(int i = 0, length = results.size();i < length;i++){
            ReconcileResultVO resultPO = new ReconcileResultVO(results.get(i));
            list.add(resultPO);
        }
    }

    public List<ReconcileResultVO> getList() {
        return list;
    }

}
