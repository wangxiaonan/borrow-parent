package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOrderItemDao;
import com.borrow.manage.dao.mapper.BoOrderItemMapper;
import com.borrow.manage.model.dto.BoOrderItem;
import com.borrow.manage.model.dto.BoOrderItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/11/27
 */
@Repository
public class BoOrderItemDaoImpl implements BoOrderItemDao {


    @Autowired
    BoOrderItemMapper boOrderItemMapper;
    @Override
    public void insertItem(BoOrderItem boOrderItem) {
        boOrderItemMapper.insertSelective(boOrderItem);
    }

    @Override
    public List<BoOrderItem> selByorderId(long orderId) {
        BoOrderItemExample example = new BoOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return boOrderItemMapper.selectByExample(example);
    }

    @Override
    public void updateItemValue(long orderId, String itermKey, BoOrderItem record) {
        BoOrderItemExample example = new BoOrderItemExample();
        example.createCriteria()
                .andOrderIdEqualTo(orderId)
                .andItemKeyEqualTo(itermKey);
        boOrderItemMapper.updateByExampleSelective(record,example);

    }

    @Override
    public void deleteItemValue(long orderId, String itermKey) {
        BoOrderItemExample example = new BoOrderItemExample();
        example.createCriteria()
                .andOrderIdEqualTo(orderId)
                .andItemKeyEqualTo(itermKey);
        boOrderItemMapper.deleteByExample(example);
    }


}
