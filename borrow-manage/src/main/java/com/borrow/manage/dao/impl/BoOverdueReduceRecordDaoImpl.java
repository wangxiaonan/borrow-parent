package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoOverdueReduceRecordDao;
import com.borrow.manage.dao.mapper.BoOverdueReduceRecordMapper;
import com.borrow.manage.model.dto.BoOverdueReduceRecord;
import com.borrow.manage.model.dto.BoOverdueReduceRecordExample;
import com.borrow.manage.vo.RepayReduceListReq;
import com.borrow.manage.vo.RepayReduceListRes;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2019-10-01
 */
@Repository
public class BoOverdueReduceRecordDaoImpl implements BoOverdueReduceRecordDao {

    @Autowired
    BoOverdueReduceRecordMapper boOverdueReduceRecordMapper;

    @Override
    public List<BoOverdueReduceRecord> selInfoByRepaymentId(String repaymentId) {
        BoOverdueReduceRecordExample example = new BoOverdueReduceRecordExample();
        example.createCriteria().andRepaymentIdEqualTo(repaymentId);
        return boOverdueReduceRecordMapper.selectByExample(example);
    }

    @Override
    public void insertOverdueReduceRecord(BoOverdueReduceRecord reduceRecord) {
        boOverdueReduceRecordMapper.insertSelective(reduceRecord);
    }

    @Override
    public List<RepayReduceListRes> selReduceListWhere(RepayReduceListReq reduceListReq) {
        PageHelper.startPage(reduceListReq.getPageNo(), reduceListReq.getPageSize());
        return boOverdueReduceRecordMapper.selReduceList(reduceListReq);
    }
}
