package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/15
 */
public class OrderListReq extends PageBaseReq {

    private String userName;
    private String mobile;
    private String plateNumber;

    private String pCode;

    private String boIsState;

    private String createTimeStart;
    private String createTimeEnd;

    private String boFinishTimeStart;
    private String boFinishTimeEnd;

    private String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getBoFinishTimeStart() {
        return boFinishTimeStart;
    }

    public void setBoFinishTimeStart(String boFinishTimeStart) {
        this.boFinishTimeStart = boFinishTimeStart;
    }

    public String getBoFinishTimeEnd() {
        return boFinishTimeEnd;
    }

    public void setBoFinishTimeEnd(String boFinishTimeEnd) {
        this.boFinishTimeEnd = boFinishTimeEnd;
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

    public String getBoIsState() {
        return boIsState;
    }

    public void setBoIsState(String boIsState) {
        this.boIsState = boIsState;
    }
}
