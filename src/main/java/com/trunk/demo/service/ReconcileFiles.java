package com.trunk.demo.service;


import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

@Service
public interface ReconcileFiles {
	public void reconcileDocuments();
	public void setBankStatement (BankStmt bankStatement);
	public void setSettlementDocument (SettlementStmt settlementDocument);
}
