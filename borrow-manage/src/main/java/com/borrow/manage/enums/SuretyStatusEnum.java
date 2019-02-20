package com.borrow.manage.enums;

/**
 * Created by wxn on 2019-02-17
 */
public enum SuretyStatusEnum {

    SURETY_STATUS_NO("未担保",1),
    SURETY_STATUS_YES("已担保",2);

    SuretyStatusEnum(String name, int code) {
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
        for (SuretyStatusEnum stateEnum : SuretyStatusEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum.getName();
            }
        }
        return null;
    }

}
