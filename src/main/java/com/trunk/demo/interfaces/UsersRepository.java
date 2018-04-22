package com.trunk.demo.interfaces;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.trunk.demo.pojo.User;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
	@Query(value="{ 'username' : ?0 }")
	public List<User> findByUsername (String username);
}
