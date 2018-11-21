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
    EXPECT_ERROR("1010003","请确认还款期数");


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
