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
    private String productName;
    private String productCode;
    //借款金额
    private String boPrice;
    //借款用途
    private String boPaySource;
    private String boSource;

    private UserInfoVo userInfo;

    private UserCarItemVo userCarItemVo;

    private BorrowSalesmanVo borrowSalesman;

    private List<String> auditkeys = new ArrayList<>();

    private UserHouseInfoVo userHouseInfo;

    public String getBoSource() {
        return boSource;
    }

    public void setBoSource(String boSource) {
        this.boSource = boSource;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBoPrice() {
        return boPrice;
    }

    public void setBoPrice(String boPrice) {
        this.boPrice = boPrice;
    }

    public String getBoPaySource() {
        return boPaySource;
    }

    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource;
    }

    public BorrowSalesmanVo getBorrowSalesman() {
        return borrowSalesman;
    }

    public void setBorrowSalesman(BorrowSalesmanVo borrowSalesman) {
        this.borrowSalesman = borrowSalesman;
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
