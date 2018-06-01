package com.trunk.demo.vo;

import com.amazonaws.util.DateUtils;
import com.trunk.demo.Util.CalenderUtil;

import java.util.Date;

public class DashMonthTotalVO {
    private String date;
    private double settleTotal;
    private double bankTotal;

    public DashMonthTotalVO(String date, double settleTotal, double bankTotal) {
        this.date = date;
        this.settleTotal = settleTotal;
        this.bankTotal = bankTotal;
    }

    public DashMonthTotalVO(int endYear, int endMonth, double settleTotal, double bankTotal) {
        StringBuffer sb = new StringBuffer();
        sb.append(endMonth+1).append("/").append(endYear);
        this.date = sb.toString();
        this.settleTotal = settleTotal;
        this.bankTotal = bankTotal;
    }

    public String getDate() {
        return date;
    }
    public double getSettleTotal() {
        return settleTotal;
    }
    public double getBankTotal() {
        return bankTotal;
    }
}
