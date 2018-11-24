package com.borrow.manage.provider.remotecoll;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.model.XMap;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wxn on 2018/11/21
 */
public abstract class DataClient {

    private Logger logger = LoggerFactory.getLogger(DataClient.class);

    //构建数据请求模板
    abstract public String getData(XMap map);

    ResponseResult<XMap> analyze(String res) {
        XMap xMap = JSON.parseObject(res,XMap.class);
        String status = xMap.getString(PlatformConstant.FundsParam.STATUS);
        String msg = xMap.getString(PlatformConstant.FundsParam.MSG);
        if (PlatformConstant.FundsParam.SUCCESS.equals(status)) {
            return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),xMap);
        }else {
            return ResponseResult.error(ExceptionCode.REMOTE_ERROR.getErrorCode(),msg);
        }
    }

    protected  abstract String getUrlType();
}
