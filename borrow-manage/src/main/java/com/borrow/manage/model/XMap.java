package com.borrow.manage.model;

import java.util.HashMap;

/**
 * Created by wxn on 2018/11/21
 */
public class XMap<K,V>  extends HashMap<K,V> {


    public String getString(Object key) {
        Object o = super.get(key);
        return null == o ? null : o.toString();
    }
}
