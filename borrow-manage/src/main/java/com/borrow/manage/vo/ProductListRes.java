package com.borrow.manage.vo;

import com.borrow.manage.enums.ProductPayTypeEnum;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by wxn on 2018/9/17
 */
public class ProductListRes {

    private String productCode;
    private String productName;
    private String productExpect;

    private String earlyServiceRate;
    private String monthServiceRate;
    private String monthAccrualRate;

    private String guaranteeViolateRate;
    private String serviceViolateRate;

    private String earlyPayRate;

    private String pPayType;

    private String pPayTypeName;

    public String getpPayTypeName() {
        return ProductPayTypeEnum.getProductPayType(Integer.valueOf(pPayType)).getName();
    }

    public void setpPayTypeName(String pPayTypeName) {

        this.pPayTypeName = pPayTypeName;
    }

    private Date createTime;

    public String getpPayType() {
        return pPayType;
    }

    public void setpPayType(String pPayType) {
        this.pPayType = pPayType;
    }

    public String getCreateTime() {
        return createTime ==null? "": DateFormatUtils.format(createTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductExpect() {
        return productExpect;
    }

    public void setProductExpect(String productExpect) {
        this.productExpect = productExpect;
    }

    public String getEarlyServiceRate() {
        return earlyServiceRate;
    }

    public void setEarlyServiceRate(String earlyServiceRate) {
        this.earlyServiceRate = earlyServiceRate;
    }

    public String getMonthServiceRate() {
        return monthServiceRate;
    }

    public void setMonthServiceRate(String monthServiceRate) {
        this.monthServiceRate = monthServiceRate;
    }

    public String getMonthAccrualRate() {
        return monthAccrualRate;
    }

    public void setMonthAccrualRate(String monthAccrualRate) {
        this.monthAccrualRate = monthAccrualRate;
    }

    public String getGuaranteeViolateRate() {
        return guaranteeViolateRate;
    }

    public void setGuaranteeViolateRate(String guaranteeViolateRate) {
        this.guaranteeViolateRate = guaranteeViolateRate;
    }

    public String getServiceViolateRate() {
        return serviceViolateRate;
    }

    public void setServiceViolateRate(String serviceViolateRate) {
        this.serviceViolateRate = serviceViolateRate;
    }

    public String getEarlyPayRate() {
        return earlyPayRate;
    }

    public void setEarlyPayRate(String earlyPayRate) {
        this.earlyPayRate = earlyPayRate;
    }
}
