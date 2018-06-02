package com.trunk.demo.vo;

import java.util.Calendar;
import java.util.Date;

public class DashDailyTransaction {
    private String date;
    private double settleVisa;
    private double settleDebit;
    private double settleAmex;
    private double bankVisa;
    private double bankDebit;
    private double bankAmex;

    public DashDailyTransaction(String date, double settleVisa, double settleDebit, double settleAmex, double bankVisa, double bankDebit, double bankAmex) {
        this.date = date;
        this.settleVisa = settleVisa;
        this.settleDebit = settleDebit;
        this.settleAmex = settleAmex;
        this.bankVisa = bankVisa;
        this.bankDebit = bankDebit;
        this.bankAmex = bankAmex;
    }

    public DashDailyTransaction(Calendar cal,double settleVisa, double settleDebit, double settleAmex, double bankVisa, double bankDebit, double bankAmex) {
        StringBuffer sb = new StringBuffer();

        sb.append(cal.get(Calendar.MONTH)+1)
                .append("/")
                .append(cal.get(Calendar.DAY_OF_MONTH))
                .append("/")
                .append(cal.get(Calendar.YEAR));
        this.date = sb.toString();
        this.settleVisa = settleVisa;
        this.settleDebit = settleDebit;
        this.settleAmex = settleAmex;
        this.bankVisa = bankVisa;
        this.bankDebit = bankDebit;
        this.bankAmex = bankAmex;
    }

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
}
