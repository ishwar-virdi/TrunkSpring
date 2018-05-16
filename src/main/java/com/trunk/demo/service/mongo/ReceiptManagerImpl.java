package com.trunk.demo.service.mongo;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.SettlementRepository;
import com.trunk.demo.model.mongo.SettlementStmt;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class ReceiptManagerImpl implements ReceiptManager {

	@Autowired
	private SettlementRepository settlementStmtRepo;

	@Override
	public String getReceipt(String id) {
		Optional<SettlementStmt> stmt = settlementStmtRepo.findById(Long.parseLong(id));
		try {
			SettlementStmt stmtFound = stmt.get();
			JSONObject result = new JSONObject();

			result.put("TransactionDate", stmtFound.getTransactionTimeStamp().getDayOfMonth()+"-"+stmtFound.getTransactionTimeStamp().getMonthValue()+"-"+stmtFound.getTransactionTimeStamp().getYear());
			result.put("TransactionTime", stmtFound.getTransactionTimeStamp().getHour()+":"+stmtFound.getTransactionTimeStamp().getMinute());
			result.put("CustomerName", stmtFound.getCustomerName());
			result.put("MerchantID", stmtFound.getMerchantID());
			result.put("BankReference", stmtFound.getBankReference());

			result.put("Status", stmtFound.getStatus());
			result.put("SettlementDate", stmtFound.getSettlementDate().toString());
			
			switch(stmtFound.getReconcileStatus()) {
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

			return result.toString();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("ERROR : Recipt Not Found");
			return "ERROR : Recipt Not Found";
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("ERROR : JSON Error");
			return "ERROR : JSON Error";
		}
	}

}
