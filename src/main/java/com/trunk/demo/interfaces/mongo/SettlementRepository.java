package com.trunk.demo.interfaces.mongo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.model.mongo.SettlementStmt;

@Repository
public interface SettlementRepository extends MongoRepository<SettlementStmt, Long> {

	@Query("{ 'settlementDate' : {$regex:?0 }}")
	List<SettlementStmt> findAllBySettlementDateLike(String settlementDate);

	@Query("{ 'cardSchemeName' : {$regex : '.*amex.*', $options : 'i' } }")
	List<SettlementStmt> findAllByCardSchemeNameAmexAndDateBetween(Date start, Date end);

	@Query("{ $or : [ {'cardSchemeName' : {$regex: '.*visa.*', $options : 'i'}}, {'cardSchemeName' : {$regex: '.*mastercard.*', $options : 'i'}}  ]}")
	List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercardAndDateBetween(Date start,Date end);

	@Query("{ $and : [ {'cardSchemeName' : ''}, { 'bankReference' : { $ne: '' } } ] }")
	List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmptyAndDateBetween(Date start,Date end);

	@Query("{ 'receiptNumber' : ?0 }")
	Optional<SettlementStmt> findByReceiptNumber(Long receiptNumber);

}
