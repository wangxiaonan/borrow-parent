package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/15
 */
public enum BoIsStateEnum {

    WAITING("待筹标",1),
    LOANING("筹标中",2),
    LOAN_CANCEL("流标",3),
    LOAN_OVER("已放款",4),
    PAYING("还款中",5),
    PAY_OVER("已放款",6);

    BoIsStateEnum(String name, int code) {
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
        for (BoIsStateEnum stateEnum : BoIsStateEnum.values()) {
             if (stateEnum.getCode() == code) {
                 return stateEnum.getName();
             }
        }
        return null;
    }
}
