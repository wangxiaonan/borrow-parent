package com.borrow.manage.vo.permisson;

import com.borrow.manage.vo.BaseReq;

/**
 * Created by wxn on 2018/11/26
 */
public class LoginReq extends BaseReq {

    public String loginName;
    public String passwd;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
