package com.borrow.manage.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2019-10-01
 */
public class OverdueReduceRes {


   private String totalPunishAmount;
   private String totalFineAmount;


   private String punishAmount;

   private String fineAmount;

   private String actualpunishAmount;

   private String actualFineAmount;


   private List<BoOverduerReduceRecord> overdueReduceRecords = new ArrayList<>();

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

    public String getActualpunishAmount() {
        return actualpunishAmount;
    }

    public void setActualpunishAmount(String actualpunishAmount) {
        this.actualpunishAmount = actualpunishAmount;
    }

    public String getActualFineAmount() {
        return actualFineAmount;
    }

    public void setActualFineAmount(String actualFineAmount) {
        this.actualFineAmount = actualFineAmount;
    }

}
