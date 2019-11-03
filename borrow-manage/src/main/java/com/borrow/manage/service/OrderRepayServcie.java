package com.borrow.manage.service;

import com.borrow.manage.vo.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxn on 2018/9/18
 */
public interface OrderRepayServcie {

    ResponseResult orderRepaySelList(OrderRepayListReq orderRepayListReq);

    void orderRepaySelListExport(HttpServletResponse response,OrderRepayListReq orderRepayListReq);


    ResponseResult orderRepayPlanCal(RepayPlanCalReq repayPlanCalReq);


    ResponseResult orderRepayPlan(OrderRepayPlanReq orderRepayPlanReq);



    ResponseResult selRepayListDetail(RepayListDetailReq repayListDetailReq);

    ResponseResult orderRepayOver(OrderPayOverReq orderPayOverReq);

    ResponseResult orderUpRepayCal(OrderUpRepayCalReq orderUpRepayCalReq);


    ResponseResult orderUpRepay(OrderUpRepayReq orderUpRepayReq);


    ResponseResult orderRepaySurety(OrderPayOverReq orderPayOverReq);

    /**
     * 逾期违约金 罚息减免
     * @param overdueReduceReq
     * @return
     */
    ResponseResult overdueReduce(OverdueReduceReq overdueReduceReq);

    /**
     *
     * @param reduceAddReq
     * @return
     */

    ResponseResult overdueAddReduce(OverdueReduceAddReq reduceAddReq);


    ResponseResult selReduceList(RepayReduceListReq reduceListReq);


    ResponseResult selReceivableList(RepayReceivableListReq req);


    ResponseResult repayOverStatistical(RepayOverStatisticalReq req);




}
