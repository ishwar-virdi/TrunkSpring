package com.trunk.demo.repository.Impl;

import com.google.gson.Gson;
import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.Util.DocumentType;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.RedisRepository;
import com.trunk.demo.vo.UploadReviewBankVO;
import com.trunk.demo.vo.UploadReviewSettleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository("RedisRepositoryImpl")
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void pushStringToHash(String h,String key, String value){
        stringRedisTemplate.opsForHash().put(h, key, value);
    }

    @Override
    public Object getStringFromHash(String h,String key){
        return stringRedisTemplate.opsForHash().get(h, key);
    }
    @Override
    public void setExpireTime(String h,int seconds){
        stringRedisTemplate.expire(h,seconds,TimeUnit.SECONDS);
    }

    @Override
    public void deleteSpecificFromHash(String h, String value){
        stringRedisTemplate.opsForHash().delete(h,value);
    }

    @Override
    public void deleteCache(String h) {
        stringRedisTemplate.delete(h);
    }

    @Override
    public void pushObjectToHash(String h,String key, Object value) {
        redisTemplate.opsForHash().put(h, key, value);
    }

    @Override
    public Object getObjectFromHash(String h,String key){
        return redisTemplate.opsForHash().get(h, key);
    }

    @Override
    public void setObjectExpireTime(String h,int seconds){
        redisTemplate.expire(h,seconds,TimeUnit.SECONDS);
    }

    @Override
    public void deleteObjectCache(String h) {
        redisTemplate.delete(h);
    }

}
