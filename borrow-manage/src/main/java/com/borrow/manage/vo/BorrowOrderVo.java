package com.borrow.manage.vo;

import java.math.BigDecimal;

/**
 * Created by wxn on 2018/9/12
 */
public class BorrowOrderVo {

    private BigDecimal boPrice;

    private String boPaySource;

    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getBoPrice() {
        return boPrice;
    }

    public void setBoPrice(BigDecimal boPrice) {
        this.boPrice = boPrice;
    }

    public String getBoPaySource() {
        return boPaySource;
    }

    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource;
    }


}
