//package com.trunk.demo.interfaces.mongo;
//
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.trunk.demo.model.mongo.User;
//
//@Repository
//public interface UsersRepository extends MongoRepository<User, String> {
//	@Query(value="{ 'username' : ?0 }")
//	public List<User> findByUsername (String username);
//}
