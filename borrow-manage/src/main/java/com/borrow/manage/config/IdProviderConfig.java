package com.borrow.manage.config;


import com.borrow.manage.utils.id.IdProvider;
import com.borrow.manage.utils.id.MachineIdProvider;
import com.borrow.manage.utils.id.impl.IdProvidermpl;
import com.borrow.manage.utils.id.impl.IpConfigurableMachineIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by wxn on 2018/7/5
 */
@Configuration
public class IdProviderConfig {

    @Value("${idProvider.ips}")
    private String idprovider_ips;


    @Bean
    public MachineIdProvider getMachineIdProvider(){
        IpConfigurableMachineIdProvider provider = new IpConfigurableMachineIdProvider();
        provider.setIps(idprovider_ips);
        provider.init();
        return provider;
    }

    @Bean
    public IdProvider getIdProvider(){
        /**
         * 目前方式可供使用34年
         * genMethod由大变小会导致id比修改之前生成的id小，间接导致数据库索引重做，建议使用默认值
         * version>0时会导致生成的long型数字为负数，不建议修改，推荐使用默认值0
         * idType由大变小会导致id比修改之前生成的id小，间接导致数据库索引重做，建议使用默认值
         */
        IdProvidermpl provider = new IdProvidermpl();
        provider.setMachineIdProvider(getMachineIdProvider());
        provider.init();
        return provider;
    }
}
