package com.borrow.manage.vo;

import java.math.BigDecimal;

/**
 * Created by wxn on 2018/9/21
 */
public class OrderUpRepayReq {

    private String orderId;
//    private String repayId;

    private BigDecimal payAmount;
    private String upPayType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getUpPayType() {
        return upPayType;
    }

    public void setUpPayType(String upPayType) {
        this.upPayType = upPayType;
    }
}
