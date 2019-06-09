package com.borrow.manage.vo;

import com.borrow.manage.model.dto.BoOrderItem;
import com.borrow.manage.utils.Utility;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wxn on 2018/9/16
 */
public class OrderDetailRes {

    private int bussType;

    private UserInfoVo userInfo;

    private UserCarItemVo userCarItemVo;

    private BorrowSalesmanVo borrowSalesman;

    private BorrowOrderVo borrowOrder;

    private List<String> auditkeys = new ArrayList<>();

    private UserHouseInfoVo userHouseInfo;

    public int getBussType() {
        return bussType;
    }

    public void setBussType(int bussType) {
        this.bussType = bussType;
    }

    public UserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVo userInfo) {
        this.userInfo = userInfo;
    }


    public BorrowSalesmanVo getBorrowSalesman() {
        return borrowSalesman;
    }

    public void setBorrowSalesman(BorrowSalesmanVo borrowSalesman) {
        this.borrowSalesman = borrowSalesman;
    }

    public BorrowOrderVo getBorrowOrder() {
        return borrowOrder;
    }

    public void setBorrowOrder(BorrowOrderVo borrowOrder) {
        this.borrowOrder = borrowOrder;
    }

    public UserCarItemVo getUserCarItemVo() {
        return userCarItemVo;
    }

    public void setUserCarItemVo(UserCarItemVo userCarItemVo) {
        this.userCarItemVo = userCarItemVo;
    }

    public List<String> getAuditkeys() {
        return auditkeys;
    }

    public void setAuditkeys(List<String> auditkeys) {
        this.auditkeys = auditkeys;
    }

    public UserHouseInfoVo getUserHouseInfo() {
        return userHouseInfo;
    }

    public void setUserHouseInfo(UserHouseInfoVo userHouseInfo) {
        this.userHouseInfo = userHouseInfo;
    }
}
