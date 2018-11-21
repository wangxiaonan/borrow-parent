package com.borrow.manage.provider.remotecoll;

import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.factory.DataClientFactory;
import com.borrow.manage.model.XMap;
import com.borrow.manage.provider.RemoteDataCollector;
import com.borrow.manage.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wxn on 2018/11/21
 */
@Component
public class RemoteDataCollectorService extends RemoteDataCollector {

    @Autowired
    DataClientFactory dataClientFactory;

    @Override
    public ResponseResult<XMap> collect(XMap xMap) {

        DataClient dataClient = dataClientFactory
                .getDataClient(xMap.getString(DataClientEnum.USER_CHECK_DATA.getUrlType()));
        String res = dataClient.getData(xMap);
        return dataClient.analyze(res);
    }
}
