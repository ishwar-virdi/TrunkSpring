package com.trunk.demo.vo;

import com.trunk.demo.model.mongo.ReconcileResult;

import java.util.ArrayList;
import java.util.List;

public class ListReconcileResultVO {
	private List<ReconcileResultVO> list;

	public ListReconcileResultVO(List<ReconcileResult> results) {
		list = new ArrayList<>();
		for (ReconcileResult eachReconcileResult : results) {
			ReconcileResultVO resultVO = new ReconcileResultVO(eachReconcileResult);
			list.add(resultVO);
		}
	}

	public List<ReconcileResultVO> getList() {
		return list;
	}

}
