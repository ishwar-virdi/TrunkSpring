package com.trunk.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.SettlementRepository;
import com.trunk.demo.service.mongo.ResultDetailManager;
import com.trunk.demo.vo.ListResultDetailsVO;

@Service
public class ResultDetailManagerImpl implements ResultDetailManager {
	@Autowired
	private SettlementRepository settlementRepo;
	
	@Override
	public String getResultDetail(String id) {
		List<SettlementStmt> settlementItems = settlementRepo.findAllByReconcileResultsId(id);
		ListResultDetailsVO vo = new ListResultDetailsVO(settlementItems);
		return vo.getJSON();
	}

}
