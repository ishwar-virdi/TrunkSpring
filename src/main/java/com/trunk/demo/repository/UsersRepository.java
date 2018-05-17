package com.trunk.demo.repository;

import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    public List<User> findByUsername(String username);

    public Optional<User> findById(String _id);

}
