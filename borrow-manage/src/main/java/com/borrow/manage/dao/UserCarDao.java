package com.borrow.manage.dao;

import com.borrow.manage.model.dto.UserCar;

/**
 * Created by wxn on 2018/9/14
 */
public interface UserCarDao {

    void insertUserCar(UserCar userCar);


    UserCar selByPlateNO(String userUid,String plateNumber);

    UserCar selByUserUid(String userUid);

    void updateByUserUid(UserCar userCar,String userUid);


}
