package com.borrow.manage.dao;

import com.borrow.manage.model.dto.SysUserToken;

/**
 * Created by wxn on 2018/11/26
 */
public interface SysUserTokenDao {

    SysUserToken selByToken(String token);

    SysUserToken selByUserUid(String userUid);

    void updateByUserUid(String userUid,SysUserToken sysUserToken);

    void insertInfo(SysUserToken userToken);


}
