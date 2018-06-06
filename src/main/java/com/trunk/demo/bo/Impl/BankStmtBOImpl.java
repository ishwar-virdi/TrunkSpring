package com.trunk.demo.bo.Impl;

import com.trunk.demo.bo.BankStmtBO;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.repository.BankStmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BankStmtBOImpl implements BankStmtBO {
    @Autowired
    private BankStmtRepository bankStmtRepository;


    @Override
    public List<BankStmt> findAllByDateBetween(Date startDate, Date endDate, Sort sort) {
        return bankStmtRepository.findAllByDateBetween(startDate,endDate,sort);
    }

    @Override
    public Optional<BankStmt> findById(int id) {
        return bankStmtRepository.findById(id);
    }

    @Override
    public List<BankStmt> findAllBetweenDates(Date startDate, Date endDate) {
        return bankStmtRepository.findAllBetweenDates(startDate,endDate);
    }

    @Override
    public void insert(BankStmt bankStmt){
        bankStmtRepository.insert(bankStmt);
    }
}
