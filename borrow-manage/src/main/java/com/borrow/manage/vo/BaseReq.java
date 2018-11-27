package com.borrow.manage.vo;

import com.alibaba.fastjson.JSON;

/**
 * Created by wxn on 2018/11/26
 */
public class BaseReq {

    private String sysUserUid;
    private String token;

    private long clickTime;

    public long getClickTime() {
        return System.currentTimeMillis();
    }

    public void setClickTime(long clickTime) {
        this.clickTime = clickTime;
    }

    public String getSysUserUid() {
        return sysUserUid;
    }

    public void setSysUserUid(String sysUserUid) {
        this.sysUserUid = sysUserUid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
