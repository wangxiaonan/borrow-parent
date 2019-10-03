package com.borrow.manage.vo;

import com.borrow.manage.model.dto.BoOverdueReduceRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2019-10-01
 */
public class OverdueReduceRes {

    //总违约金
   private String totalPunishAmount;
   //总罚息
   private String totalFineAmount;

   //减免违约金
   private String reducePunishAmount;
   //减免罚息
   private String reduceFineAmount;
    //实际违约金
   private String actualPunishAmount;
   //实际罚息
   private String actualFineAmount;

    //违约记录
   private List<BoOverdueReduceRecord> overdueReduceRecords = new ArrayList<>();

    public String getTotalPunishAmount() {
        return totalPunishAmount;
    }

    public void setTotalPunishAmount(String totalPunishAmount) {
        this.totalPunishAmount = totalPunishAmount;
    }

    public String getTotalFineAmount() {
        return totalFineAmount;
    }

    public void setTotalFineAmount(String totalFineAmount) {
        this.totalFineAmount = totalFineAmount;
    }

    public String getActualFineAmount() {
        return actualFineAmount;
    }

    public void setActualFineAmount(String actualFineAmount) {
        this.actualFineAmount = actualFineAmount;
    }

    public String getReducePunishAmount() {
        return reducePunishAmount;
    }

    public void setReducePunishAmount(String reducePunishAmount) {
        this.reducePunishAmount = reducePunishAmount;
    }

    public String getReduceFineAmount() {
        return reduceFineAmount;
    }

    public void setReduceFineAmount(String reduceFineAmount) {
        this.reduceFineAmount = reduceFineAmount;
    }

    public String getActualPunishAmount() {
        return actualPunishAmount;
    }

    public void setActualPunishAmount(String actualPunishAmount) {
        this.actualPunishAmount = actualPunishAmount;
    }

    public List<BoOverdueReduceRecord> getOverdueReduceRecords() {
        return overdueReduceRecords;
    }

    public void setOverdueReduceRecords(List<BoOverdueReduceRecord> overdueReduceRecords) {
        this.overdueReduceRecords = overdueReduceRecords;
    }
}
