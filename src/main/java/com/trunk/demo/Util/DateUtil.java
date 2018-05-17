package com.trunk.demo.Util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
    public DateUtil(){}

    public Date convSettleToDate(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String convSettleToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public String convCurrentToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public Date convStringToCurrent(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date convBankStateToDate(String s){
        StringBuffer sb = new StringBuffer();
        if(s.length() == 7){
            sb.append("0");
            sb.append(s);
        }else{
            sb.append(s);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Date date = null;
        try {
            date = sdf.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date stringToDate(String s) throws Exception {
        s = s.substring(0,10);
        s = s.replaceAll(" ","");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.parse(s);
    }

    public Date getNextDate(String s) throws Exception {
        s = s.replaceAll(" ","");
        StringBuffer sb = new StringBuffer(s);
        int day = Integer.parseInt(sb.substring(3,5))+1;
        StringBuffer nextDay = new StringBuffer();
        if(day < 10){
            nextDay.append("0");
        }
        nextDay.append(String.valueOf(day));
        sb.replace(3,5,nextDay.toString());
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        date = sdf.parse(sb.toString());
        return date;
    }
}
