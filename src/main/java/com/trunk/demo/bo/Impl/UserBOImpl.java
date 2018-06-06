package com.trunk.demo.bo.Impl;

import com.trunk.demo.bo.UserBO;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserBOImpl implements UserBO {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public List<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public User findById(String _id) {
        Optional<User> user = usersRepository.findById(_id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public void save(User user) {
        usersRepository.save(user);
    }
}
