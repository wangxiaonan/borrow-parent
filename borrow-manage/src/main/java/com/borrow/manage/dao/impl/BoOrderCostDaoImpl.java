package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOrderCostDao;
import com.borrow.manage.dao.mapper.BoOrderCostMapper;
import com.borrow.manage.model.dto.BoOrderCost;
import com.borrow.manage.model.dto.BoOrderCostExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/20
 */
@Repository
public class BoOrderCostDaoImpl implements BoOrderCostDao{

    @Autowired
    private BoOrderCostMapper boOrderCostMapper;
    @Override
    public void insertOrderCost(BoOrderCost boOrderCost) {

        boOrderCostMapper.insertSelective(boOrderCost);

    }

    @Override
    public List<BoOrderCost> selByOrderId(long orderId) {
        BoOrderCostExample orderCostExample = new BoOrderCostExample();
        orderCostExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BoOrderCost> boOrderCosts = boOrderCostMapper.selectByExample(orderCostExample);
        return boOrderCosts;
    }
}
