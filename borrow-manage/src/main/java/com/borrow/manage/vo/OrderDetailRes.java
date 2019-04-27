package com.borrow.manage.vo;

import com.borrow.manage.utils.Utility;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wxn on 2018/9/16
 */
public class OrderDetailRes {

    private String workNature;
    private String userEarns;
    private String childrenDesc;

    private String liabilitiesDesc;

    private String guaranteeDesc;
    private Map<String, String> auditkeys;

    private String carModel;

    private String carColor;
    private Date signTime;
    private String signTimeDesc;

    private BigDecimal assessmentPrice;
    private String mileageDesc;
    private String plateNumber;
    private String boPaySource;

    public String getSignTimeDesc() {
        return Utility.dateStrddHHmmss(signTime);
    }

    public void setSignTimeDesc(String signTimeDesc) {
        this.signTimeDesc = signTimeDesc;
    }

    public String getBoPaySource() {
        return boPaySource;
    }

    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public BigDecimal getAssessmentPrice() {
        return assessmentPrice;
    }

    public void setAssessmentPrice(BigDecimal assessmentPrice) {
        this.assessmentPrice = assessmentPrice;
    }

    public String getMileageDesc() {
        return mileageDesc;
    }

    public void setMileageDesc(String mileageDesc) {
        this.mileageDesc = mileageDesc;
    }

    public Map<String, String> getAuditkeys() {
        return auditkeys;
    }

    public void setAuditkeys(Map<String, String> auditkeys) {
        this.auditkeys = auditkeys;
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

    public String getChildrenDesc() {
        return childrenDesc;
    }

    public void setChildrenDesc(String childrenDesc) {
        this.childrenDesc = childrenDesc;
    }

    public String getLiabilitiesDesc() {
        return liabilitiesDesc;
    }

    public void setLiabilitiesDesc(String liabilitiesDesc) {
        this.liabilitiesDesc = liabilitiesDesc;
    }

    public String getGuaranteeDesc() {
        return guaranteeDesc;
    }

    public void setGuaranteeDesc(String guaranteeDesc) {
        this.guaranteeDesc = guaranteeDesc;
    }


    //    private String industry;
//    private String workNature;
//    private String userEarns;
//    private String boPaySource;
//    private String creditDec;
//
//    private Map<String,String> auditkeys;
//
//    public String getCreditDec() {
//        return creditDec;
//    }
//
//    public void setCreditDec(String creditDec) {
//        this.creditDec = creditDec;
//    }
//
//    public String getIndustry() {
//        return industry;
//    }
//
//    public void setIndustry(String industry) {
//        this.industry = industry;
//    }
//
//    public String getWorkNature() {
//        return workNature;
//    }
//
//    public void setWorkNature(String workNature) {
//        this.workNature = workNature;
//    }
//
//    public String getUserEarns() {
//        return userEarns;
//    }
//
//    public void setUserEarns(String userEarns) {
//        this.userEarns = userEarns;
//    }
//
//    public String getBoPaySource() {
//        return boPaySource;
//    }
//
//    public void setBoPaySource(String boPaySource) {
//        this.boPaySource = boPaySource;
//    }
//
//
//    public Map<String, String> getAuditkeys() {
//        return auditkeys;
//    }
//
//    public void setAuditkeys(Map<String, String> auditkeys) {
//        this.auditkeys = auditkeys;
//    }
}
