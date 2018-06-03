package com.trunk.demo.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashMonthTotalVO implements Comparable<DashMonthTotalVO> {
	private String date;
	private double settleTotal;
	private double bankTotal;

	public DashMonthTotalVO(String date, double settleTotal, double bankTotal) {
		this.date = date;
		this.settleTotal = settleTotal;
		this.bankTotal = bankTotal;
	}
	/*
	 * public DashMonthTotalVO(int endYear, int endMonth, double settleTotal, double
	 * bankTotal) { StringBuffer sb = new StringBuffer();
	 * sb.append(endMonth+1).append("/").append(endYear); this.date = sb.toString();
	 * this.settleTotal = settleTotal; this.bankTotal = bankTotal; }
	 */

	public String getDate() {
		return date;
	}

	public double getSettleTotal() {
		return settleTotal;
	}

	public double getBankTotal() {
		return bankTotal;
	}

	@Override
	public int compareTo(DashMonthTotalVO arg0) {

		try {
			Date currentDate = new SimpleDateFormat("MMM-YYYY", Locale.ENGLISH).parse(this.date);
			Date otherDate = new SimpleDateFormat("MMM-YYYY", Locale.ENGLISH).parse(arg0.date);
			if (currentDate.after(otherDate))
				return -1;
			else if (currentDate.before(otherDate))
				return 1;
			else
				return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
