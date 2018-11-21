package com.borrow.manage.dao.mapper;

import com.borrow.manage.model.dto.BoOrderAudit;
import com.borrow.manage.model.dto.BoOrderAuditExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoOrderAuditMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int countByExample(BoOrderAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int deleteByExample(BoOrderAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int insert(BoOrderAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int insertSelective(BoOrderAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    List<BoOrderAudit> selectByExample(BoOrderAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    BoOrderAudit selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BoOrderAudit record, @Param("example") BoOrderAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BoOrderAudit record, @Param("example") BoOrderAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BoOrderAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bo_order_audit
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BoOrderAudit record);
}