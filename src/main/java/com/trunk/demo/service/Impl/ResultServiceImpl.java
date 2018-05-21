package com.trunk.demo.service.Impl;

import com.google.gson.Gson;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.service.ResultService;
import com.trunk.demo.vo.ListReconcileResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private Gson gson;
    
    private ReconcileResult result;

    @Override
    public String retrieveResults(HttpSession session,int pageIndex) {
        gson = new Gson();
        Object userSession;
        userSession = session.getAttribute(session.getId());
        List<ReconcileResult> results;
        ListReconcileResultVO resultsVO;

        if(userSession == null){
            return gson.toJson("UserSession is Null");
        }

        Pageable page = PageRequest.of(pageIndex,13,new Sort(Sort.Direction.DESC,"reconcileDate"));
        results = resultsRepository.findByUserId(userSession.toString(),page);

        resultsVO = new ListReconcileResultVO(results);

        return gson.toJson(resultsVO.getList());
    }

    @Override
    public String saveSeedData(HttpSession session) throws ParseException {
        Object userSession = session.getAttribute(session.getId());
        if(userSession == null){
            return gson.toJson("");
        }
        String userId = userSession.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        result = new ReconcileResult(userId,sdf.parse("20180401"),sdf.parse("20180501"),10,6);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20180301"),sdf.parse("20180401"),100,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20180201"),sdf.parse("20180301"),60,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20180101"),sdf.parse("20180201"),67,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20171101"),sdf.parse("20171201"),78,31);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20171001"),sdf.parse("20171101"),45,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20170901"),sdf.parse("20171001"),40,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20170801"),sdf.parse("20170901"),20,12);
        resultsRepository.save(result);
        result = new ReconcileResult(userId,sdf.parse("20170701"),sdf.parse("20170801"),50,12);
        resultsRepository.save(result);
        return "aaaa";
    }
}
