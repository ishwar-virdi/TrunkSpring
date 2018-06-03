package com.trunk.demo.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.trunk.demo.model.mongo.SettlementStmt;

public class ListResultDetailsVO {
	private List<SettlementItem> list;
	
	public ListResultDetailsVO (List<SettlementStmt> list) {
		this.list = new ArrayList<SettlementItem>();
		
		for (SettlementStmt item : list) {
			this.list.add(new SettlementItem(item));
		}
	}
	
	public String getJSON() {
		return new Gson().toJson(this.list);
	}
}

class SettlementItem {
	private Date date;
	private String description;
	private double amount;
	private double accountNumber;
	private String transactionType;
	private int status;
	private String rule;
	
	public SettlementItem (SettlementStmt settlementItem){
		this.date = settlementItem.getSettlementDate();
		this.description = settlementItem.getCustomerName();
		this.amount = settlementItem.getPrincipalAmount();
		this.accountNumber = settlementItem.getReceiptNumber();
		if (!"".equals(settlementItem.getCardSchemeName()))
			this.transactionType = settlementItem.getCardSchemeName();
		else
			this.transactionType = "Direct Debit";
		
		if(settlementItem.getReconcileStatus() == 0 || settlementItem.getReconcileStatus() == 1)
			this.status = 0;
		else
			this.status = 1;
		
		switch (settlementItem.getReconcileStatus()) {
		case 4:{
			this.rule = "Manually Not Reconciled";
			break;
		}
		case 3:{
			this.rule = "Auto Reconciled";
			break;
		}
		case 2:{
			this.rule = "Manually Reconciled";
			break;
		}
		case 1:{
			this.rule = "Auto Reconciled attempted but failed";
			break;
		}
		case 0:{
			this.rule = "Not reconciled manually or auto";
			break;
		}
		default:
			this.rule = "Invalid status";
			break;
		}
	}
	
	public String getJSON() {
		JSONObject json = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			json.put("date", formatter.format(this.date));
			json.put("description", this.description);
			json.put("amount", this.amount);
			json.put("accountNumber", this.accountNumber);
			json.put("transactionType", this.transactionType);
			json.put("status", this.status);
			json.put("rule", this.rule);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
}
