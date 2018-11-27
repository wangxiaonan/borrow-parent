package com.borrow.manage.annotation;


import com.alibaba.fastjson.JSON;
import com.borrow.manage.config.Properties;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.model.XMap;
import com.borrow.manage.utils.ThreeDES;
import com.borrow.manage.vo.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wxn on 2018/11/22
 */
@Aspect
@Component
public class SigIncreaseAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Pointcut("@annotation(com.borrow.manage.annotation.SignatureIncrease)")
    private void anyMethod() {

    }

    @Around("anyMethod()")
    private Object increase(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objs = null;
        try {
            Object o = joinPoint.getArgs()[0];
            System.out.println(joinPoint.getSignature());
            if (o instanceof String) {
                String str =(String) o;
                try {
                    str =ThreeDES.decrypt(str, Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
                }catch (Exception e) {
                    logger.error("Signature is fail req:{}",str,e);
                    return ResponseResult.error(ExceptionCode.SIGNATURE_FAIL.getErrorCode()
                        ,ExceptionCode.SIGNATURE_FAIL.getErrorMessage());
                }
                objs = new Object[]{str};
            }else {
                return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                        ,ExceptionCode.PARAM_ERROR.getErrorMessage());

            }
        } catch (Throwable throwable) {
            logger.error("increase is error", throwable);
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    ,ExceptionCode.PARAM_ERROR.getErrorMessage());

        }
        return joinPoint.proceed(objs);
    }
}
