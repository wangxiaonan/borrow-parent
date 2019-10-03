package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOverdueReduceRecord;

import java.util.List;

/**
 * Created by wxn on 2019-10-01
 */
public interface BoOverdueReduceRecordDao {

    List<BoOverdueReduceRecord> selInfoByRepaymentId(String repaymentId);

    void insertOverdueReduceRecord(BoOverdueReduceRecord reduceRecord);
}
