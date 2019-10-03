package com.borrow.manage.vo;

/**
 * Created by wxn on 2019-10-01
 */
public class OverdueReduceAddReq extends BaseReq {

    private String repayId;

    private String punishAmount;
    private String fineAmount;

    public String getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(String punishAmount) {
        this.punishAmount = punishAmount;
    }

    public String getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }
}
