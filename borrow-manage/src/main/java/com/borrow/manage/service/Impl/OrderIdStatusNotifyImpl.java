package com.borrow.manage.service.Impl;

import com.borrow.manage.model.XMap;
import com.borrow.manage.service.FundsNotifyService;
import com.borrow.manage.vo.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * Created by wxn on 2018/11/24
 */

@Service("orderIdStatusNotify")
public class OrderIdStatusNotifyImpl implements FundsNotifyService {


    @Override
    public ResponseResult doNotify(XMap xMap) {
        return null;
    }
}
