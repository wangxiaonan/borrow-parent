package com.borrow.manage.vo;

import com.borrow.manage.model.dto.BorrowRepayment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2018/9/21
 */
public class OrderUpRepayCalRes {

    private String payTotalAmount;

    private String userName;

    private String expectTotal;

    private String payExpect;

    private String finishExpect;

    List<BorrowRepayment> calculateOver= new ArrayList<>();

    public List<BorrowRepayment> getCalculateOver() {
        return calculateOver;
    }

    public void setCalculateOver(List<BorrowRepayment> calculateOver) {
        this.calculateOver = calculateOver;
    }

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
