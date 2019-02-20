package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.UpRepayTEnum;
import com.borrow.manage.service.OrderRepayServcie;
import com.borrow.manage.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by wxn on 2018/9/18
 */
@RestController
public class OrderRepayApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderRepayServcie orderRepayServcie;


    @RequestMapping(value = "/borrow/repayment/sel", method = RequestMethod.POST)
    public ResponseResult selRepaymentList(@RequestBody OrderRepayListReq orderRepayListReq) {
        logger.info("====>selRepaymentList():req={}", orderRepayListReq);

        ResponseResult res = orderRepayServcie.orderRepaySelList(orderRepayListReq);
        logger.info("<====selRepaymentList():res={}",res);
        return res;
    }

    @RequestMapping(value = "/borrow/repayment/sel/export")
    @ResponseBody
    public void selRepaymentListExport(HttpServletResponse response, OrderRepayListReq orderRepayListReq) {
        logger.info("====>selRepaymentListExport():req={}", orderRepayListReq);
        orderRepayServcie.orderRepaySelListExport(response,orderRepayListReq);

    }

    @RequestMapping(value = "/borrow/repayplan/cal", method = RequestMethod.POST)
    public ResponseResult repayPlanCal(@RequestBody RepayPlanCalReq repayPlanCalReq) {
        logger.info("====>repayPlanCal():req={}", repayPlanCalReq);
        ResponseResult res = null;

        if (repayPlanCalReq.getBoPrice() == null
                || StringUtils.isEmpty(repayPlanCalReq.getpCode())
                ||repayPlanCalReq.getBoPrice().compareTo(BigDecimal.ZERO) <=0) {

            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    ,ExceptionCode.PARAM_ERROR.getErrorMessage());


        }else {
            res = orderRepayServcie.orderRepayPlanCal(repayPlanCalReq);
        }
        logger.info("<====repayPlanCal():res={}", res);
        return res;

    }


    @RequestMapping(value = "/order/repay/plan", method = RequestMethod.POST)
    public ResponseResult orderRepayPlan(@RequestBody OrderRepayPlanReq orderCancelReq) {
        logger.info("====>orderRepayPlan():req={}", orderCancelReq);
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderCancelReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else {
            res =  orderRepayServcie.orderRepayPlan(orderCancelReq);
        }
        logger.info("<====orderRepayPlan():res={}",res);
        return res;
    }

    @RequestMapping(value = "/order/repay/detail", method = RequestMethod.POST)
    public ResponseResult orderRepayDetail(@RequestBody RepayListDetailReq repayListDetailReq) {
        logger.info("====>orderRepayDetail():req={}", repayListDetailReq);

        ResponseResult res = null;

        if (StringUtils.isEmpty(repayListDetailReq.getOrderId())) {
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }else {
            res =  orderRepayServcie.selRepayListDetail(repayListDetailReq);
        }
        logger.info("<====orderRepayDetail():res={}",res);
        return res;
    }

    @RequestMapping(value = "/order/repay/over", method = RequestMethod.POST)
    public ResponseResult orderRepayOver(@RequestBody OrderPayOverReq orderPayOverReq) {

        logger.info("====>orderRepayOver():req={}", orderPayOverReq);
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderPayOverReq.getOrderId())
                || StringUtils.isEmpty(orderPayOverReq.getRepayId()))
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(), ExceptionCode.PARAM_ERROR.getErrorMessage());
        else {
            res =  orderRepayServcie.orderRepayOver(orderPayOverReq);
        }
        logger.info("<====orderRepayOver():res={}",res);
        return res;
    }

    @RequestMapping(value = "/order/up/repay/cal", method = RequestMethod.POST)
    public ResponseResult orderUpRepayCal(@RequestBody OrderUpRepayCalReq orderUpRepayCalReq) {
        logger.info("====>orderUpRepayCal():req={}", orderUpRepayCalReq);
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderUpRepayCalReq.getOrderId())
//                || StringUtils.isEmpty(orderUpRepayCalReq.getRepayId())
                )
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(), ExceptionCode.PARAM_ERROR.getErrorMessage());
        else {
            res =  orderRepayServcie.orderUpRepayCal(orderUpRepayCalReq);
        }
        logger.info("<====orderUpRepayCal():res={}",res);
        return res;


    }

    @RequestMapping(value = "/order/up/repay", method = RequestMethod.POST)
    public ResponseResult orderUpRepay(@RequestBody OrderUpRepayReq orderUpRepayReq) {

        logger.info("====>orderUpRepay():req={}", orderUpRepayReq);
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderUpRepayReq.getOrderId())
                || !UpRepayTEnum.isExist(Integer.valueOf(orderUpRepayReq.getUpPayType()))
                ||(Integer.valueOf(orderUpRepayReq.getUpPayType()) ==UpRepayTEnum.MANUAL_PAY_AMOUNT.getCode()
                && orderUpRepayReq.getPayAmount() ==null)
                )

            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(), ExceptionCode.PARAM_ERROR.getErrorMessage());
        else {
            res =  orderRepayServcie.orderUpRepay(orderUpRepayReq);
        }
        logger.info("<====orderUpRepay():res={}",res);
        return res;
    }

    /**
     * 担保垫付
     * @param orderPayOverReq
     * @return
     */
    @RequestMapping(value = "/order/repay/surety", method = RequestMethod.POST)
    public ResponseResult orderRepaySurety(@RequestBody OrderPayOverReq orderPayOverReq) {

        logger.info("====>orderRepaySurety():req={}", orderPayOverReq);
        ResponseResult res = null;

        if (StringUtils.isEmpty(orderPayOverReq.getOrderId())
                || StringUtils.isEmpty(orderPayOverReq.getRepayId()))
            res = ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(), ExceptionCode.PARAM_ERROR.getErrorMessage());
        else {
            res =  orderRepayServcie.orderRepaySurety(orderPayOverReq);
        }
        logger.info("<====orderRepaySurety():res={}",res);
        return res;
    }

}
