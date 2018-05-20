package com.trunk.demo.Util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
    private FormatUtil formatUtil;
    public DateUtil(){formatUtil = new FormatUtil();}

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

    public Date getNextMonthDate(String str){
        Date date = null;
        try {
            str = str.replaceAll(" ","");
            StringBuffer sb = new StringBuffer(str);
            int month = Integer.parseInt(sb.substring(4,6))+1;
            StringBuffer nextDay = new StringBuffer();
            if(month < 10){
                nextDay.append("0");
            }
            nextDay.append(String.valueOf(month));
            sb.replace(4,6,nextDay.toString()).replace(6,8,"01");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date = sdf.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getCurrMonthDate(String str){
        Date date = null;

        try {
            str = str.replaceAll(" ","");
            StringBuffer sb = new StringBuffer(str);
            sb.replace(6,8,"01");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date = sdf.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDetailID(String str){
        str = str.replaceAll(" ","");
        int nextMonth = Integer.parseInt(str.substring(4,6))+1;
        int nextYear = Integer.parseInt(str.substring(0,4));
        if(nextMonth > 12){
            nextYear+=1;
            nextMonth = 1;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(str, 0, 6);
        sb.append("01");
        sb.append(nextYear);
        if(nextMonth < 10){
            sb.append("0");
        }
        sb.append(nextMonth);
        sb.append("01");
        return sb.toString();
    }

    public String getCurrDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        return sdf.format(new Date());
    }

    public Date[] getReconcileDate(String year,String month) throws ParseException {
        Date[] dates = new Date[2];
        if(!formatUtil.isNum(year) || !formatUtil.isNum(month)){
            return null;
        }
        Date currMonthDate = null;
        Date nextMonthDate = null;
        StringBuffer currMonthSb = new StringBuffer();
        StringBuffer nextMonthSb = new StringBuffer();
        currMonthSb.append(year);
        nextMonthSb.append(year);
        int nextMonth = Integer.parseInt(month) + 1;
        //month
        if(nextMonth < 10){
            currMonthSb.append("0");
            nextMonthSb.append("0");
        }
        currMonthSb.append(month);
        nextMonthSb.append(nextMonth);
        //day
        currMonthSb.append("01");
        nextMonthSb.append("01");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        currMonthDate = sdf.parse(currMonthSb.toString());
        nextMonthDate = sdf.parse(nextMonthSb.toString());
        dates[0] = currMonthDate;
        dates[1] = nextMonthDate;
        return dates;
    }
}
