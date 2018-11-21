package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/21
 */
public class OrderPayOverReq {

    private String orderId;

    private String repayId;

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
}
