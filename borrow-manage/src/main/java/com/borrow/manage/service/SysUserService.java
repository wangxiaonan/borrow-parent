package com.borrow.manage.service;

import com.borrow.manage.vo.ResponseResult;

/**
 * Created by wxn on 2018/11/26
 */
public interface SysUserService {

    public ResponseResult login(String loginName,String passwd);
}
