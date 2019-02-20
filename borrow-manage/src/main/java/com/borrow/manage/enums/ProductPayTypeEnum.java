package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/16
 */
public enum ProductPayTypeEnum {


    PAY_TYPE_ONE("一次性还本息",1),
    PAY_TYPE_TWO("等额本息",2);

    ProductPayTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }
    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public  static String getNameValue(int code) {
        for (ProductPayTypeEnum stateEnum : ProductPayTypeEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum.getName();
            }
        }
        return null;
    }

    public  static ProductPayTypeEnum getProductPayType(int code) {
        for (ProductPayTypeEnum stateEnum : ProductPayTypeEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum;
            }
        }
        return null;
    }

}
