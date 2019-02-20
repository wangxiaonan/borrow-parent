package com.borrow.manage.vo;

import java.util.Date;

/**
 * Created by wxn on 2018/9/21
 */
public class RepayDetailVo {

    private String repayId;
    private String repayExpect;
    //日期
    private Date brTime;
    //月供
    private String repayAmount;
    //本金
    private String capitalAmount;
    //利息
    private String interestAmount;
    //服务费
    private String serviceFee;
    //违约金
    private String punishAmount;
    //还款状态
    private String repayStatus;
    //还款时间
    private Date brRepayTime;
    //还款金额
    private String repayFinishAmount;
    //还款类型
    private String repayType;
    //罚息
    private String fineAmount;

    public String getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

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

    public String getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(String punishAmount) {
        this.punishAmount = punishAmount;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Date getBrRepayTime() {
        return brRepayTime;
    }

    public void setBrRepayTime(Date brRepayTime) {
        this.brRepayTime = brRepayTime;
    }

    public String getRepayFinishAmount() {
        return repayFinishAmount;
    }

    public void setRepayFinishAmount(String repayFinishAmount) {
        this.repayFinishAmount = repayFinishAmount;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }
}
