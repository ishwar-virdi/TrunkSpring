package com.trunk.demo;

import com.trunk.demo.Util.BCryptText;

import com.trunk.demo.Util.CalenderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;


public class UtilTests {

    private BCryptText bCryptText;
    private CalenderUtil calenderUtil;
    private String password = "!PasswOrd@";
    private String cipherText = "";

    Calendar calendar;

    private Date time;
    private int year = 2013;
    private int month = 1;
    private int date = 2;
    private int hourOfDay = 17;
    private  int minute = 35;
    private  int second = 44;
    public UtilTests(){
        bCryptText = new BCryptText();
        calenderUtil = new CalenderUtil();

        calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.date, this.hourOfDay, this.minute, this.second);
        this.time = calendar.getTime();
    }


    public void encrypt(){
        this.cipherText = bCryptText.getCipherText(this.password);
        if(this.cipherText.equals(this.password)){
            System.out.println("------------encrypt not Pass-----------------");
        }
    }
    public void isEquals(){
        boolean flag = bCryptText.isEquals(this.password,this.cipherText);
        if(!flag){
            System.out.println("----------encrypt equals not Pass------------");
        }
    }

    public void getDateYear(){
        int year = calenderUtil.getDateYear(time);
        if(year != 2013){
            System.out.println("-----------getDateYear not Pass--------------");
        }
    }
    public void getDateMonth(){
        int month = calenderUtil.getDateMonth(time);
        if(month != 1){
            System.out.println("-----------getDateMonth not Pass-------------");
        }
    }
    public void getDateDay(){
        int day = calenderUtil.getDateDay(time);
        if(day != 2){
            System.out.println("-----------getDateDay not Pass--------------");
        }
    }

    public void isMonth(){
        int monthOne = calenderUtil.isMonth("january");
        if(monthOne != 0){
            System.out.println("----------- isMonth not Pass ----------------");
        }
        int monthTwo = calenderUtil.isMonth("jan");
        if(monthTwo != 0){
            System.out.println("----------- isMonth not Pass ----------------");
        }
        int monthThree = calenderUtil.isMonth("March");
        if(monthThree != 2){
            System.out.println("----------- isMonth not Pass ----------------");
        }
        int monthFour = calenderUtil.isMonth("MAR");
        if(monthFour != 2){
            System.out.println("----------- isMonth not Pass ----------------");
        }
    }

    public void EndDayOfMonthByString(){
        String dateOne = "Mar. 2018";
        String dateTwo = "May. 2018";
        String format = "MMM yyyy";

        Date done = calenderUtil.EndDayOfMonthByString(dateOne,format);
        Date dtwo = calenderUtil.EndDayOfMonthByString(dateTwo,format);

        if(calenderUtil.getDateYear(done) != 2018 ||
           calenderUtil.getDateMonth(done) != 2 ||
                calenderUtil.getDateDay(done) != 31
                ){
            System.out.println("--------EndDayOfMonthByString not Pass-------");
        }
        if(calenderUtil.getDateYear(dtwo) != 2018 ||
                calenderUtil.getDateMonth(dtwo) != 4 ||
                calenderUtil.getDateDay(dtwo) != 31
                ){
            System.out.println("-------EndDayOfMonthByString not Pass--------");
        }
    }


}
