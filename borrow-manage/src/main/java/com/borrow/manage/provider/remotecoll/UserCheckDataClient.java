package com.borrow.manage.provider.remotecoll;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.config.RemoteConfig;
import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.exception.RemoteException;
import com.borrow.manage.model.XMap;
import com.borrow.manage.provider.RestHttpClientService;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by wxn on 2018/11/21
 */
@Component
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
            param.put(PlatformConstant.AssertParam.IDCARD,map.getString(PlatformConstant.AssertParam.IDCARD));
            param.put(PlatformConstant.AssertParam.REQ_NO, UUIDProvider.uuid());
            logger.info("UserCheckData_req:url={},params={}",remoteConfig.assetsUserUrl,JSON.toJSONString(param));
            jsonData = commonRestTempate.postForObjectJson(remoteConfig.assetsUserUrl,param,String.class);
            logger.info("UserCheckData_res:result={}",jsonData);

        }catch (Exception e) {
            logger.error("用户存管身份校验异常",e);
            throw new RemoteException(ExceptionCode.USER_CHECK_IDENTITY_ERROR);
        }
        return jsonData;
    }
    @Override
    ResponseResult<XMap> analyze(String res) {

        XMap xMap = JSON.parseObject(res,XMap.class);
        String status = xMap.getString(PlatformConstant.AssertParam.STATUS);
        String msg = xMap.getString(PlatformConstant.AssertParam.MSG);
        if (PlatformConstant.AssertParam.success.equals(status)) {
            return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),xMap);
        }else {
            return ResponseResult.error(ExceptionCode.REMOTE_ERROR.getErrorCode(),msg);
        }
    }

    @Override
    protected String getUrlType() {
        return DataClientEnum.USER_CHECK_DATA.getUrlType();
    }
}
