package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/12
 */
public class UserInfoVo {


    private String userName;
    private String idcard;
    private String mobile;

    private String creditDec;
    private String industry;
    private String workNature;
    private String userEarns;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreditDec() {
        return creditDec;
    }

    public void setCreditDec(String creditDec) {
        this.creditDec = creditDec;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public String getUserEarns() {
        return userEarns;
    }

    public void setUserEarns(String userEarns) {
        this.userEarns = userEarns;
    }
}
