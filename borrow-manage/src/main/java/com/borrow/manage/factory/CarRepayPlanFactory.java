package com.borrow.manage.factory;

import com.borrow.manage.enums.ProductEnum;
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

    public AbstractCarRepayPlan getCarRepayPlan(ProductEnum productEnum){

        if (productEnum == null)
            return carDefaultRepayPlan;
        switch (productEnum){
            case CAR_LOAN_ONE:
                return this.carDefaultRepayPlan;
            case CAR_LOAN_TWO:
                return this.carDefaultRepayPlan;
        }

        return carDefaultRepayPlan;
    }
}
