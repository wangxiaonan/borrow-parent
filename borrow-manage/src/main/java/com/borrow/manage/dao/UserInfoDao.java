package com.borrow.manage.dao;

import com.borrow.manage.model.dto.UserInfo;

/**
 * Created by wxn on 2018/9/12
 */
public interface UserInfoDao {

  UserInfo selInfoByIdcard(String idcard) ;

  UserInfo selByMobile(String mobile) ;

  void insertUserInfo(UserInfo userInfo);

  UserInfo selInfoByUid(String userUid);

  void updateUserInfo(String userUid,UserInfo userInfo);


}
