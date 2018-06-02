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
public class SettlementBOImpl implements SettlementBO{

    @Autowired
    private SettlementRepository settlementRepository;
    @Override
    public List<SettlementStmt> findAllBySettlementDateLike(String settlementDate) {
        return settlementRepository.findAllBySettlementDateLike(settlementDate);
    }

    @Override
    public List<SettlementStmt> findAllByCardSchemeNameAmex() {
        return settlementRepository.findAllByCardSchemeNameAmex();
    }

    @Override
    public List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard() {
        return settlementRepository.findAllByCardSchemeNameVisaOrMastercard();
    }

    @Override
    public List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty() {
        return settlementRepository.findAllByCardSchemeNameVisaOrMastercard();
    }

    @Override
    public SettlementStmt findByReceiptNumber(Long receiptNumber) {
        Optional<SettlementStmt> settle = settlementRepository.findByReceiptNumber(receiptNumber);
        if(settle.isPresent()){
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
    public SettlementStmt findFirstByReceiptNumber(Long receiptNumber) {
        return settlementRepository.findFirstByReceiptNumber(receiptNumber);
    }

    @Override
    public void save(SettlementStmt settlementStmt){
        settlementStmt.setReconciledDateTime(new Date());
        settlementRepository.save(settlementStmt);
    }
}
