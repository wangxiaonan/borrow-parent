package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.SysUserDao;
import com.borrow.manage.dao.SysUserTokenDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.model.dto.SysUser;
import com.borrow.manage.model.dto.SysUserToken;
import com.borrow.manage.service.SysUserService;
import com.borrow.manage.utils.PasswordHelper;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.vo.ResponseResult;
import com.borrow.manage.vo.permisson.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wxn on 2018/11/26
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;


    @Override
    @Transactional
    public ResponseResult login(String loginName, String passwd) {
        SysUser sysUser = sysUserDao.selByLoginName(loginName);
        if (sysUser == null) {
           return ResponseResult.error(ExceptionCode.LOGIN_NAME_FAIL.getErrorCode()
                    ,ExceptionCode.LOGIN_NAME_FAIL.getErrorMessage());
        }
        if (!PasswordHelper.verifyPassword(passwd,sysUser.getPasswd())) {
            return ResponseResult.error(ExceptionCode.LOGIN_PASSWD_FAIL.getErrorCode()
                    ,ExceptionCode.LOGIN_PASSWD_FAIL.getErrorMessage());
        }
        SysUserToken sysUserToken = sysUserTokenDao.selByUserUid(sysUser.getUuid());
        SysUserToken userToken = new SysUserToken();
        String token = UUIDProvider.uuid();
        if (sysUserToken == null) {
            userToken.setUuid(UUIDProvider.uuid());
            userToken.setUserUid(sysUser.getUuid());
            userToken.setToken(token);
            long expiteTime = System.currentTimeMillis()+3600*1000;
            userToken.setExpireTime(new Date(expiteTime));
            sysUserTokenDao.insertInfo(userToken);
        }else {
            userToken.setToken(token);
            long expiteTime = System.currentTimeMillis()+3600*1000;
            userToken.setExpireTime(new Date(expiteTime));
            sysUserTokenDao.updateByUserUid(sysUser.getUuid(),userToken);
        }
        LoginRes loginRes = new LoginRes();
        loginRes.setToken(token);
        loginRes.setSysUserUid(sysUser.getUuid());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),loginRes);
    }

}
