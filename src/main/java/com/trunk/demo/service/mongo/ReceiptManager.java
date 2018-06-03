package com.trunk.demo.service.mongo;

import org.springframework.stereotype.Service;

@Service
public interface ReceiptManager {

	public String getReceipt(String id);

	public String markAsReconciledOrNot(String id, boolean option);

	//public String performBulkReconcile(boolean option, String[] allReciptNums);

}
