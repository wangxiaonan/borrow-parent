package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BorrowRepayment;
import com.borrow.manage.vo.OrderRepayListReq;
import com.borrow.manage.vo.OrderRepayListRes;

import java.util.List;

/**
 * Created by wxn on 2018/9/18
 */
public interface BorrowRepaymentDao {

    List<OrderRepayListRes> selOrderRepayListWhere(OrderRepayListReq orderRepayListReq);

    List<OrderRepayListRes> selOrderRepayListWhereALL(OrderRepayListReq orderRepayListReq);

    void  insertRepayment(BorrowRepayment borrowRepayment);

    List<BorrowRepayment> selByOrderId(long orderId);

    void updateRepayStatus(long repayId,int repayStatus);

    void updateBoRepayment(long repayId,BorrowRepayment repayment);

    BorrowRepayment selByRepayId(long repayId);


    List<BorrowRepayment> selOverdueListCal();

}
