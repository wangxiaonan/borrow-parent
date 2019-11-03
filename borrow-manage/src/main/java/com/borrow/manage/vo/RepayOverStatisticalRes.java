package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/18
 */
public class RepayOverStatisticalRes extends BaseReq  {

    //存管账户可用余额
    private String availableAmount;
    //应还总额
    private String repayTotalAmount;
    //本金金额
    private String capitalAmount;
    //利息金额
    private String interestAmount;
    //违约金
    private String punishAmount;
    //平台服务费
    private String serviceFee;
    //罚息
    private String fineAmount;
    //累计违约金金额
    private String reducePunishAmount;
    //累计罚息金额
    private String reduceFineAmount;
    //实际应还
    private String actualRepayTotalAmount;

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getRepayTotalAmount() {
        return repayTotalAmount;
    }

    public void setRepayTotalAmount(String repayTotalAmount) {
        this.repayTotalAmount = repayTotalAmount;
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

    public String getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(String punishAmount) {
        this.punishAmount = punishAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getReducePunishAmount() {
        return reducePunishAmount;
    }

    public void setReducePunishAmount(String reducePunishAmount) {
        this.reducePunishAmount = reducePunishAmount;
    }

    public String getReduceFineAmount() {
        return reduceFineAmount;
    }

    public void setReduceFineAmount(String reduceFineAmount) {
        this.reduceFineAmount = reduceFineAmount;
    }

    public String getActualRepayTotalAmount() {
        return actualRepayTotalAmount;
    }

    public void setActualRepayTotalAmount(String actualRepayTotalAmount) {
        this.actualRepayTotalAmount = actualRepayTotalAmount;
    }
}
