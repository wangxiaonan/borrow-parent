package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.UserInfoDao;
import com.borrow.manage.dao.mapper.UserInfoMapper;
import com.borrow.manage.model.dto.UserInfo;
import com.borrow.manage.model.dto.UserInfoExample;
import com.borrow.manage.utils.UUIDProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/12
 */
@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selInfoByIdcard(String idcard) {
        logger.debug("selInfoByIdcard:idcard={}",idcard);
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andIdcardEqualTo(idcard);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos.isEmpty()?null:userInfos.get(0);
    }

    @Override
    public UserInfo selByMobile(String mobile) {
        logger.debug("selMobile:mobile={}",mobile);
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos.isEmpty()?null:userInfos.get(0);

    }

    @Override
    public void insertUserInfo(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);

    }

    @Override
    public UserInfo selInfoByUid(String userUid) {
        logger.debug("selInfoByUid:userUid={}",userUid);
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUuidEqualTo(userUid);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos.isEmpty()?null:userInfos.get(0);
    }

}
