package com.trunk.demo.service.mongo;

import org.springframework.stereotype.Service;

@Service
public interface DashboardManager {
	public String getReconcileData();

	public String getMonthTotal(int page);

	public String getDailyTransaction(int page);

}
