package com.poshist.BI.vo;

import java.util.Date;

public class QueryVO {
    private Date minTime;
    private Date maxTime;

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }
}
