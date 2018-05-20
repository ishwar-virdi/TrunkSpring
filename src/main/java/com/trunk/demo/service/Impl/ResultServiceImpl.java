package com.trunk.demo.service.Impl;

import com.google.gson.Gson;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.UsersRepository;
import com.trunk.demo.service.mongo.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resultServiceImpl")
public class ResultServiceImpl implements ResultService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ResultsRepository resultsRepository;
    private ReconcileResult result;

    @Override
    public String retrieveResults() {
        Gson gson = new Gson();
        List<ReconcileResult> results = resultsRepository.findAll(new Sort(Sort.Direction.DESC, "reconcileDate"));
        return gson.toJson(results);
    }

    @Override
    public String saveSeedData() {
        List<User> user = usersRepository.findByUsername("test@test.com");
        String uid = user.get(0).getId();
        result = new ReconcileResult(uid,20180410,"17:10","2018031020180410",80);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180310,"17:10","2018021020180310",100);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180210,"17:10","2018011020180210",70);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180210,"17:10","2018010520180210",100);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180208,"17:10","2018010420180210",60);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180110,"17:10","2017110520171210",55);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20180101,"17:10","2017110520171210",67);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20170416,"17:10","2017110520171210",20);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20160216,"17:10","2017110520171210",50);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20160215,"17:10","2017110520171210",30);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20160214,"17:10","2017110520171210",60);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20150216,"17:10","2017110520171210",76);
        resultsRepository.save(result);
        result = new ReconcileResult(uid,20140216,"17:10","2017110520171210",100);
        resultsRepository.save(result);
        return "aaaa";
    }
}
