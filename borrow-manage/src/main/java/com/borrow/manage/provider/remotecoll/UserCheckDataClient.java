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
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by wxn on 2018/11/21
 */
@Component("userCheckDataClient")
public class UserCheckDataClient extends DataClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RemoteConfig remoteConfig;

    @Autowired
    RestHttpClientService commonRestTempate;

    @Override
    public String getData(XMap map) {
        String jsonData = "";
        try {
            HashMap param = new HashMap();
            param.put(PlatformConstant.FundsParam.IDCARD,map.getString(PlatformConstant.FundsParam.IDCARD));
            param.put(PlatformConstant.FundsParam.REQ_NO, UUIDProvider.uuid());
            param.put(PlatformConstant.FundsParam.CONTROL,PlatformConstant.FundsMethod.LOAN_USER_QUERY_REQUEST);
            param.put(PlatformConstant.FundsParam.REQ_TIME, Utility.dateStr());

            String reqParam = JSON.toJSONString(param);
            logger.info("UserCheckData_req:url={},params={}",remoteConfig.fundsBaseUrl,reqParam);

            HashMap signatureMap = new HashMap();
            reqParam = ThreeDES.encrypt(reqParam, Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
            signatureMap.put(PlatformConstant.FundsParam.SIGNATURE,reqParam);
            jsonData = commonRestTempate.postForObjectMultiValue(remoteConfig.fundsBaseUrl,signatureMap,String.class);
            jsonData = ThreeDES.decrypt(jsonData,Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
            logger.info("UserCheckData_res:result={}",jsonData);

        }catch (Exception e) {
            logger.error("用户存管身份校验异常",e);
            throw new RemoteException(ExceptionCode.USER_CHECK_IDENTITY_ERROR);
        }
        return jsonData;
    }

    @Override
    protected String getUrlType() {
        return DataClientEnum.USER_CHECK_DATA.getUrlType();
    }
}
