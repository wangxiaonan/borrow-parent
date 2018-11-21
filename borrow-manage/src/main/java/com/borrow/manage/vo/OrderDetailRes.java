package com.borrow.manage.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2018/9/16
 */
public class OrderDetailRes {

    private String industry;
    private String workNature;
    private String userEarns;
    private String boPaySource;
    private String creditDec;

    private List<String> auditkeys = new ArrayList<>();

    public String getCreditDec() {
        return creditDec;
    }

    public void setCreditDec(String creditDec) {
        this.creditDec = creditDec;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public String getUserEarns() {
        return userEarns;
    }

    public void setUserEarns(String userEarns) {
        this.userEarns = userEarns;
    }

    public String getBoPaySource() {
        return boPaySource;
    }

    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource;
    }

    public List<String> getAuditkeys() {
        return auditkeys;
    }

    public void setAuditkeys(List<String> auditkeys) {
        this.auditkeys = auditkeys;
    }
}
