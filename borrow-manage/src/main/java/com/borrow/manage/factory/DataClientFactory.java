package com.borrow.manage.factory;

import com.borrow.manage.enums.DataClientEnum;
import com.borrow.manage.provider.remotecoll.CompensatoryRepayDataClient;
import com.borrow.manage.provider.remotecoll.DataClient;
import com.borrow.manage.provider.remotecoll.LoanerEarlyRepayRequestClient;
import com.borrow.manage.provider.remotecoll.LoanerGenerateRepayRequestClient;
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
    @Autowired
    DataClient overdueRepayRequestClient;
    @Autowired
    DataClient loanerEarlyRepayRequestClient;
    @Autowired
    DataClient loanerGenerateRepayRequestClient;

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
            case LOANER_OVERDUE_REPAY_REQUEST:
                return this.overdueRepayRequestClient;
            case LOANER_EARLY_REPAY_REQUEST:
                return this.loanerEarlyRepayRequestClient;
            case LOANER_GENERATE_REPAY_REQUEST:
                return this.loanerGenerateRepayRequestClient;

        }
        return null;
    }
}
