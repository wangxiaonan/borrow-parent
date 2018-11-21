package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BorrowRepaymentDao;
import com.borrow.manage.dao.mapper.BorrowRepaymentMapper;
import com.borrow.manage.enums.RepayStatusEnum;
import com.borrow.manage.model.dto.BorrowOrderExample;
import com.borrow.manage.model.dto.BorrowRepayment;
import com.borrow.manage.model.dto.BorrowRepaymentExample;
import com.borrow.manage.vo.OrderRepayListReq;
import com.borrow.manage.vo.OrderRepayListRes;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by wxn on 2018/9/18
 */
@Repository
public class BorrowRepaymentDaoImpl  implements BorrowRepaymentDao{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BorrowRepaymentMapper borrowRepaymentMapper;

    @Override
    public List<OrderRepayListRes> selOrderRepayListWhere(OrderRepayListReq orderRepayListReq) {
        PageHelper.startPage(orderRepayListReq.getPageNo(), orderRepayListReq.getPageSize());
        return borrowRepaymentMapper.selOrderRepayList(orderRepayListReq);
    }

    @Override
    public List<OrderRepayListRes> selOrderRepayListWhereALL(OrderRepayListReq orderRepayListReq) {
        return borrowRepaymentMapper.selOrderRepayList(orderRepayListReq);
    }

    @Override
    public void insertRepayment(BorrowRepayment borrowRepayment) {
        borrowRepaymentMapper.insertSelective(borrowRepayment);
    }

    @Override
    public List<BorrowRepayment> selByOrderId(long orderId) {
        BorrowRepaymentExample orderExample = new BorrowRepaymentExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);
        return borrowRepaymentMapper.selectByExample(orderExample);
    }

    @Override
    public void updateRepayStatus(long repayId, int repayStatus) {
        BorrowRepaymentExample orderExample = new BorrowRepaymentExample();
        orderExample.createCriteria().andRepayIdEqualTo(repayId);
        BorrowRepayment repayment = new BorrowRepayment();
        repayment.setRepayStatus(repayStatus);
        repayment.setUpdateTime(new Date());
        borrowRepaymentMapper.updateByExampleSelective(repayment,orderExample);
    }

    @Override
    public void updateBoRepayment(long repayId, BorrowRepayment repayment) {
        BorrowRepaymentExample orderExample = new BorrowRepaymentExample();
        orderExample.createCriteria().andRepayIdEqualTo(repayId);
        repayment.setUpdateTime(new Date());
        borrowRepaymentMapper.updateByExampleSelective(repayment,orderExample);
    }

    @Override
    public BorrowRepayment selByRepayId(long repayId) {
        BorrowRepaymentExample orderExample = new BorrowRepaymentExample();
        orderExample.createCriteria().andRepayIdEqualTo(repayId);
        List<BorrowRepayment> repaymentList = borrowRepaymentMapper.selectByExample(orderExample);
        return repaymentList.isEmpty()? null:repaymentList.get(0);
    }

    @Override
    public List<BorrowRepayment> selOverdueListCal() {
        BorrowRepaymentExample orderExample = new BorrowRepaymentExample();
        orderExample.createCriteria()
                .andRepayStatusEqualTo(RepayStatusEnum.PAY_NO.getCode())
                .andBrTimeLessThan(new Date());
        return borrowRepaymentMapper.selectByExample(orderExample);
    }


}
