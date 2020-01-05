package com.borrow.manage.vo;

import java.util.Date;

/**
 * Created by wxn on 2018/9/18
 */
public class OrderRepayListReq extends PageBaseReq  {

    private String userName;
    private String mobile;
    private String plateNumber;

    private String pCode;
    private String boRepayStatus;

    private String  brTimeStart;

    private String  brTimeEnd;

    private String brRepayTimeStart;

    private String brRepayTimeEnd;

    private String repayStatus;
    private String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getBrRepayTimeStart() {
        return brRepayTimeStart;
    }

    public void setBrRepayTimeStart(String brRepayTimeStart) {
        this.brRepayTimeStart = brRepayTimeStart;
    }

    public String getBrRepayTimeEnd() {
        return brRepayTimeEnd;
    }

    public void setBrRepayTimeEnd(String brRepayTimeEnd) {
        this.brRepayTimeEnd = brRepayTimeEnd;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

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

    public String getBoRepayStatus() {
        return boRepayStatus;
    }

    public void setBoRepayStatus(String boRepayStatus) {
        this.boRepayStatus = boRepayStatus;
    }

    public String getBrTimeStart() {
        return brTimeStart;
    }

    public void setBrTimeStart(String brTimeStart) {
        this.brTimeStart = brTimeStart;
    }

    public String getBrTimeEnd() {
        return brTimeEnd;
    }

    public void setBrTimeEnd(String brTimeEnd) {
        this.brTimeEnd = brTimeEnd;
    }
}
