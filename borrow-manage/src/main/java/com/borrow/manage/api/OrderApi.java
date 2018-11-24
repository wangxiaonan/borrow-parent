package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.OrderAuditEnum;
import com.borrow.manage.service.OrderServcie;
import com.borrow.manage.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wxn on 2018/9/12
 */
@RestController
public class OrderApi {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderServcie  orderServcie;

    @RequestMapping(value = "/borrow/order/add", method = RequestMethod.POST)
    public ResponseResult addOrder(@RequestBody OrderCreateReq orderCreateReq) {
        ResponseResult res = null;
        logger.info("====>addOrder():req={}", JSON.toJSON(orderCreateReq));

        if (checkAddParams(orderCreateReq)) {
            res= orderServcie.orderAdd(orderCreateReq);
        }else {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        logger.info("<====addOrder():res={}",JSON.toJSON(res));
        return res;
    }
    @RequestMapping(value = "/borrow/order/sel", method = RequestMethod.POST)
    public ResponseResult selOrderList(@RequestBody OrderListReq orderListReq) {
        logger.info("====>selOrderList():req={}", JSON.toJSON(orderListReq));
        ResponseResult res =  orderServcie.orderSelList(orderListReq);
        logger.info("<====selOrderList():res={}",JSON.toJSON(res));
        return res;
    }

    @RequestMapping(value = "/borrow/order/sel/export")
    @ResponseBody
    public void selOrderListExport(HttpServletResponse response, OrderListReq orderListReq) {
        logger.info("====>selOrderListExport():req={}", JSON.toJSON(orderListReq));
        orderServcie.orderSelListExport(response,orderListReq);
    }


    @RequestMapping(value = "/borrow/order/sel/detail", method = RequestMethod.POST)
    public ResponseResult selOrderDetail(@RequestBody OrderDetailReq orderDetailReq) {
        logger.info("====>selOrderDetail():req={}", JSON.toJSON(orderDetailReq));
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderDetailReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());

        }else {
            res =  orderServcie.orderDetailSel(orderDetailReq);
        }
        logger.info("<====selOrderDetail():res={}",JSON.toJSON(res));
        return res;
    }

    @RequestMapping(value = "/borrow/pay/sel", method = RequestMethod.POST)
    public ResponseResult selOrderPayList(@RequestBody OrderPayListReq orderPayListReq) {
        logger.info("====>selOrderPayList():req={}", JSON.toJSON(orderPayListReq));
        ResponseResult res =  orderServcie.orderPaySelList(orderPayListReq);
        logger.info("<====selOrderPayList():res={}",JSON.toJSON(res));
        return res;
    }

    @RequestMapping(value = "/borrow/pay/sel/export")
    @ResponseBody
    public void selOrderPayListExport(HttpServletResponse response,OrderPayListReq orderPayListReq) {
        logger.info("====>selOrderPayListExport():req={}", JSON.toJSON(orderPayListReq));
        orderServcie.orderPaySelListExport(response,orderPayListReq);
    }

    @RequestMapping(value = "/make/raise", method = RequestMethod.POST)
    public ResponseResult makeRaise(@RequestBody MakeLoansReq makeLoansReq) {
        logger.info("====>MakeRaise():req={}", JSON.toJSON(makeLoansReq));
        ResponseResult res = null;
        if (StringUtils.isEmpty(makeLoansReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else {
            res =  orderServcie.makeRaise(makeLoansReq);
        }
        logger.info("<====makeRaise():res={}",JSON.toJSON(res));
        return res;
    }


//    @RequestMapping(value = "/make/loans", method = RequestMethod.POST)
    public ResponseResult makeLoans(@RequestBody MakeLoansReq makeLoansReq) {
        logger.info("====>makeLoans():req={}", JSON.toJSON(makeLoansReq));
        ResponseResult res = null;

        if (StringUtils.isEmpty(makeLoansReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else {
            res =  orderServcie.makeLoans(makeLoansReq);
        }
        logger.info("<====makeLoans():res={}",JSON.toJSON(res));
        return res;
    }

    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    public ResponseResult orderCancel(@RequestBody OrderCancelReq orderCancelReq) {
        logger.info("====>orderCancel():req={}", JSON.toJSON(orderCancelReq));
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderCancelReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else {
            res =  orderServcie.orderCancel(orderCancelReq);
        }
        logger.info("<====orderCancel():res={}",JSON.toJSON(res));
        return res;
    }





    private boolean checkAddParams(OrderCreateReq orderCreateReq) {
        boolean checkStatus =false;
        if (    orderCreateReq == null
                || orderCreateReq.getUserInfo() == null
                || orderCreateReq.getUserCar() == null
                || orderCreateReq.getBorrowOrder() == null
                || orderCreateReq.getBorrowSalesman() == null
                || orderCreateReq.getOrderAudit() == null
                ) {
            return checkStatus;
        }
        if (
                StringUtils.isEmpty(orderCreateReq.getUserInfo().getCreditDec())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getIdcard())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getIndustry())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getMobile())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getUserEarns())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getUserName())
                || StringUtils.isEmpty(orderCreateReq.getUserInfo().getWorkNature())
                )
        {
            return checkStatus;
        }

        if (
                StringUtils.isEmpty(orderCreateReq.getUserCar().getPlateNumber())
                )
        {
            return checkStatus;
        }
        if (StringUtils.isEmpty(orderCreateReq.getBorrowOrder().getBoPaySource())
            || StringUtils.isEmpty(orderCreateReq.getBorrowOrder().getProductCode())
            || orderCreateReq.getBorrowOrder().getBoPrice().compareTo(BigDecimal.ZERO) <=0
         )
        {
            return checkStatus;
        }

        if (StringUtils.isEmpty(orderCreateReq.getBorrowSalesman().getSalesMobile())
                || StringUtils.isEmpty(orderCreateReq.getBorrowSalesman().getSalesName())
                )
        {
            return checkStatus;
        }

        if (orderCreateReq.getOrderAudit().getAuditkeys() == null
                || orderCreateReq.getOrderAudit().getAuditkeys().size() == 0
                )
        {
            return checkStatus;
        }
        List<String> authKeys = orderCreateReq.getOrderAudit().getAuditkeys();
        for (String s: authKeys) {
            if (StringUtils.isEmpty(OrderAuditEnum.getAuthNameByKey(s))) {
                return checkStatus;
            }
        }
        return true;
    }


}
