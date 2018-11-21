package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/15
 */
public enum DataClientEnum {

    USER_CHECK_DATA("USER_CHECK_DATA","用户存管开户校验");

    DataClientEnum(String urlType, String desc) {
        this.urlType = urlType;
        this.desc = desc;
    }
    private String urlType;
    private String desc;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static DataClientEnum getEnum(String urlType) {
        for (DataClientEnum dataClientEnum : DataClientEnum.values()) {
            if (dataClientEnum.getUrlType().equals(urlType)) {
                return dataClientEnum;
            }
        }
        return null;
    }
}
