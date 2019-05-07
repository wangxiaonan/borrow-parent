package com.borrow.manage.enums;

/**
 * Created by wxn
 * Date:2019/5/5
 * Time:6:21
 */
public enum BoOrderCarItem {

    AUTH_IDARD("AUTH_IDCARD","二代身份证"),
    AUTH_VEHICLE_LICENSE("VEHICLE_LICENSE","行驶证"),
    POLLING_LICENSE("POLLING_LICENSE","登记证"),
    CAR_MILEAGE("CAR_MILEAGE","车辆外观照片"),
    INSURANCE_POLICY("INSURANCE_POLICY","担保函"),
    LETTER_COMMITMENT("LETTER_COMMITMENT","信批承诺函"),
    AUTH_OTHER("AUTH_OTHER","其他补充材料");

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
