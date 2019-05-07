package com.borrow.manage.vo;

import java.util.Map;

/**
 * Created by wxn on 2018/11/27
 */
public class BoOrderItemVo {

    private String boSource;

    private Map<String, String> imageUrl;


    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBoSource() {
        return boSource;
    }

    public void setBoSource(String boSource) {
        this.boSource = boSource;
    }
}
