package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOrderItemDao;
import com.borrow.manage.dao.mapper.BoOrderItemMapper;
import com.borrow.manage.model.dto.BoOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
