package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.SettlementStmt;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface SettlementBO {
	public List<SettlementStmt> findAllBySettlementDateLike(String settlementDate);

	public List<SettlementStmt> findAllByCardSchemeNameAmex(Date fromDate, Date toDate);

	public List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date fromDate, Date toDate);

	public List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date fromDate, Date toDate);

	public SettlementStmt findByReceiptNumber(Long receiptNumber);

	public List<SettlementStmt> findAllByReconcileResultsId(String id);

	public List<SettlementStmt> findAllBySettlementDateBetween(Date startDate, Date endDate, Sort sort);

	public SettlementStmt findFirstByReceiptNumber(Long receiptNumber);

	void save(SettlementStmt settlementStmt);

}
