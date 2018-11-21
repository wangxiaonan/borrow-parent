package com.borrow.manage.vo;

import java.util.Date;

/**
 * Created by wxn on 2018/9/27
 */
public class OrderPayRecordVo {
    private String orderId;
    private String orderPayId;
    private Date payTime;
    private String payPrice;
    private String payType;
    private String payTypeDesc;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderPayId() {
        return orderPayId;
    }

    public void setOrderPayId(String orderPayId) {
        this.orderPayId = orderPayId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }
}
