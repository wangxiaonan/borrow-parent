package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/20
 */
public enum RepayStatusEnum {

    PAY_NO("未还款",1),
    PAY_YES("已还款",2);

    RepayStatusEnum(String name, int code) {
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
        for (RepayStatusEnum stateEnum : RepayStatusEnum.values()) {
            if (stateEnum.getCode() == code) {
                return stateEnum.getName();
            }
        }
        return null;
    }
}
