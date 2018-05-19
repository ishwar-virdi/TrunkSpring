package com.trunk.demo.interfaces.mongo;

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
	List<SettlementStmt> findAllByCardSchemeNameAmex();

	@Query("{ $or : [ {'cardSchemeName' : {$regex: '.*visa.*', $options : 'i'}}, {'cardSchemeName' : {$regex: '.*mastercard.*', $options : 'i'}}  ]}")
	List<SettlementStmt> findAllByCardSchemeNameVisaOrMastercard();

	@Query("{ $and : [ {'cardSchemeName' : ''}, { 'bankReference' : { $ne: '' } } ] }")
	List<SettlementStmt> findAllByCardSchemeNameEmptyAndBankReferenceNotEmpty();

	@Query("{ 'receiptNumber' : ?0 }")
	Optional<SettlementStmt> findByReceiptNumber(Long receiptNumber);

}
