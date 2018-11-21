package com.borrow.manage.provider;

/**
 * Created by wxn on 2018/11/21
 */
public interface DataCollector<T,K> {

    T collect(K k);

}
