package com.trunk.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.SettlementStmt;

@Repository
public interface SettlementRepository extends MongoRepository<SettlementStmt, Long> {

	@Query("{ 'settlementDate' : {$regex:?0 }}")
	List<SettlementStmt> findAllBySettlementDateLike(String settlementDate);

	@Query("{ $and : [ { 'cardSchemeName' : {$regex : '.*amex.*', $options : 'i' } } , { 'settlementDate' : { $gte : ?0 } } ] }")
    List<SettlementStmt> findAllByCardSchemeNameAmex(Date date);

    @Query("{ $and : [ { $or : [ {'cardSchemeName' : {$regex: '.*visa.*', $options : 'i'}}, {'cardSchemeName' : {$regex: '.*mastercard.*', $options : 'i'}}  ]}, { 'settlementDate' : { $gte : ?0 } } ] }")
    List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date date);

    @Query("{ $and : [ {'cardSchemeName' : ''}, { 'bankReference' : { $ne: '' } } , { 'settlementDate' : { $gte : ?0 } } ] }")
    List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date date);

	@Query("{ 'receiptNumber' : ?0 }")
	Optional<SettlementStmt> findByReceiptNumber(Long receiptNumber);
	
	List<SettlementStmt> findAllByReconcileResultsId(String id);

	List<SettlementStmt> findAllBySettlementDateBetween(Date startDate,Date endDate,Sort sort);

	SettlementStmt findFirstByReceiptNumber(Long receiptNumber);
}
