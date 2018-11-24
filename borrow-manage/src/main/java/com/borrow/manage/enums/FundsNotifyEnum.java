package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/11/24
 */
public enum FundsNotifyEnum {

    SIGNATURE("SIGNATURE","SIGNATURE"),
    CONTROL("control","方法类型"),
    BORROW_ORDERID_AUDIT_HANDLE("borrowOrderIdAuditHandle","审核通知接口"),
    BORROW_ORDERID_STATUS_HANDLE("borrowOrderIdStatusHandle","标更新接口");

    FundsNotifyEnum(String methodType, String desc) {
        this.methodType = methodType;
        this.desc = desc;
    }
    private String methodType;
    private String desc;

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FundsNotifyEnum getEnum(String methodType) {
        for (FundsNotifyEnum notifyEnum : FundsNotifyEnum.values()) {
            if (notifyEnum.getMethodType().equals(methodType)) {
                return notifyEnum;
            }
        }
        return null;
    }
}
