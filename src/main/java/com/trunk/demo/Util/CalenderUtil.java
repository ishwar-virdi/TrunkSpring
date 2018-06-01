package com.trunk.demo.Util;

import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class CalenderUtil {
    private Calendar cal;
    public CalenderUtil(){
        cal = Calendar.getInstance();
    }

    public int getYear(){
        return cal.get(Calendar.YEAR);
    }
    public int getMonth(){
        return cal.get(Calendar.MONTH);
    }
    public int getDay(){
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public Date getFristDayOfMonth(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    public Date getLastDayOfMonth(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    public Date getPrevMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    public int differenceDay(Date startDate,Date endDate){
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        long start = startCal.getTimeInMillis();
        long end = endCal.getTimeInMillis();
        return (int) TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
    public Calendar calcPrevDayFromCurr(int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH) + day);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public Calendar calcPrevDayFromDate(Date date,int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH) + day);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }


    public Date setDateToInit(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date setDateToMax(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.getActualMaximum(Calendar.HOUR_OF_DAY);
        int minute = cal.getActualMaximum(Calendar.MINUTE);
        int second = cal.getActualMaximum(Calendar.SECOND);
        int mileSecond = cal.getActualMaximum(Calendar.MILLISECOND);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);
        cal.set(Calendar.MILLISECOND,mileSecond);
        return cal.getTime();
    }

    public int getDateYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public int getDateMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public int getDateDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
