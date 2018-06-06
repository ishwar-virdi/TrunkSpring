package com.trunk.demo.service.mongo;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface DashboardManager {
	public String getReconcileData(String userId);

	public String getMonthTotal(int page);

	public String getDailyTransaction(int page);

}
