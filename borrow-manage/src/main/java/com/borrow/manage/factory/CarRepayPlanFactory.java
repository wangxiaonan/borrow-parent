package com.borrow.manage.factory;

import com.borrow.manage.enums.ProductEnum;
import com.borrow.manage.enums.ProductPayTypeEnum;
import com.borrow.manage.provider.AbstractCarRepayPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.borrow.manage.enums.ProductEnum.*;

/**
 * Created by wxn on 2018/9/19
 */
@Component
public class CarRepayPlanFactory {

    @Autowired
    AbstractCarRepayPlan carDefaultRepayPlan;
    @Autowired
    AbstractCarRepayPlan principalCarRepayPlan;
    @Autowired
    AbstractCarRepayPlan equalPrincipalCarRepayPlan;

    public AbstractCarRepayPlan getCarRepayPlan(ProductPayTypeEnum payTypeEnum){

        if (payTypeEnum == null)
            return carDefaultRepayPlan;
        switch (payTypeEnum){
            case PAY_TYPE_ONE:
                return this.carDefaultRepayPlan;
            case PAY_TYPE_TWO:
                return this.principalCarRepayPlan;
            case PAY_TYPE_THREE:
                return this.equalPrincipalCarRepayPlan;
        }

        return carDefaultRepayPlan;
    }
}
