package com.borrow.manage.provider;

import com.borrow.manage.model.XMap;
import com.borrow.manage.vo.ResponseResult;

import java.util.Map;

/**
 * Created by wxn on 2018/11/21
 */
public abstract class RemoteDataCollector implements DataCollector<ResponseResult<XMap>,XMap> {



    @Override
    abstract public ResponseResult<XMap> collect(XMap map);
}
