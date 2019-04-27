package com.borrow.manage.provider.remotecoll;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.PlatformConstant;
import com.borrow.manage.exception.RemoteException;
import com.borrow.manage.model.XMap;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wxn on 2019-04-27
 */
@Component("loanerGenerateRepayRequestClient")
public class LoanerGenerateRepayRequestClient extends DataClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getData(XMap param) {

        String jsonData = "";
        try {
            param.put(PlatformConstant.FundsParam.REQ_NO, UUIDProvider.uuid());
            param.put(PlatformConstant.FundsParam.CONTROL,PlatformConstant.FundsMethod.LOANER_GENERATE_REPAY_REQUEST);
            param.put(PlatformConstant.FundsParam.REQ_TIME, Utility.dateStr());
            String reqParam = JSON.toJSONString(param);
            logger.info("LoanerGenerateRepay_req:url={},params={}",remoteConfig.fundsBaseUrl,reqParam);
            jsonData = doSendRequest(reqParam);
            logger.info("LoanerGenerateRepay_res:result={}",jsonData);

        }catch (Exception e) {
            logger.error("理财还款计划接口异常",e);
            throw new RemoteException(ExceptionCode.LOANER_EARLY_REPAY_ERROR);
        }
        return jsonData;
    }

    @Override
    protected String getUrlType() {
        return DataClientEnum.LOANER_GENERATE_REPAY_REQUEST.getUrlType();
    }
}
