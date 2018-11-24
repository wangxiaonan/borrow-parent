package com.borrow.manage.service;

import com.borrow.manage.model.XMap;
import com.borrow.manage.vo.ResponseResult;

import java.util.Map;

/**
 * Created by wxn on 2018/11/24
 */
public interface FundsNotifyService {


    ResponseResult doNotify(XMap xMap);
}
