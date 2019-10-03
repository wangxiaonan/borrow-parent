package com.borrow.manage.dao;

import com.borrow.manage.model.dto.SysUser;

/**
 * Created by wxn on 2018/11/26
 */
public interface SysUserDao {

    SysUser selByLoginName(String loginName);

    SysUser selByUserUid(String userUid);

}
