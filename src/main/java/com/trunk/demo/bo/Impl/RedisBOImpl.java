package com.trunk.demo.bo.Impl;

import com.google.gson.Gson;
import com.trunk.demo.bo.RedisBO;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisBOImpl implements RedisBO {

    @Autowired
    private Gson gson;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private HttpSession session;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final String type = "type";
    private final String filename = "filename";

    private final String date = "date";
    @Override
    public void pushTransaction(int id, Object settle) {
        redisRepository.pushObjectToHash(session.getId(),String.valueOf(id),settle);
        redisRepository.setObjectExpireTime(session.getId(),60);
    }

    @Override
    public void pushType(String type) {
        redisRepository.pushStringToHash(session.getId(),this.type,type);
        redisRepository.setExpireTime(session.getId(),60);
    }

    @Override
    public void pushFileName(String filename) {
        redisRepository.pushStringToHash(session.getId(),this.filename,filename);
        redisRepository.setExpireTime(session.getId(),60);
    }


    @Override
    public Object getTransaction(int id) {
        return redisRepository.getObjectFromHash(session.getId(),String.valueOf(id));
    }

    @Override
    public Object getFileName() {
        return redisRepository.getStringFromHash(session.getId(),this.filename);
    }

    @Override
    public Object getType() {
        return redisRepository.getStringFromHash(session.getId(),this.type);
    }

    @Override
    public void pushTransactionDate(Set<String> sets) {
        int i = 0;
        for(String date:sets){
            redisRepository.pushStringToHash(session.getId()+"date", String.valueOf(i) ,date);
            i++;
        }
    }

    @Override
    public Object getTransactionDate(String id){
        Object dateString = redisRepository.getStringFromHash(session.getId()+"date", id);
        redisTemplate.opsForHash().delete(session.getId()+"date",id,dateString);
        return dateString;

    }
    @Override
    public void deleteSpecificValue(String value){
        redisRepository.deleteSpecificFromHash(session.getId()+"date",value);
    }

    @Override
    public void deleteCache() {
        redisRepository.deleteCache(session.getId());
    }


    @Override
    public void deleteObjectCache() {
        redisRepository.deleteObjectCache(session.getId());
    }

}
