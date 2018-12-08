package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/12
 */
public enum ExceptionCode {
    // 10 代表风控系统
    SUCCESS("0000000","成功"),

     // 1 系统异常
    SYSTEM_ERROR("1010001","开小差了去喽~"),
    PARAM_ERROR("1010002","参数错误"),
    EXPECT_ERROR("1010003","请确认还款期数"),


    //2 系统提示

    USER_CHECK_OPEN("2010001","存管未开户"),
    USER_CHECK_IDENTITY("2010002","不是借款用户"),
    SIGNATURE_FAIL("2010003","签名解析是失败"),
    LOGIN_NAME_FAIL("2010004","用户名不存在"),
    LOGIN_PASSWD_FAIL("2010005","密码错误"),
    SYS_TOKEN_FAIL("2010006","请重新登录"),


    // 3 远端服务调用异常 01 是理财
    USER_CHECK_IDENTITY_ERROR("3010001","理财服务—用户身份确认接口异常"),
    REMOTE_ERROR("3010002","第三方服务状态码异常"),
    ORDER_MAKE_RAISE_ERROR("3010003","理财服务—筹标接口异常"),
    ORDER_TRANSFER_FUND_ERROR("3010004","理财服务—资金划拨接口异常"),
    ORDER_STATUS_FUND_ERROR("3010005","理财服务—订单状态重复通知异常"),

    // 4 数据库异常 01 订单
    ORDER_IS_NOT_EXIST_ERROR("4010001","订单不存在");

    private String errorCode;
    private String errorMessage;

    ExceptionCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode:'" + errorCode + '\'' +
                ", errorMessage:'" + errorMessage + '\'' +
                '}';
    }
}
