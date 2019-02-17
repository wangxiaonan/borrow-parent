package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/16
 */
public enum  ProductRateEnum {

    EARLY_SERVICE_RATE("EARLY_SERVICE_RATE","前期服务费率"),
    MONTH_SERVICE_RATE("MONTH_SERVICE_RATE","月服务费率"),
    MONTH_ACCRUAL_RATE("MONTH_ACCRUAL_RATE","月利息率"),
    GUARANTEE_VIOLATE_RATE("GUARANTEE_VIOLATE_RATE","担保方违约费率"),
    SERVICE_VIOLATE_RATE("SERVICE_VIOLATE_RATE","平台违约金费率"),
    EARLY_PAY_RATE("EARLY_PAY_RATE","提前还款费率"),
    GPS_COST("GPS_COST","GPS费"),
    EARLY_SERVICE_COST("EARLY_SERVICE_COST","前期服务费"),
    FINE_SERVICE_RATE("FINE_SERVICE_RATE","罚息率");
    ProductRateEnum(String rateKey, String rateDesc) {
        this.rateKey = rateKey;
        this.rateDesc = rateDesc;
    }
    private String rateKey;
    private String rateDesc;

    public String getRateKey() {
        return rateKey;
    }

    public void setRateKey(String rateKey) {
        this.rateKey = rateKey;
    }



    public String getRateDesc() {
        return rateDesc;
    }

    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
    }
}
