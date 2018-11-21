package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/17
 */
public class OrderPayListReq extends PageBaseReq {

    private String userName;
    private String mobile;
    private String plateNumber;

    private String pCode;
    private String boPayState;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getBoPayState() {
        return boPayState;
    }

    public void setBoPayState(String boPayState) {
        this.boPayState = boPayState;
    }
}
