package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/20
 */
public class OrderCancelReq extends BaseReq{

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
