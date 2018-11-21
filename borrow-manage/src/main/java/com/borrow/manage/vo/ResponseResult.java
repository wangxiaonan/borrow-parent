package com.borrow.manage.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by wxn on 2018/9/12
 */
public class ResponseResult<T> {
    private static final String SUCCESS_CODE = "0000000";
    private String errorCode;
    private String errorMessage;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(ResponseResult<T> old) {
        if (null != old.getErrorCode()) {
            this.errorCode = new String(old.getErrorCode());
        }

        if (null != old.getErrorMessage() && !"".equals(old.getErrorMessage())) {
            this.errorMessage = new String(old.getErrorMessage());
        }

        if (null != old.getData()) {
            this.data = old.getData();
        }

    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> error(String errorCode, Object errorMessage) {
        ResponseResult<T> result = new ResponseResult();
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage.toString());
        return result;
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        ResponseResult<T> result = new ResponseResult();
        result.setErrorCode("0000000");
        result.setErrorMessage(message);
        result.setData(data);
        return result;
    }


    public boolean isSucceed() {
        return "0000000".equals(this.errorCode);
    }

    public String toString() {
        return this.data != null ? "ResponseResult [errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + ", data=" + this.data.toString() + "]" : "ResponseResult [errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + "]";
    }
}



