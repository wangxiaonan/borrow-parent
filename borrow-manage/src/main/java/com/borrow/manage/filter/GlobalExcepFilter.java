package com.borrow.manage.filter;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxn on 2018/9/15
 */
@RestController
@ControllerAdvice
public class GlobalExcepFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public ResponseResult defaultErrorHandler(HttpServletRequest request
            , HttpServletResponse response, Exception e) throws Exception {
        logger.error("全局错误异常:",e);
        ResponseResult tResult = null;
        if (e instanceof HttpMessageConversionException) {
            tResult= ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else if(e instanceof BorrowException){
            BorrowException borrowException = (BorrowException)e;
            tResult= ResponseResult.error(
                    borrowException.getExceptionCode().getErrorCode(),
                    borrowException.getExceptionCode().getErrorMessage());
        }else {
            tResult = ResponseResult.error(ExceptionCode.SYSTEM_ERROR.getErrorCode()
                    ,ExceptionCode.SYSTEM_ERROR.getErrorMessage());
        }

        return tResult;
    }
}
