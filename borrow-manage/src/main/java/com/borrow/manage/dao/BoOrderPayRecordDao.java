package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOrderPayRecord;

import java.util.List;

/**
 * Created by wxn on 2018/9/26
 */
public interface BoOrderPayRecordDao {


     void insertPayOrder(BoOrderPayRecord boOrderPayRecord);

     List<BoOrderPayRecord> selInfoByOrderId(long orderId);

}
