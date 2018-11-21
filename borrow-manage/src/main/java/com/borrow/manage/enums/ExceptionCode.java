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
    USER_CHECK_IDENTITY("2010002","不是借款身份"),

    // 3 远端服务调用异常 01 是理财
    USER_CHECK_IDENTITY_ERROR("3010001","理财服务—用户身份确认接口异常"),

    REMOTE_ERROR("3010002","第三方服务状态码异常");

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
