package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOrderAuditDao;
import com.borrow.manage.dao.mapper.BoOrderAuditMapper;
import com.borrow.manage.model.dto.BoOrderAudit;
import com.borrow.manage.model.dto.BoOrderAuditExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/15
 */
@Repository
public class BoOrderAuditDaoImpl implements BoOrderAuditDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BoOrderAuditMapper boOrderAuditMapper;

    @Override
    public void insertOrderAudit(BoOrderAudit boOrderAudit) {
        boOrderAuditMapper.insertSelective(boOrderAudit);
    }

    @Override
    public List<BoOrderAudit> selByOrderId(Long orderId) {
        logger.debug("selByOrderId:orderId={}",orderId);
        BoOrderAuditExample auditExample = new BoOrderAuditExample();
        auditExample.createCriteria().andOrderIdEqualTo(orderId);
        return boOrderAuditMapper.selectByExample(auditExample);
    }

    @Override
    public void delByOrderId(Long orderId) {
        BoOrderAuditExample auditExample = new BoOrderAuditExample();
        auditExample.createCriteria().andOrderIdEqualTo(orderId);
        boOrderAuditMapper.deleteByExample(auditExample);
    }

}
