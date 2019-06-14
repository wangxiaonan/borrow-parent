package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOrderItem;

import java.util.List;

/**
 * Created by wxn on 2018/11/27
 */
public interface BoOrderItemDao {


    public void insertItem(BoOrderItem boOrderItem);


    public List<BoOrderItem> selByorderId(long orderId);

    public void updateItemValue(long orderId,String itermKey,BoOrderItem boOrderItem);

    public void deleteItemValue(long orderId,String itermKey);

}
