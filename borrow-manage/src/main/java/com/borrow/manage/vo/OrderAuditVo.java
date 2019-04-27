package com.borrow.manage.vo;

import java.util.*;

/**
 * Created by wxn on 2018/9/12
 */
public class OrderAuditVo {

    private Map<String,String> auditkeys = new LinkedHashMap<>();

    public Map<String,String> getAuditkeys() {
        return auditkeys;
    }

    public void setAuditkeys(Map<String,String> auditkeys) {
        this.auditkeys = auditkeys;
    }
}
