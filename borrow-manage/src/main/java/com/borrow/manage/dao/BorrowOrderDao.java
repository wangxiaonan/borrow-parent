package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BorrowOrder;
import com.borrow.manage.vo.OrderListReq;
import com.borrow.manage.vo.OrderListRes;
import com.borrow.manage.vo.OrderPayListReq;
import com.borrow.manage.vo.OrderPayListRes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
public interface BorrowOrderDao {

    void insertBorrowOrder(BorrowOrder borrowOrder);

    List<OrderListRes> selOrderListWhere(OrderListReq orderListReq);

    List<OrderListRes> selOrderListWhereAll(OrderListReq orderListReq);



    BorrowOrder selByOrderId(long orderId);


    List<OrderPayListRes> selOrderPayListWhere(OrderPayListReq orderPayListReq);
    List<OrderPayListRes> selOrderPayListWhereAll(OrderPayListReq orderPayListReq);


    void  upBorrowOrderByOrderId(Long orderId,int boIsState);

    void  upBoOrderByOrderId(Long orderId, int boIsState, BigDecimal boFinishPrice);


    void upBoPayExpectCount(long orderId);

    void updateBorrowOrder(long orderId,BorrowOrder borrowOrder);


}
