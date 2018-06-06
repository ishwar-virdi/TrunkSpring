package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.SettlementStmt;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SettlementBO {

	public List<SettlementStmt> findAllByCardSchemeNameAmex(Date fromDate, Date toDate);

	public List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date fromDate, Date toDate);

	public List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date fromDate, Date toDate);

	public SettlementStmt findByReceiptNumber(Long receiptNumber);

	public List<SettlementStmt> findAllByReconcileResultsId(String id);

	public List<SettlementStmt> findAllBySettlementDateBetween(Date startDate, Date endDate, Sort sort);

	List<SettlementStmt> findAllBySettlementDateBetweenValues(Date startDate, Date endDate);

	public SettlementStmt findFirstByReceiptNumber(Long receiptNumber);

	public void save(SettlementStmt settlementStmt);

    public void saveAll(List<SettlementStmt> settlementDocument);

	List<SettlementStmt> findAll();

	public Optional<SettlementStmt> findById(Long _id);

	public void insert(SettlementStmt settlementStmt);
}
