package com.trunk.demo.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashDailyTransaction implements Comparable<DashDailyTransaction> {
	private String date;
	private double settleVisa;
	private double settleDebit;
	private double settleAmex;
	private double bankVisa;
	private double bankDebit;
	private double bankAmex;

	public DashDailyTransaction(String date, double settleVisa, double settleDebit, double settleAmex, double bankVisa,
			double bankDebit, double bankAmex) {
		this.date = date;
		this.settleVisa = settleVisa;
		this.settleDebit = settleDebit;
		this.settleAmex = settleAmex;
		this.bankVisa = bankVisa;
		this.bankDebit = bankDebit;
		this.bankAmex = bankAmex;
	}

	/*
	 * public DashDailyTransaction(Calendar cal, double settleVisa, double
	 * settleDebit, double settleAmex, double bankVisa, double bankDebit, double
	 * bankAmex) { StringBuffer sb = new StringBuffer();
	 * 
	 * sb.append(cal.get(Calendar.MONTH)).append("/").append(cal.get(Calendar.
	 * DAY_OF_MONTH)).append("/") .append(cal.get(Calendar.YEAR)); this.date =
	 * sb.toString(); this.settleVisa = settleVisa; this.settleDebit = settleDebit;
	 * this.settleAmex = settleAmex; this.bankVisa = bankVisa; this.bankDebit =
	 * bankDebit; this.bankAmex = bankAmex; }
	 */

	public String getDate() {
		return date;
	}

	public double getSettleVisa() {
		return settleVisa;
	}

	public double getSettleDebit() {
		return settleDebit;
	}

	public double getSettleAmex() {
		return settleAmex;
	}

	public double getBankVisa() {
		return bankVisa;
	}

	public double getBankDebit() {
		return bankDebit;
	}

	public double getBankAmex() {
		return bankAmex;
	}

	@Override
	public int compareTo(DashDailyTransaction o) {
		try {
			Date currentDate = new SimpleDateFormat("DD-MMM-YYYY", Locale.ENGLISH).parse(this.date);
			Date otherDate = new SimpleDateFormat("DD-MMM-YYYY", Locale.ENGLISH).parse(o.date);
			if (currentDate.after(otherDate))
				return 1;
			else if (currentDate.before(otherDate))
				return -1;
			else
				return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
