package com.borrow.manage.enums;

/**
 * Created by wxn
 * Date:2019/5/5
 * Time:6:37
 */
public enum  BussTypeEnum {

    CARD("车贷",1),
    HOUSE("房贷",2);

    BussTypeEnum(String name, int code) {
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
        for (BussTypeEnum stateEnum : BussTypeEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum.getName();
            }
        }
        return null;
    }
}
