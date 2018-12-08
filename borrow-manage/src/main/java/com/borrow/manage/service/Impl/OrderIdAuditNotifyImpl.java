package com.borrow.manage.service.Impl;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.dao.BorrowOrderDao;
import com.borrow.manage.enums.BoIsFinishEnum;
import com.borrow.manage.enums.BoIsStateEnum;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.model.XMap;
import com.borrow.manage.model.dto.BorrowOrder;
import com.borrow.manage.service.FundsNotifyService;
import com.borrow.manage.service.OrderServcie;
import com.borrow.manage.vo.MakeLoansReq;
import com.borrow.manage.vo.OrderCancelReq;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxn on 2018/11/24
 */
@Service("orderIdAuditNotify")
public class OrderIdAuditNotifyImpl implements FundsNotifyService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderServcie orderServcie;

    @Override
    public ResponseResult doNotify(XMap xMap) {
        logger.info("orderIdAuditNotify req={}",JSON.toJSONString(xMap));
        ResponseResult result = ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);;

        try {
            String orderId = xMap.getString(PlatformConstant.FundsParam.LOAN_NO);
            String auditStatus = xMap.getString(PlatformConstant.FundsParam.AUDIT_STATUS);
            if (PlatformConstant.FundsParam.AUDIT_STATUS_NO.equals(auditStatus)) {
                OrderCancelReq cancelReq = new OrderCancelReq();
                cancelReq.setOrderId(orderId);
                orderServcie.orderCancel(cancelReq);
            }else if (PlatformConstant.FundsParam.AUDIT_STATUS_YES.equals(auditStatus)) {
                orderServcie.orderLoaning(orderId);
            }
        }catch (BorrowException b) {
            result = ResponseResult.error(b.getExceptionCode().getErrorCode(),b.getExceptionCode().getErrorMessage());
        }catch (Exception e) {
             logger.error("orderIdAuditNotify is error",e);
            result = ResponseResult.error(ExceptionCode.SYSTEM_ERROR.getErrorCode(),ExceptionCode.SYSTEM_ERROR.getErrorMessage());
        }
        logger.info("orderIdAuditNotify res={}",JSON.toJSONString(result));
        return result;
    }
}
