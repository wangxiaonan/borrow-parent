package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.UserCarDao;
import com.borrow.manage.dao.mapper.UserCarMapper;
import com.borrow.manage.model.dto.UserCar;
import com.borrow.manage.model.dto.UserCarExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
@Repository
public class UserCarDaoImpl implements UserCarDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserCarMapper userCarMapper;


    @Override
    public void insertUserCar(UserCar userCar) {
        userCarMapper.insertSelective(userCar);
    }

    @Override
    public UserCar selByPlateNO(String userUid, String plateNumber) {
        logger.debug("selByPlateNO:userUid={},plateNumber={}",userUid,plateNumber);
        UserCarExample userCarExample = new UserCarExample();
        userCarExample.createCriteria().andUserUidEqualTo(userUid)
                .andPlateNumberEqualTo(plateNumber);
        List<UserCar> userCars =  userCarMapper.selectByExample(userCarExample);

        return userCars.isEmpty()? null:userCars.get(0);
    }

    @Override
    public UserCar selByUserUid(String userUid) {
        UserCarExample userCarExample = new UserCarExample();
        userCarExample.createCriteria().andUserUidEqualTo(userUid);

        List<UserCar> userCars =  userCarMapper.selectByExample(userCarExample);
        return userCars.isEmpty()? null:userCars.get(0);
    }

    @Override
    public void updateByUserUid(UserCar userCar,String userUid) {
        UserCarExample userCarExample = new UserCarExample();
        userCarExample.createCriteria().andUserUidEqualTo(userUid);

        userCarMapper.updateByExampleSelective(userCar,userCarExample);
    }
}
