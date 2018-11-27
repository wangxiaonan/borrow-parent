package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/11/27
 */
public enum  BoOrderItemEnum {

    BO_SOURCE("BO_SOURCE","借款用途");

    BoOrderItemEnum(String itemKey,String itemDesc){
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
        for (BoOrderItemEnum type : BoOrderItemEnum.values()) {
            if (type.getItemKey().equals(itemKey)) {
                return type.getItemDesc();
            }
        }
        return null;
    }
}
