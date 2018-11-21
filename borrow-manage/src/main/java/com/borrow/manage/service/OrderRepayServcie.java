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


}
