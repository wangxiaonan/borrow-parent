package com.borrow.manage.dao.impl;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.dao.SysUserTokenDao;
import com.borrow.manage.dao.mapper.SysUserTokenMapper;
import com.borrow.manage.model.dto.SysUserToken;
import com.borrow.manage.model.dto.SysUserTokenExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by wxn on 2018/11/26
 */
@Repository
public class SysUserTokenDaoImpl implements SysUserTokenDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SysUserTokenMapper sysUserTokenMapper;
    @Override
    public SysUserToken selByToken(String token) {
        SysUserTokenExample example = new SysUserTokenExample();
        example.createCriteria().andTokenEqualTo(token);
        List<SysUserToken> userTokenList =  sysUserTokenMapper.selectByExample(example);
        return userTokenList.isEmpty()?null:userTokenList.get(0);
    }

    @Override
    public SysUserToken selByUserUid(String userUid) {
        SysUserTokenExample example = new SysUserTokenExample();
        example.createCriteria().andUserUidEqualTo(userUid);
        List<SysUserToken> userTokenList =  sysUserTokenMapper.selectByExample(example);
        return userTokenList.isEmpty()?null:userTokenList.get(0);
    }

    @Override
    public void updateByUserUid(String userUid, SysUserToken sysUserToken) {
        SysUserTokenExample example = new SysUserTokenExample();
        example.createCriteria().andUserUidEqualTo(userUid);
        sysUserToken.setUpdateTime(new Date());
        sysUserTokenMapper.updateByExampleSelective(sysUserToken,example);
    }

    @Override
    public void insertInfo(SysUserToken userToken) {
        logger.info("insertTokenInfo:{}", JSON.toJSONString(userToken));
        sysUserTokenMapper.insertSelective(userToken);
    }

}