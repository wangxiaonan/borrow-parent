package com.borrow.manage.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2018/9/12
 */
public class OrderAuditVo {

    private List<String> auditkeys = new ArrayList<>();

    public List<String> getAuditkeys() {
        return auditkeys;
    }

    public void setAuditkeys(List<String> auditkeys) {
        this.auditkeys = auditkeys;
    }
}
