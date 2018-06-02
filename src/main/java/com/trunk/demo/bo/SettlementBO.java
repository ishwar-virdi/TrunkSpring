package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.SettlementStmt;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SettlementBO {
    public List<SettlementStmt> findAllBySettlementDateLike(String settlementDate);

    public List<SettlementStmt> findAllByCardSchemeNameAmex(Date date);

    public List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date date);

    public List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date date);

    public SettlementStmt findByReceiptNumber(Long receiptNumber);

    public List<SettlementStmt> findAllByReconcileResultsId(String id);

    public  List<SettlementStmt> findAllBySettlementDateBetween(Date startDate, Date endDate, Sort sort);

    public SettlementStmt findFirstByReceiptNumber(Long receiptNumber);

    void save(SettlementStmt settlementStmt);

}
