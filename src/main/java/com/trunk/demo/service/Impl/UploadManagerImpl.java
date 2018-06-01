package com.trunk.demo.service.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trunk.demo.Util.DocumentType;
import com.trunk.demo.repository.RedisRepository;

import com.trunk.demo.vo.UploadReiewListVO;
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
	private RedisRepository redisRepository;

	@Autowired
	private Gson gson;
    private DocumentType documentType;
	@Override
	public String newUploadFile(String type, String fileName, InputStream inputStream) {

		InputStream streamForMongo;
		try {
            redisRepository.deleteCache();
			streamForMongo = IOUtils.toBufferedInputStream(inputStream);
			String result = new String();
//			String s3Reponse = s3Service.newUploadFile(type, fileName, inputStream);

//			if (s3Reponse.equalsIgnoreCase("SUCCESS")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(streamForMongo));
				if (type.equalsIgnoreCase("Bank")){
					redisRepository.putType(documentType.BANKSTATEMENT.name());
					redisRepository.putFileName(fileName);
					result = uploadBankCSV(br);
				}
				else if (type.equalsIgnoreCase("Settlement")){
					redisRepository.putType(documentType.SETTLEMENT.name());
					redisRepository.putFileName(fileName);
					result = uploadSettlementCSV(br);
				}
//				else
//					return "{\"result\":\"fail\",\"reason\":\"Invalid File Type\"}";
//			} else {
//				return s3Reponse;
//			}
			if (result.contains("success"))
				reconcileService.reconcile();
			return result;
		} catch (IOException e) {
			return "{\"result\":\"fail\",\"reason\":\"Fatal Error:" + e.getMessage() + "\"}";
		}
	}

    @Override
    public String retrieveUploadRecords() {
        JsonObject jsonObject = new JsonObject();
        Object fileName = redisRepository.getFileName();
        Object type = redisRepository.getType();
        int id = 0;
        boolean flag = false;
        Object transaction;
        JsonObject transactionJson;
		UploadReiewListVO uploadReiewList;
		List<T> transactions = new ArrayList<>();
        if(fileName == null){
            jsonObject.addProperty("result","fail");
            return jsonObject.toString();
        }else{
            flag = true;
        }

        while (flag){
            transaction = redisRepository.getTransaction(id);
            if(transaction == null){
                flag = false;
                break;
            }
            transactionJson = new JsonParser().parse(transaction.toString()).getAsJsonObject();
            if(documentType.SETTLEMENT.name().equals(type.toString())){
                UploadReviewSettleVO settleVO = new UploadReviewSettleVO(transactionJson);
				transactions.add((T) settleVO);
            }else if(documentType.BANKSTATEMENT.name().equals(type.toString())){
				UploadReviewBankVO bankStmtVO = new UploadReviewBankVO(transactionJson);
				transactions.add((T) bankStmtVO);
            }

            id++;
        }
		uploadReiewList = new UploadReiewListVO(fileName.toString(),type.toString(),transactions);
        return gson.toJson(uploadReiewList);
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
					SettlementStmt newStmt = new SettlementStmt(elements[1], elements[2], elements[4], elements[7],
							Double.parseDouble(elements[10].isEmpty() ? "0" : elements[10]),
							Double.parseDouble(elements[11].isEmpty() ? "0" : elements[11]), elements[13], elements[16],
							elements[24], Long.parseLong(elements[25].isEmpty() ? "0" : elements[25]), elements[26],
							elements[27], elements[29], elements[30], "");
					redisRepository.putSettlement(id,newStmt);
					settlementStmtRepo.insert(newStmt);
					id++;
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
						redisRepository.putBankStatement(id,newStmt);
						bankStmtRepo.insert(newStmt);
						id++;
					}
				}
			}
			br.close();
			return "{\"result\":\"success\",\"reason\":\"Bank Statement has been Uploaded & pushed to system\"}";
		} catch (Exception e) {
			return "{\"result\":\"fail\",\"reason\":\"Fatal Error:" + e.getMessage() + ". File is Incorrect.\"}";
		}
	}

	private String reverseDate(String date) {
		if (date.length() < 8)
			date = "0" + date;

		return date.substring(4, 8) + date.substring(2, 4) + date.substring(0, 2);
	}

}
