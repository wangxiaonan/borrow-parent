package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/21
 */
public class OrderUpRepayCalRes {

    private String payTotalAmount;
    private String payTotalCapitalAmount;
    private String payTotalInterestAmount;

    private String payTotalServiceFee;

    private String earlyPayCostAmount;

    public String getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(String payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public String getPayTotalCapitalAmount() {
        return payTotalCapitalAmount;
    }

    public void setPayTotalCapitalAmount(String payTotalCapitalAmount) {
        this.payTotalCapitalAmount = payTotalCapitalAmount;
    }

    public String getPayTotalInterestAmount() {
        return payTotalInterestAmount;
    }

    public void setPayTotalInterestAmount(String payTotalInterestAmount) {
        this.payTotalInterestAmount = payTotalInterestAmount;
    }

    public String getPayTotalServiceFee() {
        return payTotalServiceFee;
    }

    public void setPayTotalServiceFee(String payTotalServiceFee) {
        this.payTotalServiceFee = payTotalServiceFee;
    }

    public String getEarlyPayCostAmount() {
        return earlyPayCostAmount;
    }

    public void setEarlyPayCostAmount(String earlyPayCostAmount) {
        this.earlyPayCostAmount = earlyPayCostAmount;
    }
}
