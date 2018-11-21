package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOrderCost;

import java.util.List;

/**
 * Created by wxn on 2018/9/20
 */
public interface BoOrderCostDao {

    void insertOrderCost(BoOrderCost boOrderCost);

    List<BoOrderCost> selByOrderId(long  orderId);

}
