package com.borrow.manage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wxn on 2018/11/21
 */
@Configuration
public class RemoteConfig {

    @Value("${funds.base.url}")
    public String fundsBaseUrl;

    @Value("${funds.flag}")
    public int fundsFlag = 0;



}
