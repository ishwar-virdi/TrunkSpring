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

			result.put("TransactionDate", stmtFound.getTransactionDate());
			result.put("TransactionTime", stmtFound.getTransactionTime());
			result.put("CustomerName", stmtFound.getCustomerName());
			result.put("MerchantID", stmtFound.getMerchantID());
			result.put("BankReference", stmtFound.getBankReference());

			result.put("Status", stmtFound.getStatus());
			result.put("SettlementDate", stmtFound.getSettlementDate());
			if(stmtFound.isReconciled()) {
				result.put("IfReconciled", "Yes");
				result.put("ReconciledDate", stmtFound.getReconciledDateTime().toString());
			}else {
				result.put("IfReconciled", "No");
				result.put("ReconciledDate", "");
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
