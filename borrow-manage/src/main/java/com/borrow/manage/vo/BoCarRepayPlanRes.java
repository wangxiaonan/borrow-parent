package com.borrow.manage.vo;

import java.util.List;

/**
 * Created by wxn on 2018/9/19
 */
public class BoCarRepayPlanRes {


    private String boPrice;
    private String boExpect;
    private String gpsCost;
    private String earlyServiceRate;
    private String boFinishPrice;
    private String earlyServiceCost;

    public String getEarlyServiceCost() {
        return earlyServiceCost;
    }

    public void setEarlyServiceCost(String earlyServiceCost) {
        this.earlyServiceCost = earlyServiceCost;
    }
    //    private String monthServiceRate;
//    private String monthAccrualRate;
//    private String pPayType;

    private List<CarRepayPlanVo> repayPlans;


    public String getBoPrice() {
        return boPrice;
    }

    public void setBoPrice(String boPrice) {
        this.boPrice = boPrice;
    }

    public String getBoExpect() {
        return boExpect;
    }

    public void setBoExpect(String boExpect) {
        this.boExpect = boExpect;
    }

    public String getGpsCost() {
        return gpsCost;
    }

    public void setGpsCost(String gpsCost) {
        this.gpsCost = gpsCost;
    }

    public String getEarlyServiceRate() {
        return earlyServiceRate;
    }

    public void setEarlyServiceRate(String earlyServiceRate) {
        this.earlyServiceRate = earlyServiceRate;
    }

    public String getBoFinishPrice() {
        return boFinishPrice;
    }

    public void setBoFinishPrice(String boFinishPrice) {
        this.boFinishPrice = boFinishPrice;
    }



    public List<CarRepayPlanVo> getRepayPlans() {
        return repayPlans;
    }

    public void setRepayPlans(List<CarRepayPlanVo> repayPlans) {
        this.repayPlans = repayPlans;
    }
}
