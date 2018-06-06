package com.trunk.demo.bo;

import com.trunk.demo.model.mongo.BankStmt;
import org.springframework.data.domain.Sort;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface BankStmtBO {

    public List<BankStmt> findAllByDateBetween(Date startDate, Date endDate, Sort sort);

    public Optional<BankStmt> findById(int id);

    public List<BankStmt> findAllBetweenDates(Date startDate, Date endDate);

    public void insert(BankStmt bankStmt);
}
