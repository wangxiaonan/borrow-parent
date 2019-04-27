package com.borrow.manage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * Created by wxn on 2018/9/15
 */
@Configuration
public class Properties {

    public static String THREE_DES_BASE64_KEY;
    public static String THREE_DES_IV;

    public static String THREE_DES_ALGORITHM;
    public static String IMAGE_URL;

    public static String PROJECT_URL;

    @Value("${three_des_base64_key}")
    public void setCephAccessKey(String threeDesBase64Key) {
        THREE_DES_BASE64_KEY = threeDesBase64Key;
    }

    @Value("${three_des_iv}")
    public void setCephSecretKey(String threeDesIv) {
        THREE_DES_IV = threeDesIv;
    }

    @Value("${three_des_algorithm}")
    public void setCephEndpoint(String threeDesAlgorithm) {
        THREE_DES_ALGORITHM = threeDesAlgorithm;
    }
    @Value("${image.url}")
    public void setImageUrl(String imageUrl) {
        IMAGE_URL = imageUrl;
    }
    @Value("${project.url}")
    public  void setProjectUrl(String projectUrl) {
        PROJECT_URL = projectUrl;
    }
}
