package com.borrow.manage.utils;

import java.util.UUID;

/**
 * Created by wxn on 2018/9/14
 */
public class UUIDProvider {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
