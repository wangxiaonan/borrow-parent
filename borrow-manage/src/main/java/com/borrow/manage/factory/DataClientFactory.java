package com.borrow.manage.factory;

import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.provider.remotecoll.DataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wxn on 2018/9/19
 */
@Component
public class DataClientFactory {

    @Autowired
    DataClient userCheckDataClient;
    @Autowired
    DataClient orderMakeRaiseDataClient;
    @Autowired
    DataClient orderTransferFundDataClient;
    @Autowired
    DataClient compensatoryRepayDataClient;

    public DataClient  getDataClient(String urlType){
        DataClientEnum dataClientEnum = DataClientEnum.getEnum(urlType);
        switch (dataClientEnum){
            case USER_CHECK_DATA:
                return this.userCheckDataClient;
            case ORDER_MAKE_RAISE:
                return this.orderMakeRaiseDataClient;
            case ORDER_TRANSFER_FUND:
                return this.orderTransferFundDataClient;
            case COMPENSATORY_REPAY_REQUEST:
                return this.compensatoryRepayDataClient;

        }
        return null;
    }
}
