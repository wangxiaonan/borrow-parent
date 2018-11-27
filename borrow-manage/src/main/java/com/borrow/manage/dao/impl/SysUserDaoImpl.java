package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.SysUserDao;
import com.borrow.manage.dao.mapper.SysUserMapper;
import com.borrow.manage.model.dto.SysUser;
import com.borrow.manage.model.dto.SysUserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/11/26
 */
@Repository
public class SysUserDaoImpl implements SysUserDao {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser selByLoginName(String loginName) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andLoginNameEqualTo(loginName);
        List<SysUser> sysUsers =  sysUserMapper.selectByExample(example);
        return sysUsers.isEmpty()?null:sysUsers.get(0);
    }


}
