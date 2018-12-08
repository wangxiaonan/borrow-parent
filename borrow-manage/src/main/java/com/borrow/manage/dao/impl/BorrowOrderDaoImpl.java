package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BorrowOrderDao;
import com.borrow.manage.dao.mapper.BorrowOrderMapper;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.exception.BorrowDaoException;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.model.dto.BorrowOrder;
import com.borrow.manage.model.dto.BorrowOrderExample;
import com.borrow.manage.vo.OrderListReq;
import com.borrow.manage.vo.OrderListRes;
import com.borrow.manage.vo.OrderPayListReq;
import com.borrow.manage.vo.OrderPayListRes;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
@Repository
public class BorrowOrderDaoImpl implements BorrowOrderDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BorrowOrderMapper borrowOrderMapper;
    @Override
    public void insertBorrowOrder(BorrowOrder borrowOrder) {

        borrowOrderMapper.insertSelective(borrowOrder);
    }

    @Override
    public BorrowOrder selByOrderId(long orderId) {
        logger.debug("selByOrderId:orderId={}",orderId);
        BorrowOrderExample orderExample = new BorrowOrderExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BorrowOrder> orderList = borrowOrderMapper.selectByExample(orderExample);
        return orderList.isEmpty()? null:orderList.get(0);
    }

    @Override
    public List<OrderPayListRes> selOrderPayListWhere(OrderPayListReq orderPayListReq) {
        PageHelper.startPage(orderPayListReq.getPageNo(), orderPayListReq.getPageSize());
        return borrowOrderMapper.selOrderPayList(orderPayListReq);
    }

    @Override
    public List<OrderPayListRes> selOrderPayListWhereAll(OrderPayListReq orderPayListReq) {
        return borrowOrderMapper.selOrderPayList(orderPayListReq);
    }

    @Override
    public void upBorrowOrderByOrderId(Long orderId, int boIsState) {
        BorrowOrderExample orderExample = new BorrowOrderExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);
        BorrowOrder borrowOrder = new BorrowOrder();
        borrowOrder.setUpdateTime(new Date());
        borrowOrder.setBoIsState(boIsState);
        int count = borrowOrderMapper.updateByExampleSelective(borrowOrder,orderExample);
        if (count != 1) {
            throw new BorrowDaoException(ExceptionCode.ORDER_IS_NOT_EXIST_ERROR);
        }
    }

    @Override
    public void upBoOrderByOrderId(Long orderId, int boIsState, BigDecimal boFinishPrice) {
        BorrowOrderExample record = new BorrowOrderExample();
        record.createCriteria().andOrderIdEqualTo(orderId);
        BorrowOrder order = new BorrowOrder();
        order.setUpdateTime(new Date());
        order.setBoIsState(boIsState);
        order.setBoPayState(1);
        order.setBoFinishPrice(boFinishPrice);
        order.setBoIsFinish(1);
        int count = borrowOrderMapper.updateByExampleSelective(order,record);
        if (count != 1) {
            throw new BorrowDaoException(ExceptionCode.ORDER_IS_NOT_EXIST_ERROR);
        }
    }

    @Override
    public void upBoPayExpectCount(long orderId) {
        int num = borrowOrderMapper.updateBoPayExpectCount(orderId);
        if (num !=1) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
    }

    @Override
    public void updateBorrowOrder(long orderId, BorrowOrder borrowOrder) {
        BorrowOrderExample record = new BorrowOrderExample();
        record.createCriteria().andOrderIdEqualTo(orderId);
        borrowOrder.setUpdateTime(new Date());
        borrowOrderMapper.updateByExampleSelective(borrowOrder,record);
    }

    @Override
    public List<BorrowOrder> selByState(int state) {
        BorrowOrderExample example = new BorrowOrderExample();
        example.createCriteria().andBoIsStateEqualTo(state);
        List<BorrowOrder> borrowOrders = borrowOrderMapper.selectByExample(example);
        return borrowOrders;
    }

    @Override
    public List<OrderListRes> selOrderListWhere(OrderListReq orderListReq) {
        PageHelper.startPage(orderListReq.getPageNo(), orderListReq.getPageSize());
        return borrowOrderMapper.selOrderList(orderListReq);
    }

    @Override
    public List<OrderListRes> selOrderListWhereAll(OrderListReq orderListReq) {
        return borrowOrderMapper.selOrderList(orderListReq);
    }
}
