package com.borrow.manage.vo;

import java.util.Date;

/**
 * Created by wxn on 2018/9/19
 */
public class CarRepayPlanVo {

    private String repayExpect;
    private Date brTime;
    private String repayAmount;
    private String capitalAmount;
    private String interestAmount;
    private String serviceFee;

    public String getRepayExpect() {
        return repayExpect;
    }

    public void setRepayExpect(String repayExpect) {
        this.repayExpect = repayExpect;
    }

    public Date getBrTime() {
        return brTime;
    }

    public void setBrTime(Date brTime) {
        this.brTime = brTime;
    }

    public String getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(String repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(String capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
}
