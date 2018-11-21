package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/16
 */
public enum ProductEnum {

    CAR_LOAN_ONE("车满金1号","0001"),
    CAR_LOAN_TWO("车满金2号","0002");

    ProductEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ProductEnum getProductEnum(String pCode){
        for (ProductEnum productEnum :ProductEnum.values()) {
            if (productEnum.getCode().equals(pCode)) {
                return productEnum;
            }
        }
        return null;
    }
}
