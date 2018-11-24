package com.borrow.manage.factory;

import com.borrow.manage.config.SpringContextHolder;
import com.borrow.manage.enums.FundsNotifyEnum;
import com.borrow.manage.service.FundsNotifyService;

/**
 * Created by wxn on 2018/11/24
 */
public class FundsNotifyFactory {


    public static FundsNotifyService getFundsNotify(FundsNotifyEnum notifyEnum){
        switch (notifyEnum) {
            case BORROW_ORDERID_AUDIT_HANDLE:
                return SpringContextHolder.getBean("orderIdAuditNotify",FundsNotifyService.class);
            case BORROW_ORDERID_STATUS_HANDLE:
                return SpringContextHolder.getBean("orderIdStatusNotify",FundsNotifyService.class);

            default:
                return null;
        }
    }
}
