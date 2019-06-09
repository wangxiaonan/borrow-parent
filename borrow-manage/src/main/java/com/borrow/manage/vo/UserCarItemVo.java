package com.borrow.manage.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by wxn on 2019-06-08
 */
public class UserCarItemVo {

    private String carName;
    private String plateNumber;
    private String userUid;
    private String carModel;
    private String carColor;

    private String signTimeStr;
    private BigDecimal assessmentPrice;

    private String mileageDesc;

    // 写一下image url

    private String authIdcardUrl;
    private String vehicleLicenseUrl;
    private String pollingLicenseUrl;
    private String carSkinUrl;
    private String insurancePolicyUrl;
    private String letterCommitmentUrl;
    private String authOtherUrl;

    public String getSignTimeStr() {
        return signTimeStr;
    }

    public void setSignTimeStr(String signTimeStr) {
        this.signTimeStr = signTimeStr;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public String getAuthIdcardUrl() {
        return authIdcardUrl;
    }

    public void setAuthIdcardUrl(String authIdcardUrl) {
        this.authIdcardUrl = authIdcardUrl;
    }

    public String getVehicleLicenseUrl() {
        return vehicleLicenseUrl;
    }

    public void setVehicleLicenseUrl(String vehicleLicenseUrl) {
        this.vehicleLicenseUrl = vehicleLicenseUrl;
    }

    public String getPollingLicenseUrl() {
        return pollingLicenseUrl;
    }

    public void setPollingLicenseUrl(String pollingLicenseUrl) {
        this.pollingLicenseUrl = pollingLicenseUrl;
    }

    public String getCarSkinUrl() {
        return carSkinUrl;
    }

    public void setCarSkinUrl(String carSkinUrl) {
        this.carSkinUrl = carSkinUrl;
    }

    public String getInsurancePolicyUrl() {
        return insurancePolicyUrl;
    }

    public void setInsurancePolicyUrl(String insurancePolicyUrl) {
        this.insurancePolicyUrl = insurancePolicyUrl;
    }

    public String getLetterCommitmentUrl() {
        return letterCommitmentUrl;
    }

    public void setLetterCommitmentUrl(String letterCommitmentUrl) {
        this.letterCommitmentUrl = letterCommitmentUrl;
    }

    public String getAuthOtherUrl() {
        return authOtherUrl;
    }

    public void setAuthOtherUrl(String authOtherUrl) {
        this.authOtherUrl = authOtherUrl;
    }
}
