package com.trunk.demo.service.Impl;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trunk.demo.bo.ReconcileResultBO;
import com.trunk.demo.bo.SettlementBO;
import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.service.mongo.ResultDetailManager;
import com.trunk.demo.vo.ListResultDetailsVO;

@Service
public class ResultDetailManagerImpl implements ResultDetailManager {
	@Autowired
	private SettlementBO settlementBO;
	@Autowired
	private ReconcileResultBO reconcileResultBO;

	@Override
	public String getResultDetail(String id) {
		List<SettlementStmt> settlementItems = settlementBO.findAllByReconcileResultsId(id);
		ListResultDetailsVO vo = new ListResultDetailsVO(settlementItems);
		return vo.getJSON();
	}

	@Override
	public String markReconcile(JsonArray items){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result","success");
		SettlementStmt settle = null;
		ReconcileResult result = null;
		String resultsId = null;
		int count = 0;
		if(items.size() == 0){
			jsonObject.addProperty("result","fail");
			return jsonObject.toString();
		}

		for(int i = 0,length = items.size();i < length;i++){
			settle = settlementBO.findByReceiptNumber(items.get(i).getAsLong());
			if(resultsId == null){
				resultsId = settle.getReconcileResultsId();
			}
			if(settle != null &&
					(settle.getReconcileStatus() == 0 || settle.getReconcileStatus() == 1)){
				settle.setReconcileStatus(2);
				settlementBO.save(settle);
				count++;
			}
		}

		result = reconcileResultBO.findById(resultsId);
		reconcileResultBO.updateIncreaseIsReconcile(count,result);
		return jsonObject.toString();
	}

	@Override
	public String markNotReconcile(JsonArray items){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result","success");
		SettlementStmt settle = null;
		ReconcileResult result = null;
		String resultsId = null;
		int count = 0;
		if(items.size() == 0){
			jsonObject.addProperty("result","fail");
			return jsonObject.toString();
		}
		for(int i = 0,length = items.size();i < length;i++){
			settle = settlementBO.findByReceiptNumber(items.get(i).getAsLong());
			if(resultsId == null){
				resultsId = settle.getReconcileResultsId();
			}
			if(settle != null &&
					(settle.getReconcileStatus() == 2 || settle.getReconcileStatus() == 3)){
				settle.setReconcileStatus(0);
				settlementBO.save(settle);
				count++;
			}
		}

		result = reconcileResultBO.findById(resultsId);
		reconcileResultBO.updateIncreaseNotReconcile(count,result);
		return jsonObject.toString();
	}

}
