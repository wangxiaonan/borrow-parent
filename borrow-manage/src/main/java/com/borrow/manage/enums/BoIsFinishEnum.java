package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/15
 */
public enum BoIsFinishEnum {

    FINISH_YES("已完成",1),
    FINISH_NO("未完成",0);

    BoIsFinishEnum(String name, int code) {
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
        for (BoIsFinishEnum stateEnum : BoIsFinishEnum.values()) {
             if (stateEnum.getCode() == code) {
                 return stateEnum.getName();
             }
        }
        return null;
    }
}
