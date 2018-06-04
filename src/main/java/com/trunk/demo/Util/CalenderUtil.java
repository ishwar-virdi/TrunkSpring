package com.trunk.demo.Util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class CalenderUtil {
	private Calendar cal;



	public CalenderUtil() {
		cal = Calendar.getInstance();
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	public int getMonth() {
		return cal.get(Calendar.MONTH);
	}

	public int getDay() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public Date getFristDayOfMonth(int year, int month) {
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day, 0, 0, 0);
		return cal.getTime();
	}

	public Date getLastDayOfMonth(int year, int month) {
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day, 0, 0, 0);
		return cal.getTime();
	}

	public Date getPrevMonth() {
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public int differenceDay(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		long start = startCal.getTimeInMillis();
		long end = endCal.getTimeInMillis();
		return (int) TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}

	public Calendar calcPrevDayFromCurr(int day) {
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public Calendar calcPrevDayFromDate(Date date, int day) {
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public Date setDateToInit(Date date) {
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Date setDateToMax(Date date) {
		cal.setTime(date);
		int hour = cal.getActualMaximum(Calendar.HOUR_OF_DAY);
		int minute = cal.getActualMaximum(Calendar.MINUTE);
		int second = cal.getActualMaximum(Calendar.SECOND);
		int mileSecond = cal.getActualMaximum(Calendar.MILLISECOND);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, mileSecond);
		return cal.getTime();
	}

	public int getDateYear(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public int getDateMonth(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	public int getDateDay(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public Date firstDayOfThisMonth(Date date) {
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Date firstDayOfMonthByString(String value,String format){
		Date date = null;
		if(value.contains("May")){
			value = value.replace(".","");
		}
		try {
			date = new SimpleDateFormat(format).parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public Date EndDayOfMonthByString(String value,String format){
		Date date = null;
		if(value.contains("May")){
			value = value.replace(".","");
		}
		try {
			date = new SimpleDateFormat(format).parse(value);
			cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH,month);
			date = this.setDateToMax(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public int isMonth(String value){
		final String[] months = {"January","February","March","April",
				"May","June","July","August", "September","October","November","December",
				"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
		int month = 0;
		for(int i = 0,length = months.length;i<length;i++){
			if(value.equals(months[i])){
				month = i;
				if(month > 12){
					month = month - 12;
				}
				return month;
			}
		}
		return -1;
	}
}
