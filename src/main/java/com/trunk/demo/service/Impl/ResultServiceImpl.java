package com.trunk.demo.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trunk.demo.Util.CalenderUtil;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private Gson gson;

    @Autowired
    private CalenderUtil cal;

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

        Pageable page = PageRequest.of(pageIndex,20,new Sort(Sort.Direction.DESC,"lastModified"));
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

    @Override
    public String resultSearch(String userId, String page, String value) {
        String json = "";

        if(!"result".equals(page)){
            return "fail";
        }

        ListReconcileResultVO results;
        int dotIndex = value.indexOf(".");
        int lessSignIndex = value.indexOf("<");
        int largeSignIndex = value.indexOf(">");
        int hyphenIndex = value.indexOf("-");
        int slashIndex = value.indexOf("/");
        int secondSlash = value.indexOf("/", slashIndex + 1);

        try {
            // Last Modified
            if (value.length() >= 10 && dotIndex == 6) {
                value = value.substring(0,10);

                if(value.contains("May")){
                    value = value.replace(".","");
                }

                Date date = new SimpleDateFormat("dd MMM yy").parse(value);

                Date firstDayOfDate = cal.setDateToInit(date);
                Date lastDayOfDate = cal.setDateToMax(date);

                results = new ListReconcileResultVO(
                        resultsRepository.findByUserIdAndLastModifiedBetween(userId, firstDayOfDate, lastDayOfDate)
                );
                json = gson.toJson(results.getList());
            }
            // dateRange
            else if (value.length() >= 21 && secondSlash - slashIndex == 3 && hyphenIndex != -1) {
                String dates[] = value.split("-");
                Date startDate = stringToDate(dates[0]);
                Date endDate = getNextDate(dates[1]);
                results = new ListReconcileResultVO(resultsRepository
                        .findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(userId, startDate, endDate));
                json = gson.toJson(results.getList());
            }
            // percentage
            else if (value.length() <= 3 && largeSignIndex == -1 && lessSignIndex == -1) {
                int percentage = Integer.parseInt(value);
                if (percentage > 100 || percentage < 0) {
                    return "fail";
                }
                results = new ListReconcileResultVO(resultsRepository.findByUserIdAndPercentage(userId, percentage));
                json = gson.toJson(results.getList());
            }
            // Retrieve percentage by greater than
            else if (value.length() <= 4 && largeSignIndex != -1 && lessSignIndex == -1) {
                int percentage = Integer.parseInt(value.replace(">", ""));
                if (percentage > 100 || percentage < 0) {
                    return "fail";
                }
                results = new ListReconcileResultVO(
                        resultsRepository.findByUserIdAndPercentageGreaterThanEqual(userId, percentage));
                json = gson.toJson(results.getList());
            }
            // Retrieve percentage by less than
            else if (value.length() <= 4 && largeSignIndex == -1 && lessSignIndex != -1) {
                int percentage = Integer.parseInt(value.replace("<", ""));
                if (percentage > 100 || percentage < 0) {
                    return "fail";
                }
                results = new ListReconcileResultVO(
                        resultsRepository.findByUserIdAndPercentageLessThanEqual(userId, percentage));
                json = gson.toJson(results.getList());
            }
            // Retrieve percentage by greater than and less than
            else if (value.length() <= 8 && largeSignIndex != -1 && lessSignIndex != -1) {
                int lessThanValue = 0;
                int largerThanValue = 0;

                if (largeSignIndex > lessSignIndex) {
                    lessThanValue = Integer.parseInt(value.substring(lessSignIndex + 1, largeSignIndex));
                    largerThanValue = Integer.parseInt(value.substring(largeSignIndex + 1));
                } else {
                    lessThanValue = Integer.parseInt(value.substring(lessSignIndex + 1));
                    largerThanValue = Integer.parseInt(value.substring(largeSignIndex + 1, lessSignIndex));
                }
                if (largerThanValue > lessThanValue || lessThanValue > 100 || largerThanValue < 0) {
                    return "fail";
                }
                lessThanValue++;
                largerThanValue--;
                results = new ListReconcileResultVO(
                        resultsRepository.findByUserIdAndPercentageBetween(userId, largerThanValue, lessThanValue));
                json = gson.toJson(results.getList());
            }
            // Retrieve by ID
            else if (value.length() == 24) {
                Optional<ReconcileResult> id = resultsRepository.findById(value);
                if (!id.isPresent()) {
                    return "fail";
                }
                List<ReconcileResult> result = new ArrayList<>();
                result.add(id.get());
                json = gson.toJson(result);
            }
            // Month
            else if (value.length() == 7 && dotIndex == 3) {
                if(value.contains("May")){
                    value = value.replace(".","");
                }
                Date date =new SimpleDateFormat("MMM yy").parse(value);
                Date firstDayOfDate = cal.getFristDayOfMonth(cal.getDateYear(date),cal.getDateMonth(date));
                Date lastDayOfDate = cal.getLastDayOfMonth(cal.getDateYear(date),cal.getDateMonth(date));
                firstDayOfDate = cal.calcPrevDayFromDate(firstDayOfDate,-15).getTime();
                lastDayOfDate = cal.calcPrevDayFromDate(lastDayOfDate, 15).getTime();
                results = new ListReconcileResultVO(
                        resultsRepository.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(userId, firstDayOfDate, lastDayOfDate)
                );
                json = gson.toJson(results.getList());
            }else {
                return "fail";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }
        return json;
    }

    private Date stringToDate(String s) throws Exception {
        s = s.substring(0, 10);
        s = s.replaceAll(" ", "");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.parse(s);
    }

    private Date getNextDate(String s) throws Exception {
        s = s.replaceAll(" ", "");
        StringBuffer sb = new StringBuffer(s);
        int day = Integer.parseInt(sb.substring(3, 5)) + 1;
        StringBuffer nextDay = new StringBuffer();
        if (day < 10) {
            nextDay.append("0");
        }
        nextDay.append(String.valueOf(day));
        sb.replace(3, 5, nextDay.toString());
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        date = sdf.parse(sb.toString());
        return date;
    }
}
