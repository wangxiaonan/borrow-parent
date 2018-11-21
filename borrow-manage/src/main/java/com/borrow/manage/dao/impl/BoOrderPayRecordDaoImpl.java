package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOrderPayRecordDao;
import com.borrow.manage.dao.mapper.BoOrderPayRecordMapper;
import com.borrow.manage.model.dto.BoOrderPayRecord;
import com.borrow.manage.model.dto.BoOrderPayRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/26
 */
@Repository
public class BoOrderPayRecordDaoImpl implements BoOrderPayRecordDao {

    @Autowired
    private BoOrderPayRecordMapper boOrderPayRecordMapper;


    @Override
    public void insertPayOrder(BoOrderPayRecord boOrderPayRecord) {
        boOrderPayRecordMapper.insertSelective(boOrderPayRecord);
    }

    @Override
    public List<BoOrderPayRecord> selInfoByOrderId(long orderId) {
        BoOrderPayRecordExample example = new BoOrderPayRecordExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return boOrderPayRecordMapper.selectByExample(example);
    }
}
