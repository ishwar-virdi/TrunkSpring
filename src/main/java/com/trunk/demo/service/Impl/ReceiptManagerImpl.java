package com.trunk.demo.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.trunk.demo.Util.CalenderUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.service.mongo.ReceiptManager;

@Service
public class ReceiptManagerImpl implements ReceiptManager {

	@Autowired
	private SettlementRepository settlementStmtRepo;

	@Override
	public String getReceipt(String id) {
		JsonObject jsonObject = new JsonObject();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(id));
		if(stmt.isPresent()){
			SettlementStmt settle = stmt.get();
			if("".equals(settle.getCardSchemeName())){
				settle.setCardSchemeName("Debit");
			}
			jsonObject.addProperty("TransactionDate", formatter.format(settle.getTransactionTimeStamp()));
			formatter = new SimpleDateFormat("HH:mm:ss");

			jsonObject.addProperty("TransactionTime", formatter.format(settle.getTransactionTimeStamp()));
			jsonObject.addProperty("CustomerName", settle.getCustomerName());
			jsonObject.addProperty("MerchantID", settle.getMerchantID());
			jsonObject.addProperty("BankReference", settle.getBankReference());

			jsonObject.addProperty("Status", settle.getStatus());
			jsonObject.addProperty("SettlementDate", settle.getSettlementDate().toString());
			System.out.println(settle.getReconcileStatus());
			switch (settle.getReconcileStatus()) {
				case 3:
					jsonObject.addProperty("ReconcileStatus", "AutoReconciled");
					jsonObject.addProperty("ReconciledDate", settle.getReconciledDateTime().toString());
					break;
				case 2:
					jsonObject.addProperty("ReconcileStatus", "Manually Reconciled");
					jsonObject.addProperty("ReconciledDate", settle.getReconciledDateTime().toString());
					break;
				case 1:
					jsonObject.addProperty("ReconcileStatus", "AutoReconciler attempted but failed");
					jsonObject.addProperty("ReconciledDate", settle.getReconciledDateTime().toString());
					break;
				case 0:
					jsonObject.addProperty("ReconcileStatus", "Not Reconciled by AutoReconciler");
					jsonObject.addProperty("ReconciledDate", "");
					break;
				default:
					jsonObject.addProperty("ReconcileStatus", "Reconcile Status Incorrectly Set");
					jsonObject.addProperty("ReconciledDate", "");
					break;
			}

			jsonObject.addProperty("CardPAN", settle.getCardPAN());
			jsonObject.addProperty("CardScheme", settle.getCardSchemeName());
			jsonObject.addProperty("CardExpiry", settle.getCardExpiry());

			jsonObject.addProperty("PrincipleAmount", settle.getPrincipalAmount());
			jsonObject.addProperty("Surcharge", settle.getSurcharge());
			jsonObject.addProperty("Currency", settle.getCurrency());

		}else{
			jsonObject.addProperty("result","fail");
		}
		return jsonObject.toString();
	}

	@Override
	public String markAsReconciled(String id) {
		Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(id));
		JsonObject jsonObject = new JsonObject();
		if(stmt.isPresent()){
			SettlementStmt settle = stmt.get();
			settle.setReconcileStatus(2);
			settle.setReconciledDateTime(new Date());
			settlementStmtRepo.save(settle);
			jsonObject.addProperty("result","success");
		}else{
			jsonObject.addProperty("result","fail");
		}
		return jsonObject.toString();
	}

	@Override
	public String markAsNotReconciled(String id) {
		Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(id));
		JsonObject jsonObject = new JsonObject();
		if(stmt.isPresent()){
			SettlementStmt settle = stmt.get();
			settle.setReconcileStatus(0);
			settle.setReconciledDateTime(new Date());
			settlementStmtRepo.save(settle);
			jsonObject.addProperty("result","success");
		}else{
			jsonObject.addProperty("result","fail");
		}
		return jsonObject.toString();
	}

	@Override
	public String performBulkReconcile(boolean option, String[] allReciptNums) {

		for (String eachId : allReciptNums) {
			Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(eachId));
			try {
				SettlementStmt stmtFound = stmt.get();

				if (option) {
					stmtFound.setReconcileStatus(2);
					stmtFound.setReconciledDateTime(new Date());
				} else {
					stmtFound.setReconcileStatus(0);
					stmtFound.setReconciledDateTime(new Date());
				}
				settlementStmtRepo.save(stmtFound);
			} catch (Exception e) {
				return "{\"result\":\"fail\",\"reason\":\"" + e.getMessage() + "\"}";
			}
		}

		return "{\"result\":\"success\",\"reason\":\"Done\"}";
	}

}
