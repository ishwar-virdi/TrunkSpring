package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    public List<User> findByUsername(String username);
}
