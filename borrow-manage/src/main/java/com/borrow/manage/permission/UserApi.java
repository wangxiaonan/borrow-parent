package com.borrow.manage.permission;

import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.service.SysUserService;
import com.borrow.manage.vo.OrderCreateReq;
import com.borrow.manage.vo.ResponseResult;
import com.borrow.manage.vo.permisson.LoginReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wxn on 2018/11/26
 */
@RestController
public class UserApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/user/permission/login", method = RequestMethod.POST)
    public ResponseResult permissionLogin(@RequestBody LoginReq loginReq) {
        logger.info("====>permissionLogin():req={}", loginReq);

        ResponseResult res = sysUserService.login(loginReq.getLoginName(),loginReq.getPasswd());
        logger.info("<====permissionLogin():res={}",res);
        return res;
    }
}
