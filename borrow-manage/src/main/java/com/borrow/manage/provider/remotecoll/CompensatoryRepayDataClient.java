package com.borrow.manage.provider.remotecoll;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.config.Properties;
import com.borrow.manage.config.RemoteConfig;
import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.exception.RemoteException;
import com.borrow.manage.model.XMap;
import com.borrow.manage.provider.RestHttpClientService;
import com.borrow.manage.utils.ThreeDES;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by wxn on 2018/11/24
 */

@Component("compensatoryRepayDataClient")
public class CompensatoryRepayDataClient extends  DataClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RemoteConfig remoteConfig;

    @Autowired
    RestHttpClientService commonRestTempate;


    public String getData(XMap param){
        String jsonData = "";
        try {
            param.put(PlatformConstant.FundsParam.REQ_NO, UUIDProvider.uuid());
            param.put(PlatformConstant.FundsParam.CONTROL,PlatformConstant.FundsMethod.COMPENSATORY_REPAY_REQUEST);
            param.put(PlatformConstant.FundsParam.REQ_TIME, Utility.dateStr());
            String reqParam = JSON.toJSONString(param);
            logger.info("compensatory_req:url={},params={}",remoteConfig.fundsBaseUrl,reqParam);
            jsonData = doSendRequest(reqParam);
            logger.info("compensatory_res:result={}",jsonData);

        }catch (Exception e) {
            logger.error("代偿接口异常",e);
            throw new RemoteException(ExceptionCode.COMPENSATORY_REPAY_ERROR);
        }
        return jsonData;
    }

    @Override
    protected String getUrlType() {
        return DataClientEnum.COMPENSATORY_REPAY_REQUEST.getUrlType();
    }
}
