package com.risk.app.config;

import com.csyy.core.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by wangxindong on 18/5/16
 */
@Configuration
public class Properties {

    public static String BIGDATA_SYSID;

    public static String CEPH_ACCESS_KEY;

    public static String CEPH_SECRET_KEY;

    public static String CEPH_ENDPOINT;



    @Value("b${bigdata.sysId}")
    public void setBigdataSysid(String bigdataSysid) {
        BIGDATA_SYSID = bigdataSysid;
    }

    @Value("${ceph.access.key}")
    public void setCephAccessKey(String cephAccessKey) {
        CEPH_ACCESS_KEY = cephAccessKey;
    }

    @Value("${ceph.secret.key}")
    public void setCephSecretKey(String cephSecretKey) {
        CEPH_SECRET_KEY = cephSecretKey;
    }

    @Value("${ceph.endpoint}")
    public void setCephEndpoint(String cephEndpoint) {
        CEPH_ENDPOINT = cephEndpoint;
    }

}
