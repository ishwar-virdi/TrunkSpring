package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.User;

import java.util.List;

public interface UserBO {
    public List<User> findByUsername(String username);

    public User findById(String _id);

    public void save(User user);
}
