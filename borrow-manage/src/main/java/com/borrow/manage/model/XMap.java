package com.borrow.manage.model;

import java.util.HashMap;

/**
 * Created by wxn on 2018/11/21
 */
public class XMap extends HashMap {


    public String getString(Object key) {
        Object o = super.get(key);
        return null == o ? null : o.toString();
    }
}
