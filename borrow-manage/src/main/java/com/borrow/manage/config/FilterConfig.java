package com.risk.app.config;

import com.risk.app.filter.WhiteIpFilter;
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
    public WhiteIpFilter whiteIpFilter(){
        final WhiteIpFilter whiteIpFilter = new WhiteIpFilter();
        return whiteIpFilter;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(whiteIpFilter());
        registration.addUrlPatterns("/dcinapi/*");
        registration.setName("whiteIpFilter");
        registration.setOrder(1);
        return registration;
    }

}
