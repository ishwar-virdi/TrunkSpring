package com.trunk.demo.repository.Impl;

import com.google.gson.Gson;
import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.Util.DocumentType;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.SettlementStmt;
import com.trunk.demo.repository.RedisRepository;
import com.trunk.demo.vo.UploadReviewBankVO;
import com.trunk.demo.vo.UploadReviewSettleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Repository("RedisRepositoryImpl")
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private HttpSession session;
    @Autowired
    private Gson gson;
    @Autowired
    private CalenderUtil cal;
    private final String type = "type";
    private final String filename = "filename";
    @Override
    public void putSettlement(int id,SettlementStmt stmt) {
        UploadReviewSettleVO uploadReviewSettle = new UploadReviewSettleVO(stmt.getReceiptNumber(),stmt.getCardSchemeName(),
                stmt.getPrincipalAmount(),stmt.getCustomerName());
        redisTemplate.opsForHash().put(session.getId(),String.valueOf(id),gson.toJson(uploadReviewSettle));
        redisTemplate.expire(session.getId(),60000,TimeUnit.MILLISECONDS);
    }

    @Override
    public void putBankStatement(int id,BankStmt stmt) {
        StringBuffer sb = new StringBuffer();
        sb.append(cal.getDateMonth(stmt.getDate())).append("/").
                append(cal.getDateDay(stmt.getDate())).append("/").
                append(cal.getDateYear(stmt.getDate()));
        UploadReviewBankVO uploadReviewBankStmtVO = new UploadReviewBankVO(sb.toString(),stmt.getTransactionDescription(),stmt.getCredits(),
                stmt.getDebits());
        redisTemplate.opsForHash().put(session.getId(),String.valueOf(id),gson.toJson(uploadReviewBankStmtVO));
        redisTemplate.expire(session.getId(),60000,TimeUnit.MILLISECONDS);
    }

    @Override
    public void putType(String type) {
        redisTemplate.opsForHash().put(session.getId(),this.type,type);
        redisTemplate.expire(session.getId(),60000,TimeUnit.MILLISECONDS);
    }

    @Override
    public void putFileName(String filename) {
        redisTemplate.opsForHash().put(session.getId(),this.filename,filename);
        redisTemplate.expire(session.getId(),60000,TimeUnit.MILLISECONDS);
    }

    @Override
    public Object getTransaction(int id) {
        return redisTemplate.opsForHash().get(session.getId(),String.valueOf(id));
    }


    @Override
    public Object getFileName() {
        return redisTemplate.opsForHash().get(session.getId(),this.filename);
    }

    @Override
    public void deleteCache() {
        redisTemplate.delete(session.getId());
    }

    @Override
    public Object getType() {
        return redisTemplate.opsForHash().get(session.getId(),this.type);
    }
}
