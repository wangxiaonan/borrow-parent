package com.borrow.manage.vo;

import java.util.Date;

/**
 * Created by wxn on 2018/9/18
 */
public class RepayReduceListRes {

    private String orderId;
    private String repayId;

    private String userName;
    private String userMobile;

    private String boPrice;
    private String productName;
    private String boExpect;
    //所属期数
    private String repayExpect;
    //应还日期

    private Date brTime;

    private String reducePunishAmount;
    private String reduceFineAmount;

    private Date reduceCreateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBoPrice() {
        return boPrice;
    }

    public void setBoPrice(String boPrice) {
        this.boPrice = boPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBoExpect() {
        return boExpect;
    }

    public void setBoExpect(String boExpect) {
        this.boExpect = boExpect;
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

    public Date getReduceCreateTime() {
        return reduceCreateTime;
    }

    public void setReduceCreateTime(Date reduceCreateTime) {
        this.reduceCreateTime = reduceCreateTime;
    }
}
