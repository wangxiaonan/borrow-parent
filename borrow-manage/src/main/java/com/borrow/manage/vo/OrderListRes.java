package com.borrow.manage.vo;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wxn on 2018/9/15
 */
public class OrderListRes implements Serializable{

    private String orderId;
    private Date createTime;
    private String userName;
    private String idcard;
    private String mobile;
    private String plateNumber;
    private String salesName;
    private String salesMobile;
    private String boPrice;
    private String productName;
    private String boExpect;
    private String boIsState;
    private Date boFinishTime;
    private String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime==null? "":DateFormatUtils.format(createTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getSalesMobile() {
        return salesMobile;
    }

    public void setSalesMobile(String salesMobile) {
        this.salesMobile = salesMobile;
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

    public String getBoIsState() {
        return boIsState;
    }

    public void setBoIsState(String boIsState) {
        this.boIsState = boIsState;
    }

    public String getBoFinishTime() {
        return boFinishTime ==null?"":DateFormatUtils.format(boFinishTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setBoFinishTime(Date boFinishTime) {
        this.boFinishTime = boFinishTime;
    }
}
