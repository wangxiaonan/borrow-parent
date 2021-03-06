package com.borrow.manage.dao.mapper;

import com.borrow.manage.model.dto.BoOverdueReduceRecord;
import com.borrow.manage.model.dto.BoOverdueReduceRecordExample;
import java.util.List;

import com.borrow.manage.vo.OrderRepayListReq;
import com.borrow.manage.vo.OrderRepayListRes;
import com.borrow.manage.vo.RepayReduceListReq;
import com.borrow.manage.vo.RepayReduceListRes;
import org.apache.ibatis.annotations.Param;

public interface BoOverdueReduceRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int countByExample(BoOverdueReduceRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int deleteByExample(BoOverdueReduceRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int insert(BoOverdueReduceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int insertSelective(BoOverdueReduceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    List<BoOverdueReduceRecord> selectByExample(BoOverdueReduceRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    BoOverdueReduceRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BoOverdueReduceRecord record, @Param("example") BoOverdueReduceRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BoOverdueReduceRecord record, @Param("example") BoOverdueReduceRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BoOverdueReduceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_overdue_reduce_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BoOverdueReduceRecord record);

    /**
     * 减免记录查询
     * @param repayReduceListReq
     * @return
     */
    List<RepayReduceListRes> selReduceList(RepayReduceListReq repayReduceListReq);


}
