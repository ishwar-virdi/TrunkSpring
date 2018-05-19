package com.trunk.demo.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trunk.demo.model.mongo.ReconcileDetail;
import com.trunk.demo.repository.ReconcileDetailsRepository;
import com.trunk.demo.repository.TokenRepository;
import com.trunk.demo.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("detailServiceImpl")
public class DetailServiceImpl implements DetailService {

    @Autowired
    private ReconcileDetailsRepository detailsRepository;
    @Autowired
    private Gson gson;
    @Override
    public String details(String id) {

        String respons = null;
        Optional<ReconcileDetail> detailQuery = detailsRepository.findById(id);
        if(detailQuery.isPresent()){
            ReconcileDetail details = detailQuery.get();
            respons = gson.toJson(details.getList()).toString();
        }else{
            JsonObject json = new JsonObject();
            json.addProperty("result","fail");
            respons = json.toString();
        }

        //json.addProperty("token", tokenRepository.generateToken());
        return respons.toString();
    }
}
