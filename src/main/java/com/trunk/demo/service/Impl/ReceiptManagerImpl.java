package com.trunk.demo.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;
import com.trunk.demo.bo.ReconcileResultBO;
import com.trunk.demo.bo.SettlementBO;
import com.trunk.demo.model.mongo.ReconcileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.service.mongo.ReceiptManager;

@Service
public class ReceiptManagerImpl implements ReceiptManager {

	@Autowired
	private SettlementBO settlementBO;
	@Autowired
	private ReconcileResultBO reconcileResultBO;

	@Override
	public String getReceipt(String id) {
		JsonObject jsonObject = new JsonObject();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SettlementStmt settle = settlementBO.findByReceiptNumber(Long.parseLong(id));

		if (settle == null) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		}

		if ("".equals(settle.getCardSchemeName())) {
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

		jsonObject.addProperty("ReconcileStatusCode", settle.getReconcileStatus());

		switch (settle.getReconcileStatus()) {
		case 4:
			jsonObject.addProperty("ReconcileStatus", "Manually Not Reconciled");
			jsonObject.addProperty("ReconciledDate", settle.getReconciledDateTime().toString());
			break;
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
			jsonObject.addProperty("ReconcileStatus", "AutoReconciler not attempted");
			jsonObject.addProperty("ReconciledDate", settle.getReconciledDateTime());
			break;
		default:
			jsonObject.addProperty("ReconcileStatus", "Invalid status");
			jsonObject.addProperty("ReconciledDate", "");
			break;
		}

		jsonObject.addProperty("CardPAN", settle.getCardPAN());
		jsonObject.addProperty("CardScheme", settle.getCardSchemeName());
		jsonObject.addProperty("CardExpiry", settle.getCardExpiry());

		jsonObject.addProperty("PrincipleAmount", settle.getPrincipalAmount());
		jsonObject.addProperty("Surcharge", settle.getSurcharge());
		jsonObject.addProperty("Currency", settle.getCurrency());

		return jsonObject.toString();
	}

	@Override
	public String markAsReconciledOrNot(String id, boolean option) {
		SettlementStmt settle = settlementBO.findByReceiptNumber(Long.parseLong(id));
		JsonObject jsonObject = new JsonObject();
		String resultsId;
		ReconcileResult reconcileResult = null;
		if (settle == null) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		}

		resultsId = settle.getReconcileResultsId();
		if (resultsId == null) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		}

		reconcileResult = reconcileResultBO.findById(resultsId);
		if (option) {
			settle.setReconcileStatus(2);
			reconcileResultBO.updateIncreaseIsReconcile(1, reconcileResult);
		} else {
			settle.setReconcileStatus(4);
			reconcileResultBO.updateIncreaseNotReconcile(1, reconcileResult);
		}
		settlementBO.save(settle);

		jsonObject.addProperty("result", "success");
		jsonObject.addProperty("time", new Date().toString());

		return jsonObject.toString();
	}

	// @Override
	// public String performBulkReconcile(boolean option, String[] allReciptNums) {
	//
	// for (String eachId : allReciptNums) {
	// Optional<SettlementStmt> stmt =
	// settlementStmtRepo.findByReceiptNumber(Long.parseLong(eachId));
	// try {
	// SettlementStmt stmtFound = stmt.get();
	//
	// if (option) {
	// stmtFound.setReconcileStatus(2);
	// stmtFound.setReconciledDateTime(new Date());
	// } else {
	// stmtFound.setReconcileStatus(0);
	// stmtFound.setReconciledDateTime(new Date());
	// }
	// settlementStmtRepo.save(stmtFound);
	// } catch (Exception e) {
	// return "{\"result\":\"fail\",\"reason\":\"" + e.getMessage() + "\"}";
	// }
	// }
	//
	// return "{\"result\":\"success\",\"reason\":\"Done\"}";
	// }

}
