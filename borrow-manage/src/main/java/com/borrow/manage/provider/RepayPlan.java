package com.borrow.manage.provider;

import java.util.Map;

/**
 * Created by wxn on 2018/9/19
 */
public interface RepayPlan<T,K> {


    T planCal(K k);


}
