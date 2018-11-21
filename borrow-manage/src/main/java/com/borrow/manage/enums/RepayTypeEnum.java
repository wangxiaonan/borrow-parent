package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/21
 */
public enum RepayTypeEnum {

    PAY_NORMA("正常还款",1),
    PAY_UP("提前还款",2),
    OVERDUE("逾期还款",3);

    RepayTypeEnum(String name, int code) {
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
        for (RepayTypeEnum stateEnum : RepayTypeEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum.getName();
            }
        }
        return null;
    }
}
