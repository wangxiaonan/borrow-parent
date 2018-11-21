package com.borrow.manage.exception;

import com.borrow.manage.enums.ExceptionCode;

/**
 * Created by wxn on 2018/11/21
 */
public class RemoteException extends BorrowException{

    public RemoteException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
