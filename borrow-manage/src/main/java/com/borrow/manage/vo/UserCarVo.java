package com.borrow.manage.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxn on 2018/9/12
 */
public class UserCarVo {


    private String carName;


    private String plateNumber;


    private String userUid;

    private String carModel;


    private String carColor;


    private Date signTime;

    private BigDecimal assessmentPrice;


    private String mileageDesc;

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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
