package com.borrow.manage.vo;

import java.math.BigDecimal;

/**
 * Created by wxn on 2018/9/19
 */
public class RepayPlanCalReq extends BaseReq {


    private String pCode;
    private BigDecimal boPrice;

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public BigDecimal getBoPrice() {
        return boPrice;
    }

    public void setBoPrice(BigDecimal boPrice) {
        this.boPrice = boPrice;
    }
}
