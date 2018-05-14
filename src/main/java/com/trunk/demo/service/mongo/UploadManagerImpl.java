package com.trunk.demo.service.mongo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.BankStmtRepository;
import com.trunk.demo.interfaces.mongo.SettlementRepository;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class UploadManagerImpl implements UploadManager {

	@Autowired
	private BankStmtRepository bankStmtRepo;
	@Autowired
	private SettlementRepository settlementStmtRepo;

	@Override
	public void newUploadFile(String type, InputStream inputStream) {

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		if (type.equalsIgnoreCase("Bank"))
			uploadBankCSV(br);
		else if (type.equalsIgnoreCase("Settlement"))
			uploadSettlementCSV(br);
		else
			System.out.println("Invalid Type");

	}

	private void uploadSettlementCSV(BufferedReader br) {

		String line = new String();
		try {
			// Skipping Header Row
			br.readLine();

			while ((line = br.readLine()) != null) {
				if (line != "") {
					line = line.replaceAll("\"", "");
					String elements[] = line.split(",");
					SettlementStmt newStmt = new SettlementStmt(elements[1], elements[2], elements[4], elements[7],
							Double.parseDouble(elements[10].isEmpty() ? "0" : elements[10]),
							Double.parseDouble(elements[11].isEmpty() ? "0" : elements[11]), elements[13], elements[16],
							elements[24], Long.parseLong(elements[25].isEmpty() ? "0" : elements[25]), elements[26],
							elements[27], elements[29], elements[30]);
					settlementStmtRepo.insert(newStmt);
				}
			}
			br.close();

		} catch (Exception e) {
			System.out.println("ERROR IN MONGO INSERT OF SETTLEMENT STMT");
			System.out.println(e.getMessage());

		}

	}

	private void uploadBankCSV(BufferedReader br) {
		String line = new String();
		try {
			// Skipping Header Row
			br.readLine();

			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					String elements[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					if (!(elements[0].equalsIgnoreCase("Total value of transactions:")
							|| elements[0].equalsIgnoreCase("Number of transactions:"))) {
						
						for (int i = 0; i < elements.length; i++)
							elements[i] = elements[i].replaceAll("\"", "");
						
						BankStmt newStmt = new BankStmt(elements[0],
								Long.parseLong(elements[1].isEmpty() ? "0" : elements[1]), elements[2], reverseDate(elements[3]),
								elements[4], Double.parseDouble(elements[5].isEmpty() ? "0" : elements[5]),
								Double.parseDouble(elements[6].isEmpty() ? "0" : elements[6]),
								Double.parseDouble(elements[7].isEmpty() ? "0" : elements[7]));
						bankStmtRepo.insert(newStmt);
					}
				}
			}
			br.close();

		} catch (Exception e) {
			System.out.println("ERROR IN MONGO INSERT OF BANK STMT");
			System.out.println(e.getMessage());

		}
	}
	
	private String reverseDate(String date) {
		if (date.length() < 8)
			date = "0" + date;
		
		return date.substring(4, 8) + date.substring(2, 4) + date.substring(0, 2);
	}

}
