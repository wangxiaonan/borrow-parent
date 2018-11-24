package com.borrow.manage.service;

import com.borrow.manage.enums.FundsNotifyEnum;
import com.borrow.manage.factory.FundsNotifyFactory;
import com.borrow.manage.model.XMap;
import com.borrow.manage.vo.ResponseResult;

/**
 * Created by wxn on 2018/11/24
 */

public class FundsNotifyContext {

    private  FundsNotifyService notifyService;

    public FundsNotifyContext (FundsNotifyEnum fundsNotifyEnum) {
        this.notifyService = FundsNotifyFactory.getFundsNotify(fundsNotifyEnum);
    }


    public ResponseResult fundsNotify(XMap xMap){
        return notifyService.doNotify(xMap);
    }

}
