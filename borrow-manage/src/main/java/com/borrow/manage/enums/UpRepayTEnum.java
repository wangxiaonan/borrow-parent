package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/21
 */
public enum UpRepayTEnum {

    UP_AUTO_PAY_AMOUNT("自动一次性还",1),
    MANUAL_PAY_AMOUNT("手工一次性还",2);

    UpRepayTEnum(String name, int code) {
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

    public static boolean isExist(int code) {
        for (UpRepayTEnum type : UpRepayTEnum.values()) {
            if (type.getCode() == code) {
                return true;
            }
        }
        return false;
    }
    public static String getName(int code) {
        for (UpRepayTEnum type : UpRepayTEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return null;
    }
}
