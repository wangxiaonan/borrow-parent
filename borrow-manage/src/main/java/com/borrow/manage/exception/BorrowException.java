package com.borrow.manage.exception;

import com.borrow.manage.enums.ExceptionCode;

/**
 * Created by wxn on 2018/9/15
 */
public class BorrowException extends RuntimeException {
    private ExceptionCode exceptionCode;

    public BorrowException(ExceptionCode exceptionCode){
        super(exceptionCode.getErrorMessage());
        this.exceptionCode = exceptionCode;
    }
    @Override
    public String toString() {
        return exceptionCode.toString();
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
