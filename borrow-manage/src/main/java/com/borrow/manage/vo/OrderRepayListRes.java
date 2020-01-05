package com.borrow.manage.vo;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wxn on 2018/9/18
 */
public class OrderRepayListRes  {

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
    private long brTimeDate;

    private String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public long getBrTimeDate() {
        return brTime.getTime();
    }

    public void setBrTimeDate(long brTimeDate) {
        this.brTimeDate = brTimeDate;
    }

    private String repayAmount;
    //支付状态
    private String repayStatus;

    private String boRepayStatus;

    private String  boIsState;

    private Date brRepayTime;
    private String suretyStatus;
    private Date suretyTime;

    public String getSuretyStatus() {
        return suretyStatus;
    }

    public void setSuretyStatus(String suretyStatus) {
        this.suretyStatus = suretyStatus;
    }

    public String getSuretyTime() {
        return suretyTime == null ? "":DateFormatUtils.format(suretyTime,"yyyy-MM-dd HH:ss:mm");
    }

    public void setSuretyTime(Date suretyTime) {
        this.suretyTime = suretyTime;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBrRepayTime() {
        return brRepayTime ==null ? "":DateFormatUtils.format(brRepayTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setBrRepayTime(Date brRepayTime) {
        this.brRepayTime = brRepayTime;
    }

    public String getBoIsState() {
        return boIsState;
    }

    public void setBoIsState(String boIsState) {
        this.boIsState = boIsState;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBrTime() {
        return brTime == null ? "":DateFormatUtils.format(brTime,"yyyy-MM-dd");
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

    public String getBoRepayStatus() {
        return boRepayStatus;
    }

    public void setBoRepayStatus(String boRepayStatus) {
        this.boRepayStatus = boRepayStatus;
    }
}
