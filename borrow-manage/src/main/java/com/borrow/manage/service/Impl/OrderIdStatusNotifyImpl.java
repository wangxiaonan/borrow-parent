package com.borrow.manage.service.Impl;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.dao.BorrowOrderDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.exception.RemoteException;
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

/**
 * Created by wxn on 2018/11/24
 */

@Service("orderIdStatusNotify")
public class OrderIdStatusNotifyImpl implements FundsNotifyService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderServcie orderServcie;
    @Autowired
    private BorrowOrderDao borrowOrderDao;
    @Override
    public ResponseResult doNotify(XMap xMap) {
        logger.info("orderIdStatusNotify req={}", JSON.toJSONString(xMap));
        ResponseResult result = ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);;
        //1：上架筹款中，2：下架，3：流标，4：满标放款
        try {
            String orderId = xMap.getString(PlatformConstant.FundsParam.LOAN_NO);
            String type = xMap.getString(PlatformConstant.FundsParam.TYPE);
            BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(orderId));
            if (borrowOrder.getBoIsFinish() == 1) {
                throw new RemoteException(ExceptionCode.ORDER_STATUS_FUND_ERROR);
            }
            if ("2".equals(type) || "3".equals(type)) {
                OrderCancelReq cancelReq = new OrderCancelReq();
                cancelReq.setOrderId(orderId);
                orderServcie.orderCancel(cancelReq);
            }else if ("4".equals(type)) {
                MakeLoansReq loansReq = new MakeLoansReq();
                loansReq.setOrderId(orderId);
                orderServcie.makeLoans(loansReq);
            }
        }catch (BorrowException b) {
            result = ResponseResult.error(b.getExceptionCode().getErrorCode(),b.getExceptionCode().getErrorMessage());
        }catch (Exception e) {
            logger.error("orderIdStatusNotify is error",e);
            result = ResponseResult.error(ExceptionCode.SYSTEM_ERROR.getErrorCode(),ExceptionCode.SYSTEM_ERROR.getErrorMessage());
        }
        logger.info("orderIdStatusNotify res={}",JSON.toJSONString(result));
        return result;
    }
}
