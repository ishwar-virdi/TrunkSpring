package com.trunk.demo.interfaces.mongo;

import com.trunk.demo.model.mongo.ReconcileResult;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultsRepository extends MongoRepository<ReconcileResult, String> {

	public List<ReconcileResult> findByUserId(String userId, Sort sort);

	/*
	 * Yifan needs to fix this up based on his code.
	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndReconcileDate(String userId, LocalDate date);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String userId,
			LocalDate from, LocalDate to);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndPercentage(String userId, double percentage);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndPercentageGreaterThanEqual(String userId, double percentage);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndPercentageLessThanEqual(String userId, double percentage);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public List<ReconcileResult> findByUserIdAndPercentageBetween(String userId, double lessThanValue,
			double largerThanValue);

	@Query(fields = "{'_id':0,'lastModified':1,'startDate':1,'endDate':1,'isReconciled':1,'notReconciled':1}")
	public Optional<ReconcileResult> findById(String _id);
	*/
}