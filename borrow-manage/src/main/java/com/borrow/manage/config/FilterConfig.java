package com.borrow.manage.config;

import com.borrow.manage.filter.PermissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created by wangxindong on 18/7/5
 */
@Configuration
public class FilterConfig {

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }


    @Bean
    public PermissionFilter permissionFilter(){
        final PermissionFilter permissionFilter = new PermissionFilter();
        return permissionFilter;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(permissionFilter());
        registration.addUrlPatterns("/borrow/*");
        registration.setName("permissionFilter");
        registration.setOrder(1);
        return registration;
    }

}
