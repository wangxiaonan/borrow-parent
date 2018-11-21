package com.borrow.manage.provider.remotecoll;

import com.borrow.manage.model.XMap;
import com.borrow.manage.vo.ResponseResult;

import java.util.Map;

/**
 * Created by wxn on 2018/11/21
 */
public abstract class DataClient {


    //构建数据请求模板
    public String getData(XMap map){
        return null;
    }

    ResponseResult<XMap> analyze(String res) {
        return null;
    }

    protected  abstract String getUrlType();
}
