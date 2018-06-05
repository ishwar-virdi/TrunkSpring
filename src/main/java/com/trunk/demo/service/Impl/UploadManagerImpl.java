package com.trunk.demo.service.Impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.Util.DocumentType;
import com.trunk.demo.bo.RedisBO;

import com.trunk.demo.vo.UploadReviewListVO;
import com.trunk.demo.vo.UploadReviewBankVO;
import com.trunk.demo.vo.UploadReviewSettleVO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.service.ReconcileFiles;
import com.trunk.demo.service.mongo.UploadManager;
import com.trunk.demo.service.s3.S3Service;

@Service
public class UploadManagerImpl<T> implements UploadManager {

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ReconcileFiles reconcileService;

	@Autowired
	private BankStmtRepository bankStmtRepo;

	@Autowired
	private SettlementRepository settlementStmtRepo;

	@Autowired
	private RedisBO redisBO;

	@Autowired
	private Gson gson;

	@Autowired
	private CalenderUtil cal;
	private DocumentType documentType;
	private Set<Date> monthInvolved = new HashSet<>();

	@Override
	public String newUploadFile(String type, String fileName, InputStream inputStream) {

		try {
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			InputStream s3Stream = new ByteArrayInputStream(byteArray);
			InputStream mongoStream = new ByteArrayInputStream(byteArray);

			redisBO.deleteCache();
			String result = new String();
			String s3Reponse = s3Service.newUploadFile(type, fileName, s3Stream);

			if (s3Reponse.equalsIgnoreCase("SUCCESS")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(mongoStream));
				if (type.equalsIgnoreCase("Bank")) {
					redisBO.pushType(documentType.BANKSTATEMENT.name());
					redisBO.pushFileName(fileName);
					result = uploadBankCSV(br);
				} else if (type.equalsIgnoreCase("Settlement")) {
					redisBO.pushType(documentType.SETTLEMENT.name());
					redisBO.pushFileName(fileName);
					result = uploadSettlementCSV(br);
				} else
					return "{\"result\":\"fail\",\"reason\":\"Invalid File Type\"}";
			} else {
				return s3Reponse;
			}
			System.out.println(monthInvolved);
			if (result.contains("success"))
				reconcileService.reconcile(monthInvolved);

			return result;

		} catch (Exception e) {
			return "{\"result\":\"fail\",\"reason\":\"Fatal Error:" + e.getMessage() + "\"}";
		}
	}

	@Override
	public String retrieveUploadRecords() {
		JsonObject jsonObject = new JsonObject();
		Object fileName = redisBO.getFileName();
		Object type = redisBO.getType();
		int id = 0;
		boolean flag = false;
		Object transaction;
		UploadReviewListVO uploadReviewList;
		List<T> transactions = new ArrayList<>();
		if (fileName == null) {
			jsonObject.addProperty("result", "fail");
			return jsonObject.toString();
		} else {
			flag = true;
		}

		while (flag) {
			transaction = redisBO.getTransaction(id);
			if (transaction == null) {
				flag = false;
				break;
			}
			if (documentType.SETTLEMENT.name().equals(type.toString())) {
				if (transaction.getClass().getName().equalsIgnoreCase("com.trunk.demo.model.mongo.SettlementStmt")) {
					UploadReviewSettleVO settleVO = new UploadReviewSettleVO((SettlementStmt) transaction);
					transactions.add((T) settleVO);
				}
			} else if (documentType.BANKSTATEMENT.name().equals(type.toString())) {
				if (transaction.getClass().getName().equalsIgnoreCase("com.trunk.demo.model.mongo.BankStmt")) {
					UploadReviewBankVO bankStmtVO = new UploadReviewBankVO((BankStmt) transaction);
					transactions.add((T) bankStmtVO);
				}
			}
			id++;
		}
		uploadReviewList = new UploadReviewListVO(fileName.toString(), type.toString(), transactions);
		return gson.toJson(uploadReviewList);
	}

	private String uploadSettlementCSV(BufferedReader br) {

		String line = new String();
		try {
			// Skipping Header Row
			br.readLine();
			int id = 0;
			while ((line = br.readLine()) != null) {
				if (line != "") {
					line = line.replaceAll("\"", "");
					String elements[] = line.split(",");

					if (!settlementStmtRepo.findById(Long.parseLong(elements[25].isEmpty() ? "0" : elements[25]))
							.isPresent() && elements[24].equalsIgnoreCase("approved")) {
						SettlementStmt newStmt = new SettlementStmt(elements[1], elements[2], elements[4], elements[7],
								Double.parseDouble(elements[10].isEmpty() ? "0" : elements[10]),
								Double.parseDouble(elements[11].isEmpty() ? "0" : elements[11]), elements[13],
								elements[16], elements[24], Long.parseLong(elements[25].isEmpty() ? "0" : elements[25]),
								elements[26], elements[27], elements[29], elements[30], "");
						redisBO.pushTransaction(id, newStmt);
						monthInvolved.add(cal.firstDayOfThisMonth(newStmt.getSettlementDate()));
						settlementStmtRepo.insert(newStmt);
						id++;
					}
				}
			}
			br.close();
			return "{\"result\":\"success\",\"reason\":\"Settlement File has been Uploaded & pushed to system\"}";
		} catch (Exception e) {
			return "{\"result\":\"fail\",\"reason\":\"Fatal Error:" + e.getMessage() + ". File is Incorrect.\"}";
		}
	}

	private String uploadBankCSV(BufferedReader br) {

		String line = new String();
		try {
			// Skipping Header Row
			br.readLine();
			int id = 0;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					String elements[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					if (!(elements[0].equalsIgnoreCase("Total value of transactions:")
							|| elements[0].equalsIgnoreCase("Number of transactions:"))) {

						for (int i = 0; i < elements.length; i++)
							elements[i] = elements[i].replaceAll("\"", "");

						BankStmt newStmt = new BankStmt(elements[0],
								Long.parseLong(elements[1].isEmpty() ? "0" : elements[1]), elements[2],
								reverseDate(elements[3]), elements[4],
								Double.parseDouble(elements[5].isEmpty() ? "0" : elements[5]),
								Double.parseDouble(elements[6].isEmpty() ? "0" : elements[6]),
								Double.parseDouble(elements[7].isEmpty() ? "0" : elements[7]));
						if (!bankStmtRepo.findById(newStmt.hashCode()).isPresent()) {
							redisBO.pushTransaction(id, newStmt);
							monthInvolved.add(cal.firstDayOfThisMonth(newStmt.getDate()));
							bankStmtRepo.insert(newStmt);
							id++;
						}
					}
				}
			}
			br.close();
			return "{\"result\":\"success\",\"reason\":\"Bank Statement File has been Uploaded & pushed to system\"}";
		} catch (Exception e) {
			System.out.println(e);
			return "{\"result\":\"fail\",\"reason\":\"Fatal Error:" + e.getMessage() + ". File is Incorrect.\"}";
		}
	}

	private String reverseDate(String date) {
		if (date.length() < 8)
			date = "0" + date;

		return date.substring(4, 8) + date.substring(2, 4) + date.substring(0, 2);
	}

}
