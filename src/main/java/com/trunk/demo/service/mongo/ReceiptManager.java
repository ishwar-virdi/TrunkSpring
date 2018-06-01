package com.trunk.demo.service.mongo;

import org.springframework.stereotype.Service;

@Service
public interface ReceiptManager {

	public String getReceipt(String id);

	public String markAsReconciled(String id);

	public String performBulkReconcile(boolean option, String[] allReciptNums);

    public String markAsNotReconciled(String id);
}
