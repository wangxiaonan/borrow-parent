package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.annotation.SignatureIncrease;
import com.borrow.manage.config.Properties;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.FundsNotifyEnum;
import com.borrow.manage.model.XMap;
import com.borrow.manage.service.FundsNotifyContext;
import com.borrow.manage.utils.ThreeDES;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;


/**
 * Created by wxn on 2018/11/22
 */
@RestController
public class FundsCallbackApi {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TaskExecutor notifyTaskExecutor;

    @RequestMapping(value = "/funds/notify", method = RequestMethod.POST)
    @SignatureIncrease
    public String fundsNotify(String Signature) {
        logger.info("====>fundsNotify():req={}", Signature);
        XMap paramMap = JSON.parseObject(Signature, XMap.class);
        String methodName = paramMap.getString(FundsNotifyEnum.CONTROL.getMethodType());
        FundsNotifyEnum notifyEnum = FundsNotifyEnum.getEnum(methodName);
        if (notifyEnum == null ) {
             String jsonData = JSON.toJSONString(ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                     ,ExceptionCode.PARAM_ERROR.getErrorMessage()));
             jsonData = ThreeDES.encrypt(jsonData, Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
             return jsonData;
        }
        notifyTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FundsNotifyContext notifyContext = new FundsNotifyContext(notifyEnum);
                ResponseResult res = notifyContext.fundsNotify(paramMap);
            }
        });
        ResponseResult res = ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
        String jsonData = JSON.toJSONString(res);
        logger.info("<====fundsNotify():res={}",jsonData);
        jsonData = ThreeDES.encrypt(JSON.toJSONString(res), Properties.THREE_DES_BASE64_KEY,Properties.THREE_DES_IV,Properties.THREE_DES_ALGORITHM);
        return jsonData;
    }


}
