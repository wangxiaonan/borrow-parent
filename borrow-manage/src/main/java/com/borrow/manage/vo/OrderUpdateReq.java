package com.borrow.manage.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2018/9/12
 */
public class OrderUpdateReq extends BaseReq{

    private int bussType;
    //借款用途
    private String boPaySource;
    private String boSource;

    private UserInfoVo userInfo;

    private UserCarItemVo userCarItemVo;

    private BorrowSalesmanVo borrowSalesman;

    private List<String> auditkeys = new ArrayList<>();

    private UserHouseInfoVo userHouseInfo;

    public int getBussType() {
        return bussType;
    }

    public void setBussType(int bussType) {
        this.bussType = bussType;
    }



    public String getBoPaySource() {
        return boPaySource;
    }

    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource;
    }

    public String getBoSource() {
        return boSource;
    }

    public void setBoSource(String boSource) {
        this.boSource = boSource;
    }

    public UserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public UserCarItemVo getUserCarItemVo() {
        return userCarItemVo;
    }

    public void setUserCarItemVo(UserCarItemVo userCarItemVo) {
        this.userCarItemVo = userCarItemVo;
    }

    public BorrowSalesmanVo getBorrowSalesman() {
        return borrowSalesman;
    }

    public void setBorrowSalesman(BorrowSalesmanVo borrowSalesman) {
        this.borrowSalesman = borrowSalesman;
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
