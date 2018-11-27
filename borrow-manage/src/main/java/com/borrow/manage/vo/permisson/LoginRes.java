package com.borrow.manage.vo.permisson;

/**
 * Created by wxn on 2018/11/26
 */
public class LoginRes {

    private String token;
    private String sysUserUid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSysUserUid() {
        return sysUserUid;
    }

    public void setSysUserUid(String sysUserUid) {
        this.sysUserUid = sysUserUid;
    }
}
