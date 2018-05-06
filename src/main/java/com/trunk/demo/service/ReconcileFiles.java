package com.trunk.demo.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.s3.BankStatement;
import com.trunk.demo.model.s3.SettlementDocument;

@Service
public interface ReconcileFiles {
	public JSONObject reconcileDocuments();
	public void setBankStatement (BankStatement bankStatement);
	public void setSettlementDocument (SettlementDocument settlementDocument);
}
