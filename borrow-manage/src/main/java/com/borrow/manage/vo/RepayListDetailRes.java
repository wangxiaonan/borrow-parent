package com.borrow.manage.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2018/9/21
 */
public class RepayListDetailRes {

    private String orderId;
    private String userName;
    private String plateNumber;

    private String boPrice;
    private String productName;
    private String boExpect;

    private List<RepayDetailVo> repayDetails = new ArrayList<>();

    private List<OrderPayRecordVo> orderPayRecords = new ArrayList<>();

    public List<OrderPayRecordVo> getOrderPayRecords() {
        return orderPayRecords;
    }

    public void setOrderPayRecords(List<OrderPayRecordVo> orderPayRecords) {
        this.orderPayRecords = orderPayRecords;
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

    public List<RepayDetailVo> getRepayDetails() {
        return repayDetails;
    }

    public void setRepayDetails(List<RepayDetailVo> repayDetails) {
        this.repayDetails = repayDetails;
    }
}
