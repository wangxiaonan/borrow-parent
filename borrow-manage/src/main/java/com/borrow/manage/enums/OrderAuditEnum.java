package com.borrow.manage.enums;

import java.util.Arrays;

/**
 * Created by wxn on 2018/9/15
 */
public enum  OrderAuditEnum {

    AUTH_IDARD("二代身份证","AUTH_IDCARD","1"),
    AUTH_VEHICLE_LICENSE("行驶证","VEHICLE_LICENSE","2"),
    POLLING_LICENSE("登记证","POLLING_LICENSE","3"),
    INSURANCE_POLICY("保单","INSURANCE_POLICY","4"),
    VIOLATION_RECORD("违章信息","VIOLATION_RECORD","5"),
    AUTH_APPLY_TABLE("申请表","APPLY_TABLE","6"),
    AUTH_CARPHOTO("人车合照","AUTH_CARPHOTO","7"),
    ADDRESS_LICENSE("住址证明","ADDRESS_LICENSE","8"),
    CAR_SKIN("车辆外观照片","CAR_SKIN","9"),
    CAR_MILEAGE("车辆外观照片","CAR_MILEAGE","10"),
    CAR_MOTOR("车辆发动机照片","CAR_MOTOR","11"),
    CAR_NUMBER("铭牌照片","CAR_NUMBER","12"),
    AUTH_OTHER("其它审核资料","AUTH_OTHER","14"),
    AUTH_MORTGAGE("抵押手续办理","MORTGAGE","5")

    ;

    OrderAuditEnum(String authName,String authKey,String code){
        this.authKey = authKey;
        this.authName = authName;
        this.code =code;
    }

    private String authName;
    private String authKey;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public static String getAuthCodeByKey(String authKey) {
        for (OrderAuditEnum type : OrderAuditEnum.values()) {
            if (type.getAuthKey().equals(authKey)) {
                return type.getCode();
            }
        }
        return null;
    }

}
