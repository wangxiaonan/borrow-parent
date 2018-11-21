package com.borrow.manage.enums;

import java.util.Arrays;

/**
 * Created by wxn on 2018/9/15
 */
public enum  OrderAuditEnum {

    AUTH_IDARD("二代身份证","AUTH_IDCARD"),
    AUTH_CARPHOTO("人车合照","AUTH_CARPHOTO"),
    AUTH_VEHICLE_LICENSE("行驶证","VEHICLE_LICENSE"),
    AUTH_APPLY_TABLE("申请表","APPLY_TABLE"),
    AUTH_MORTGAGE("抵押手续办理","MORTGAGE"),
    AUTH_OTHER("其它审核资料","AUTH_OTHER"),

    ;

    OrderAuditEnum(String authName,String authKey){
        this.authKey = authKey;
        this.authName = authName;
    }

    private String authName;
    private String authKey;

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public static String getAuthNameByKey(String authKey) {
        for (OrderAuditEnum type : OrderAuditEnum.values()) {
            if (type.getAuthKey().equals(authKey)) {
                return type.getAuthName();
            }
        }
        return null;
    }

}
