package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoOverdueReduceRecord;
import com.borrow.manage.vo.RepayReduceListReq;
import com.borrow.manage.vo.RepayReduceListRes;

import java.util.List;

/**
 * Created by wxn on 2019-10-01
 */
public interface BoOverdueReduceRecordDao {

    List<BoOverdueReduceRecord> selInfoByRepaymentId(String repaymentId);

    void insertOverdueReduceRecord(BoOverdueReduceRecord reduceRecord);

    List<RepayReduceListRes> selReduceListWhere(RepayReduceListReq reduceListReq);

}
