package com.borrow.manage.enums;

/**
 * Created by wxn
 * Date:2019/5/5
 * Time:6:21
 */
public enum BoOrderCarItem {


    AUTH_IDARD("二代身份证","AUTH_IDCARD"),
    AUTH_VEHICLE_LICENSE("行驶证","VEHICLE_LICENSE"),
    POLLING_LICENSE("登记证","POLLING_LICENSE"),
    CAR_MILEAGE("车辆外观照片","CAR_MILEAGE"),
    INSURANCE_POLICY("担保函","INSURANCE_POLICY"),
    LETTER_COMMITMENT("信批承诺函","LETTER_COMMITMENT"),
    AUTH_OTHER("其他补充材料","AUTH_OTHER");

    BoOrderCarItem(String itemKey, String itemDesc){
        this.itemKey = itemKey;
        this.itemDesc = itemDesc;
    }

    private String itemKey;
    private String itemDesc;

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public static String gettemDesc(String itemKey) {
        for (BoOrderCarItem type : BoOrderCarItem.values()) {
            if (type.getItemKey().equals(itemKey)) {
                return type.getItemDesc();
            }
        }
        return null;
    }

}
