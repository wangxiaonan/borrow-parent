package com.borrow.manage.provider.remotecoll;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.config.Properties;
import com.borrow.manage.config.RemoteConfig;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.model.XMap;
import com.borrow.manage.provider.RestHttpClientService;
import com.borrow.manage.utils.ThreeDES;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxn on 2018/11/21
 */
public abstract class DataClient {

    private Logger logger = LoggerFactory.getLogger(DataClient.class);

    @Autowired
    RemoteConfig remoteConfig;

    @Autowired
    RestHttpClientService commonRestTempate;

    //构建数据请求模板
    abstract public String getData(XMap map);

    ResponseResult<XMap> analyze(String res) {
        XMap xMap = JSON.parseObject(res,XMap.class);
        String code = xMap.getString(PlatformConstant.FundsParam.CODE);
        String msg = xMap.getString(PlatformConstant.FundsParam.MSG);
        if (PlatformConstant.FundsParam.SUCCESS.equals(code)) {
            return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),xMap);
        }else {
            return ResponseResult.error(ExceptionCode.REMOTE_ERROR.getErrorCode(),msg);
        }
    }

    protected  abstract String getUrlType();

    protected String doSendRequest(String reqParam) {
        if (remoteConfig.fundsFlag == 1) {
            logger.info("******远端开关关闭******");
            return "{\"code\":\"0000\"}";
        }
        HashMap signatureMap = new HashMap();
        reqParam = ThreeDES.encrypt(reqParam, Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
        signatureMap.put(PlatformConstant.FundsParam.SIGNATURE,reqParam);
        String jsonData = commonRestTempate.postForObjectMultiValue(remoteConfig.fundsBaseUrl,signatureMap,String.class);
        jsonData = ThreeDES.decrypt(jsonData,Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
        return jsonData;
    }
}
