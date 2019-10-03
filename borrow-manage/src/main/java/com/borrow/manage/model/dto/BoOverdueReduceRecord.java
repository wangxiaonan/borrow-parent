package com.borrow.manage.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BoOverdueReduceRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.disabled
     *
     * @mbggenerated
     */
    private Boolean disabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.create_user
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.update_user
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.reduce_punish_amount
     *
     * @mbggenerated
     */
    private BigDecimal reducePunishAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.reduce_fine_amount
     *
     * @mbggenerated
     */
    private BigDecimal reduceFineAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.repayment_id
     *
     * @mbggenerated
     */
    private String repaymentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.borrow_id
     *
     * @mbggenerated
     */
    private String borrowId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.operate_user_id
     *
     * @mbggenerated
     */
    private String operateUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bo_overdue_reduce_record.operate_user_name
     *
     * @mbggenerated
     */
    private String operateUserName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.id
     *
     * @return the value of bo_overdue_reduce_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.id
     *
     * @param id the value for bo_overdue_reduce_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.disabled
     *
     * @return the value of bo_overdue_reduce_record.disabled
     *
     * @mbggenerated
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.disabled
     *
     * @param disabled the value for bo_overdue_reduce_record.disabled
     *
     * @mbggenerated
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.uuid
     *
     * @return the value of bo_overdue_reduce_record.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.uuid
     *
     * @param uuid the value for bo_overdue_reduce_record.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.create_user
     *
     * @return the value of bo_overdue_reduce_record.create_user
     *
     * @mbggenerated
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.create_user
     *
     * @param createUser the value for bo_overdue_reduce_record.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.create_time
     *
     * @return the value of bo_overdue_reduce_record.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.create_time
     *
     * @param createTime the value for bo_overdue_reduce_record.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.update_user
     *
     * @return the value of bo_overdue_reduce_record.update_user
     *
     * @mbggenerated
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.update_user
     *
     * @param updateUser the value for bo_overdue_reduce_record.update_user
     *
     * @mbggenerated
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.update_time
     *
     * @return the value of bo_overdue_reduce_record.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.update_time
     *
     * @param updateTime the value for bo_overdue_reduce_record.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.remark
     *
     * @return the value of bo_overdue_reduce_record.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.remark
     *
     * @param remark the value for bo_overdue_reduce_record.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.reduce_punish_amount
     *
     * @return the value of bo_overdue_reduce_record.reduce_punish_amount
     *
     * @mbggenerated
     */
    public BigDecimal getReducePunishAmount() {
        return reducePunishAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.reduce_punish_amount
     *
     * @param reducePunishAmount the value for bo_overdue_reduce_record.reduce_punish_amount
     *
     * @mbggenerated
     */
    public void setReducePunishAmount(BigDecimal reducePunishAmount) {
        this.reducePunishAmount = reducePunishAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.reduce_fine_amount
     *
     * @return the value of bo_overdue_reduce_record.reduce_fine_amount
     *
     * @mbggenerated
     */
    public BigDecimal getReduceFineAmount() {
        return reduceFineAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.reduce_fine_amount
     *
     * @param reduceFineAmount the value for bo_overdue_reduce_record.reduce_fine_amount
     *
     * @mbggenerated
     */
    public void setReduceFineAmount(BigDecimal reduceFineAmount) {
        this.reduceFineAmount = reduceFineAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.repayment_id
     *
     * @return the value of bo_overdue_reduce_record.repayment_id
     *
     * @mbggenerated
     */
    public String getRepaymentId() {
        return repaymentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.repayment_id
     *
     * @param repaymentId the value for bo_overdue_reduce_record.repayment_id
     *
     * @mbggenerated
     */
    public void setRepaymentId(String repaymentId) {
        this.repaymentId = repaymentId == null ? null : repaymentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.borrow_id
     *
     * @return the value of bo_overdue_reduce_record.borrow_id
     *
     * @mbggenerated
     */
    public String getBorrowId() {
        return borrowId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.borrow_id
     *
     * @param borrowId the value for bo_overdue_reduce_record.borrow_id
     *
     * @mbggenerated
     */
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId == null ? null : borrowId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.operate_user_id
     *
     * @return the value of bo_overdue_reduce_record.operate_user_id
     *
     * @mbggenerated
     */
    public String getOperateUserId() {
        return operateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.operate_user_id
     *
     * @param operateUserId the value for bo_overdue_reduce_record.operate_user_id
     *
     * @mbggenerated
     */
    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId == null ? null : operateUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bo_overdue_reduce_record.operate_user_name
     *
     * @return the value of bo_overdue_reduce_record.operate_user_name
     *
     * @mbggenerated
     */
    public String getOperateUserName() {
        return operateUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bo_overdue_reduce_record.operate_user_name
     *
     * @param operateUserName the value for bo_overdue_reduce_record.operate_user_name
     *
     * @mbggenerated
     */
    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName == null ? null : operateUserName.trim();
    }
}