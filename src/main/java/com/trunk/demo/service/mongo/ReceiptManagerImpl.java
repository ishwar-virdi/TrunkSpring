package com.trunk.demo.service.mongo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.SettlementRepository;

@Service
public class ReceiptManagerImpl implements ReceiptManager {

	@Autowired
	private SettlementRepository settlementStmtRepo;

	@Override
	public String getReceipt(String id) {
		Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(id));
		try {
			SettlementStmt stmtFound = stmt.get();
			JSONObject result = new JSONObject();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			result.put("TransactionDate", formatter.format(stmtFound.getTransactionTimeStamp()));
			formatter = new SimpleDateFormat("HH:mm:ss");

			result.put("TransactionTime", formatter.format(stmtFound.getTransactionTimeStamp()));
			result.put("CustomerName", stmtFound.getCustomerName());
			result.put("MerchantID", stmtFound.getMerchantID());
			result.put("BankReference", stmtFound.getBankReference());

			result.put("Status", stmtFound.getStatus());
			result.put("SettlementDate", stmtFound.getSettlementDate().toString());

			switch (stmtFound.getReconcileStatus()) {
			case 3:
				result.put("ReconcileStatus", "AutoReconciled");
				result.put("ReconciledDate", stmtFound.getReconciledDateTime().toString());
				break;
			case 2:
				result.put("ReconcileStatus", "Manually Reconciled");
				result.put("ReconciledDate", stmtFound.getReconciledDateTime().toString());
				break;
			case 1:
				result.put("ReconcileStatus", "AutoReconciler attempted but failed");
				result.put("ReconciledDate", stmtFound.getReconciledDateTime().toString());
				break;
			case 0:
				result.put("ReconcileStatus", "Not Reconciled by AutoReconciler");
				result.put("ReconciledDate", "");
				break;
			default:
				result.put("ReconcileStatus", "Reconcile Status Incorrectly Set");
				result.put("ReconciledDate", "");
				break;
			}

			result.put("CardPAN", stmtFound.getCardPAN());
			result.put("CardScheme", stmtFound.getCardSchemeName());
			result.put("CardExpiry", stmtFound.getCardExpiry());

			result.put("PrincipleAmount", stmtFound.getPrincipalAmount());
			result.put("Surcharge", stmtFound.getSurcharge());
			result.put("Currency", stmtFound.getCurrency());

			result.put("result", "success");
			result.put("reason", "");
			
			return result.toString();
		} catch (NoSuchElementException e) {
			return "{\"result\":\"fail\",\"reason\":" + e.getMessage() + "}";
		} catch (JSONException e) {
			return "{\"result\":\"fail\",\"reason\":" + e.getMessage() + "}";
		}
	}

	@Override
	public String markAsReconciled(String id) {
		Optional<SettlementStmt> stmt = settlementStmtRepo.findByReceiptNumber(Long.parseLong(id));
		try {
			SettlementStmt stmtFound = stmt.get();
			JSONObject result = new JSONObject();

			stmtFound.setReconcileStatus(2);
			stmtFound.setReconciledDateTime(new Date());

			settlementStmtRepo.save(stmtFound);

			result.put("result", "success");
			result.put("reason", "Receipt has been Manually marked as Reconciled");
			result.put("ReconcileStatus", "Manually Reconciled");
			result.put("ReconciledDate", stmtFound.getReconciledDateTime().toString());
			
			return result.toString();
		} catch (NoSuchElementException e) {
			return "{\"result\":\"fail\",\"reason\":" + e.getMessage() + "}";
		} catch (JSONException e) {
			return "{\"result\":\"fail\",\"reason\":" + e.getMessage() + "}";
		}
	}

}
