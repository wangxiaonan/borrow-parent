package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/21
 */
public class OrderUpRepayCalRes {

    private String payTotalAmount;

    private String userName;

    private String expectTotal;

    private String payExpect;

    private String finishExpect;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpectTotal() {
        return expectTotal;
    }

    public void setExpectTotal(String expectTotal) {
        this.expectTotal = expectTotal;
    }

    public String getPayExpect() {
        return payExpect;
    }

    public void setPayExpect(String payExpect) {
        this.payExpect = payExpect;
    }

    public String getFinishExpect() {
        return finishExpect;
    }

    public void setFinishExpect(String finishExpect) {
        this.finishExpect = finishExpect;
    }

    public String getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(String payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

}
