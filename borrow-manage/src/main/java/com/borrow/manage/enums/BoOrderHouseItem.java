package com.borrow.manage.enums;

/**
 * Created by wxn
 * Date:2019/5/5
 * Time:6:21
 */
public enum  BoOrderHouseItem {

    HOUSE_NAME("HOUSE_NAME","房产权利人"),
    HOUSE_PART("HOUSE_PART","共有人情况"),
    HOUSE_NUM("HOUSE_NUM","房产证号"),
    HOUSE_AREA("HOUSE_AREA","房产面积"),
    HOUSE_ATTR("HOUSE_ATTR","房产性质"),
    HOUSE_ADDRESS("HOUSE_ADDRESS","房产地址"),
    HOUSE_DATE("HOUSE_DATE","登记时间"),
    HOUSE_PRICE("HOUSE_PRICE","评估价格"),
    HOUSE_IDCARD_PIC_URL("HOUSE_IDCARD_PIC_URL","身份证"),
    HOUSE_PIC_URL("HOUSE_PIC_URL","房产证"),
    HOUSE_AUTHORITY_CARD_PIC_URL("HOUSE_AUTHORITY_CARD_PIC_URL","他项权证"),
    HOUSE_GUARANTEE_PIC_URL("HOUSE_GUARANTEE_PIC_URL","担保函"),
    HOUSE_LETTER_COMMITMENT_PIC_URL("HOUSE_LETTER_COMMITMENT_PIC_URL","信批承诺函"),
    HOUSE_AUTH_OTHER_PIC_URL("HOUSE_AUTH_OTHER_PIC_URL","其他补充材料");

    BoOrderHouseItem(String itemKey,String itemDesc){
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
        for (BoOrderHouseItem type : BoOrderHouseItem.values()) {
            if (type.getItemKey().equals(itemKey)) {
                return type.getItemDesc();
            }
        }
        return null;
    }

}
