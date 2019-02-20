package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/9/15
 */
public enum DataClientEnum {

    URL_TYPE("URL_TYPE","URL类型"),
    USER_CHECK_DATA("USER_CHECK_DATA","用户存管开户校验"),
    ORDER_MAKE_RAISE("ORDER_MAKE_RAISE","理财筹标接口"),
    ORDER_TRANSFER_FUND("ORDER_TRANSFER_FUND","理财资金划拨接口"),
    COMPENSATORY_REPAY_REQUEST("COMPENSATORY_REPAY_REQUEST","代偿还款接口"),
    LOANER_OVERDUE_REPAY_REQUEST("LOANER_OVERDUE_REPAY_REQUEST","逾期还款");

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
