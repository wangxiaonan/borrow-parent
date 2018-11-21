package com.borrow.manage.vo;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by wxn on 2018/9/17
 */
public class OrderPayListRes {

    private String orderId;
    private String repayId;
    private String userName;
    private String plateNumber;
    private String boPrice;
    private String productName;
    private String boExpect;
    private String boPayExpect;

    private Date firstPayTime;

    private Date lastPayTime;

    private Date firstExpectTime;

    private String boPayState;

    private String boIsState;

    public String getLastPayTime() {
        return lastPayTime ==null? "":DateFormatUtils.format(lastPayTime,"yyyy-MM-dd HH:mm:ss");

    }

    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public String getFirstExpectTime() {
        return firstExpectTime ==null ? "":DateFormatUtils.format(firstExpectTime,"yyyy-MM-dd");
    }

    public void setFirstExpectTime(Date firstExpectTime) {
        this.firstExpectTime = firstExpectTime;
    }

    public String getBoIsState() {
        return boIsState;
    }

    public void setBoIsState(String boIsState) {
        this.boIsState = boIsState;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getBoPayExpect() {
        return boPayExpect;
    }

    public void setBoPayExpect(String boPayExpect) {
        this.boPayExpect = boPayExpect;
    }

    public String getFirstPayTime() {
        return firstPayTime ==null? "":DateFormatUtils.format(firstPayTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setFirstPayTime(Date firstPayTime) {
        this.firstPayTime = firstPayTime;
    }

    public String getBoPayState() {
        return boPayState;
    }

    public void setBoPayState(String boPayState) {
        this.boPayState = boPayState;
    }
}
