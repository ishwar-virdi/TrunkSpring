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

	@Query("{ $and : [ { 'cardSchemeName' : {$regex : '.*amex.*', $options : 'i' } } , { 'settlementDate' : { $gte : ?0 } }, { 'settlementDate' : { $lte : ?1 } } ] }")
	List<SettlementStmt> findAllByCardSchemeNameAmex(Date fromDate, Date toDate);

	@Query("{ $and : [ { $or : [ {'cardSchemeName' : {$regex: '.*visa.*', $options : 'i'}}, {'cardSchemeName' : {$regex: '.*mastercard.*', $options : 'i'}}  ]}, { 'settlementDate' : { $gte : ?0 } }, { 'settlementDate' : { $lte : ?1 } } ] }")
	List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard(Date fromDate, Date toDate);

	@Query("{ $and : [ {'cardSchemeName' : ''}, { 'bankReference' : { $ne: '' } } , { 'settlementDate' : { $gte : ?0 } }, { 'settlementDate' : { $lte : ?1 } } ] }")
	List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty(Date fromDate, Date toDate);

	@Query("{ 'receiptNumber' : ?0 }")
	Optional<SettlementStmt> findByReceiptNumber(Long receiptNumber);

	List<SettlementStmt> findAllByReconcileResultsId(String id);

	@Query("{ $and : [ { 'settlementDate' : { $gte : ?0 } }, { 'settlementDate' : { $lte : ?1 } } ] }")
	List<SettlementStmt> findAllBySettlementDateBetween(Date startDate, Date endDate, Sort sort);

	@Query("{ $and : [ { 'settlementDate' : { $gte : ?0 } }, { 'settlementDate' : { $lte : ?1 } } ] }")
	List<SettlementStmt> findAllBySettlementDateBetweenValues(Date startDate, Date endDate);

	SettlementStmt findFirstByReceiptNumber(Long receiptNumber);
}
