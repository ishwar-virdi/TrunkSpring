package com.trunk.demo.bo.Impl;

import com.trunk.demo.bo.SettlementBO;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SettlementBOImpl implements SettlementBO {

	@Autowired
	private SettlementRepository settlementRepository;

	@Override
	public List<SettlementStmt> findAllByCardSchemeNameAmex(Date fromDate, Date toDate) {
		return settlementRepository.findAllByCardSchemeNameAmex(fromDate, toDate);
	}

	@Override
	public List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date fromDate, Date toDate) {
		return settlementRepository.findAllByCardSchemeNameVisaOrMastercard(fromDate, toDate);
	}

	@Override
	public List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date fromDate, Date toDate) {
		return settlementRepository.findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(fromDate, toDate);
	}

	@Override
	public SettlementStmt findByReceiptNumber(Long receiptNumber) {
		Optional<SettlementStmt> settle = settlementRepository.findByReceiptNumber(receiptNumber);
		if (settle.isPresent()) {
			return settle.get();
		}
		return null;
	}

	@Override
	public List<SettlementStmt> findAllByReconcileResultsId(String id) {
		return settlementRepository.findAllByReconcileResultsId(id);
	}

	@Override
	public List<SettlementStmt> findAllBySettlementDateBetween(Date startDate, Date endDate, Sort sort) {
		return settlementRepository.findAllBySettlementDateBetween(startDate, endDate, sort);
	}

	@Override
	public List<SettlementStmt> findAllBySettlementDateBetweenValues(Date startDate, Date endDate){
		return settlementRepository.findAllBySettlementDateBetweenValues(startDate, endDate);
	}
	@Override
	public SettlementStmt findFirstByReceiptNumber(Long receiptNumber) {
		return settlementRepository.findFirstByReceiptNumber(receiptNumber);
	}

	@Override
	public void save(SettlementStmt settlementStmt) {
		settlementStmt.setReconciledDateTime(new Date());
		settlementRepository.save(settlementStmt);
	}

	@Override
	public void saveAll(List<SettlementStmt> settles){
		settlementRepository.saveAll(settles);
	}

	@Override
	public List<SettlementStmt> findAll(){
		return settlementRepository.findAll();
	}

	@Override
	public Optional<SettlementStmt> findById(Long _id){
		return settlementRepository.findById(_id);
	}

	@Override
	public void insert(SettlementStmt settlementStmt){
		settlementRepository.insert(settlementStmt);
	}
}
