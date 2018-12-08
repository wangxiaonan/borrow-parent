package com.borrow.manage.exception;

import com.borrow.manage.enums.ExceptionCode;

/**
 * Created by wxn on 2018/12/1
 */
public class BorrowDaoException extends BorrowException  {

    public BorrowDaoException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
