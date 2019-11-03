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
import com.borrow.manage.utils.Utility;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wxn on 2018/11/24
 */

@Component("repayQueryHandlerRequestDataClient")
public class RepayQueryHandlerRequestDataClient extends  DataClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RemoteConfig remoteConfig;

    @Autowired
    RestHttpClientService commonRestTempate;


    public String getData(XMap param){
        String jsonData = "";
        try {
            param.put(PlatformConstant.FundsParam.REQ_NO, UUIDProvider.uuid());
            param.put(PlatformConstant.FundsParam.CONTROL,PlatformConstant.FundsMethod.REPAY_QUERY_HANDLER);
            param.put(PlatformConstant.FundsParam.REQ_TIME, Utility.dateStr());
            String reqParam = JSON.toJSONString(param);
            logger.info("repayQueryHandlerRequestData_req:url={},params={}",remoteConfig.fundsBaseUrl,reqParam);
            jsonData = doSendRequest(reqParam);
            logger.info("repayQueryHandlerRequestData_res:result={}",jsonData);

        }catch (Exception e) {
            logger.error("回款计划查询接口异常",e);
            throw new RemoteException(ExceptionCode.REPAY_QUERY_HANDLER_REQUEST_ERROR);
        }
        return jsonData;
    }

    ResponseResult<XMap> analyze(String res) {
        XMap xMap = JSON.parseObject(res,XMap.class);
        String msg = xMap.getString(PlatformConstant.FundsParam.MSG);
        String code = xMap.getString(PlatformConstant.FundsParam.CODE);
        if ("200".equals(code) || PlatformConstant.FundsParam.SUCCESS.equals(code)) {
            return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),xMap);
        }else {
            return ResponseResult.error(ExceptionCode.REMOTE_ERROR.getErrorCode(),msg);
        }
    }


    @Override
    protected String getUrlType() {
        return DataClientEnum.PROJECT_UPDATE_REQUEST.getUrlType();
    }
}
