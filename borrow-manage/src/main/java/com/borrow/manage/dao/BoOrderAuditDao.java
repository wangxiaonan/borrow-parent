package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOrderAudit;

import java.util.List;

/**
 * Created by wxn on 2018/9/15
 */
public interface BoOrderAuditDao {

    void insertOrderAudit(BoOrderAudit boOrderAudit);

    List<BoOrderAudit> selByOrderId(Long orderId);

    void delByOrderId(Long orderId);
}
