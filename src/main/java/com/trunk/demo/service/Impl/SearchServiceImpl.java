package com.trunk.demo.service.Impl;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.UsersRepository;
import com.trunk.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ResultsRepository resultsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Gson gson;
    @Override
    public String search(String page, String value) {
        String json = "";
        switch (page){
            case "result":
                json = resultSearch(value);
                break;
            case "detail":
                System.out.println(value);
                break;
        }

        if("fail".equals(json)){
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("result", "fail");
            json = innerObject.toString();
        }
        return json;
    }

    private String resultSearch(String value){
        String json = "";
        List<ReconcileResult> resutls;
        int lessSignIndex = value.indexOf("<");
        int largeSignIndex = value.indexOf(">");
        try{
            //date
            if(value.length()== 8
                    && largeSignIndex == -1
                    && lessSignIndex == -1){
                int date = Integer.parseInt(value);
                resutls = resultsRepository.findByUidAndReconcileDate(getUid(),date);
                json = gson.toJson(resutls);
            }
            //dateRange
            else if(value.length()== 16){
                int startDate = Integer.parseInt(value.substring(0,8));
                int endDate = Integer.parseInt(value.substring(8,16));
                if(endDate < startDate){
                    return "fail";
                }
                resutls = resultsRepository.findByUidAndStartDateGreaterThanEqualAndEndDateLessThanEqual(getUid(),startDate,endDate);
                json = gson.toJson(resutls);
                System.out.println(json);
            }
            //percentage
            else if(value.length()<= 3
                    && largeSignIndex == -1
                    && lessSignIndex == -1
                    ){
                int percentage = Integer.parseInt(value);
                if(percentage > 100 || percentage < 0){
                    return "fail";
                }
                resutls = resultsRepository.findByUidAndPercentage(getUid(),percentage);
                json = gson.toJson(resutls);
            }else if(value.length()<= 4
                    && largeSignIndex != -1
                    && lessSignIndex == -1){
                int percentage = Integer.parseInt(value.replace(">",""));
                if(percentage > 100 || percentage < 0){
                    return "fail";
                }
                resutls = resultsRepository.findByUidAndPercentageGreaterThanEqual(getUid(),percentage);
                json = gson.toJson(resutls);
            }else if(value.length()<= 4
                    && largeSignIndex == -1
                    && lessSignIndex != -1){
                int percentage = Integer.parseInt(value.replace("<",""));
                if(percentage > 100 || percentage < 0){
                    return "fail";
                }
                resutls = resultsRepository.findByUidAndPercentageLessThanEqual(getUid(),percentage);
                json = gson.toJson(resutls);
            }else if(value.length() <= 8
                    && largeSignIndex != -1
                    && lessSignIndex != -1){
                int lessThanValue = 0;
                int largerThanValue = 0;

                if(largeSignIndex > lessSignIndex){
                    lessThanValue = Integer.parseInt(value.substring(lessSignIndex+1,largeSignIndex));
                    largerThanValue = Integer.parseInt(value.substring(largeSignIndex+1));
                }else{
                    lessThanValue = Integer.parseInt(value.substring(lessSignIndex+1));
                    largerThanValue = Integer.parseInt(value.substring(largeSignIndex+1,lessSignIndex));
                }
                System.out.println(lessThanValue);
                System.out.println(largerThanValue);
                if(largerThanValue > lessThanValue|| lessThanValue > 100 || largerThanValue < 0){
                    return "fail";
                }
                lessThanValue++;
                largerThanValue--;
                resutls = resultsRepository.findByUidAndPercentageBetween(getUid(),largerThanValue,lessThanValue);
                json = gson.toJson(resutls);
            }else if(value.length() == 24){
                Optional<ReconcileResult> id = resultsRepository.findById(value);
                if(!id.isPresent()){
                    return "fail";
                }
                List<ReconcileResult> results = new ArrayList<>();
                results.add(id.get());
                json = gson.toJson(results);
            }else{
                return "fail";
            }
        }catch(NumberFormatException e){
            return "fail";
        }
        return json;
    }

    private String getUid(){
        List<User> user = usersRepository.findByUsername("test@test.com");
        return user.get(0).getId();
    }
}
