package com.borrow.manage.service;

import com.borrow.manage.vo.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxn on 2018/9/12
 */
public interface OrderServcie {


     ResponseResult orderAdd(OrderCreateReq orderCreateReq);

     ResponseResult orderSelList(OrderListReq orderListReq);

     void orderSelListExport(HttpServletResponse response,OrderListReq orderListReq);

     ResponseResult orderDetailSel(OrderDetailReq orderDetailReq);

     ResponseResult orderPaySelList(OrderPayListReq orderPayListReq);

     void orderPaySelListExport(HttpServletResponse response,OrderPayListReq orderPayListReq);

     ResponseResult makeLoans(MakeLoansReq makeLoansReq);

     ResponseResult orderCancel(OrderCancelReq orderCancelReq);

     ResponseResult makeRaise(MakeLoansReq makeLoansReq);











}
