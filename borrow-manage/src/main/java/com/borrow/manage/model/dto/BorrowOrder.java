package com.borrow.manage.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BorrowOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.disabled
     *
     * @mbggenerated
     */
    private Boolean disabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.create_user
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.update_user
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.order_id
     *
     * @mbggenerated
     */
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bues_type
     *
     * @mbggenerated
     */
    private Integer buesType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.user_uid
     *
     * @mbggenerated
     */
    private String userUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.product_uid
     *
     * @mbggenerated
     */
    private String productUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.b_car_uid
     *
     * @mbggenerated
     */
    private String bCarUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.p_code
     *
     * @mbggenerated
     */
    private String pCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.plate_number
     *
     * @mbggenerated
     */
    private String plateNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.salesman_uid
     *
     * @mbggenerated
     */
    private String salesmanUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.sign_time
     *
     * @mbggenerated
     */
    private Date signTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_price
     *
     * @mbggenerated
     */
    private BigDecimal boPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_finish_price
     *
     * @mbggenerated
     */
    private BigDecimal boFinishPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_expect
     *
     * @mbggenerated
     */
    private Integer boExpect;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_expect_unit
     *
     * @mbggenerated
     */
    private Integer boExpectUnit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_paytype
     *
     * @mbggenerated
     */
    private Integer boPaytype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_is_state
     *
     * @mbggenerated
     */
    private Integer boIsState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_is_finish
     *
     * @mbggenerated
     */
    private Integer boIsFinish;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_finish_time
     *
     * @mbggenerated
     */
    private Date boFinishTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_pay_source
     *
     * @mbggenerated
     */
    private String boPaySource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_pay_state
     *
     * @mbggenerated
     */
    private Integer boPayState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.first_pay_time
     *
     * @mbggenerated
     */
    private Date firstPayTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.last_pay_time
     *
     * @mbggenerated
     */
    private Date lastPayTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.bo_pay_expect
     *
     * @mbggenerated
     */
    private Integer boPayExpect;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.first_expect_time
     *
     * @mbggenerated
     */
    private Date firstExpectTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.last_expect_time
     *
     * @mbggenerated
     */
    private Date lastExpectTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_order.pay_channel
     *
     * @mbggenerated
     */
    private Integer payChannel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.id
     *
     * @return the value of borrow_order.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.id
     *
     * @param id the value for borrow_order.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.disabled
     *
     * @return the value of borrow_order.disabled
     *
     * @mbggenerated
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.disabled
     *
     * @param disabled the value for borrow_order.disabled
     *
     * @mbggenerated
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.uuid
     *
     * @return the value of borrow_order.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.uuid
     *
     * @param uuid the value for borrow_order.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.create_user
     *
     * @return the value of borrow_order.create_user
     *
     * @mbggenerated
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.create_user
     *
     * @param createUser the value for borrow_order.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.create_time
     *
     * @return the value of borrow_order.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.create_time
     *
     * @param createTime the value for borrow_order.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.update_user
     *
     * @return the value of borrow_order.update_user
     *
     * @mbggenerated
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.update_user
     *
     * @param updateUser the value for borrow_order.update_user
     *
     * @mbggenerated
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.update_time
     *
     * @return the value of borrow_order.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.update_time
     *
     * @param updateTime the value for borrow_order.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.remark
     *
     * @return the value of borrow_order.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.remark
     *
     * @param remark the value for borrow_order.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.order_id
     *
     * @return the value of borrow_order.order_id
     *
     * @mbggenerated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.order_id
     *
     * @param orderId the value for borrow_order.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bues_type
     *
     * @return the value of borrow_order.bues_type
     *
     * @mbggenerated
     */
    public Integer getBuesType() {
        return buesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bues_type
     *
     * @param buesType the value for borrow_order.bues_type
     *
     * @mbggenerated
     */
    public void setBuesType(Integer buesType) {
        this.buesType = buesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.user_uid
     *
     * @return the value of borrow_order.user_uid
     *
     * @mbggenerated
     */
    public String getUserUid() {
        return userUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.user_uid
     *
     * @param userUid the value for borrow_order.user_uid
     *
     * @mbggenerated
     */
    public void setUserUid(String userUid) {
        this.userUid = userUid == null ? null : userUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.product_uid
     *
     * @return the value of borrow_order.product_uid
     *
     * @mbggenerated
     */
    public String getProductUid() {
        return productUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.product_uid
     *
     * @param productUid the value for borrow_order.product_uid
     *
     * @mbggenerated
     */
    public void setProductUid(String productUid) {
        this.productUid = productUid == null ? null : productUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.b_car_uid
     *
     * @return the value of borrow_order.b_car_uid
     *
     * @mbggenerated
     */
    public String getbCarUid() {
        return bCarUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.b_car_uid
     *
     * @param bCarUid the value for borrow_order.b_car_uid
     *
     * @mbggenerated
     */
    public void setbCarUid(String bCarUid) {
        this.bCarUid = bCarUid == null ? null : bCarUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.product_name
     *
     * @return the value of borrow_order.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.product_name
     *
     * @param productName the value for borrow_order.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.p_code
     *
     * @return the value of borrow_order.p_code
     *
     * @mbggenerated
     */
    public String getpCode() {
        return pCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.p_code
     *
     * @param pCode the value for borrow_order.p_code
     *
     * @mbggenerated
     */
    public void setpCode(String pCode) {
        this.pCode = pCode == null ? null : pCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.plate_number
     *
     * @return the value of borrow_order.plate_number
     *
     * @mbggenerated
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.plate_number
     *
     * @param plateNumber the value for borrow_order.plate_number
     *
     * @mbggenerated
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.salesman_uid
     *
     * @return the value of borrow_order.salesman_uid
     *
     * @mbggenerated
     */
    public String getSalesmanUid() {
        return salesmanUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.salesman_uid
     *
     * @param salesmanUid the value for borrow_order.salesman_uid
     *
     * @mbggenerated
     */
    public void setSalesmanUid(String salesmanUid) {
        this.salesmanUid = salesmanUid == null ? null : salesmanUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.sign_time
     *
     * @return the value of borrow_order.sign_time
     *
     * @mbggenerated
     */
    public Date getSignTime() {
        return signTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.sign_time
     *
     * @param signTime the value for borrow_order.sign_time
     *
     * @mbggenerated
     */
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_price
     *
     * @return the value of borrow_order.bo_price
     *
     * @mbggenerated
     */
    public BigDecimal getBoPrice() {
        return boPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_price
     *
     * @param boPrice the value for borrow_order.bo_price
     *
     * @mbggenerated
     */
    public void setBoPrice(BigDecimal boPrice) {
        this.boPrice = boPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_finish_price
     *
     * @return the value of borrow_order.bo_finish_price
     *
     * @mbggenerated
     */
    public BigDecimal getBoFinishPrice() {
        return boFinishPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_finish_price
     *
     * @param boFinishPrice the value for borrow_order.bo_finish_price
     *
     * @mbggenerated
     */
    public void setBoFinishPrice(BigDecimal boFinishPrice) {
        this.boFinishPrice = boFinishPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_expect
     *
     * @return the value of borrow_order.bo_expect
     *
     * @mbggenerated
     */
    public Integer getBoExpect() {
        return boExpect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_expect
     *
     * @param boExpect the value for borrow_order.bo_expect
     *
     * @mbggenerated
     */
    public void setBoExpect(Integer boExpect) {
        this.boExpect = boExpect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_expect_unit
     *
     * @return the value of borrow_order.bo_expect_unit
     *
     * @mbggenerated
     */
    public Integer getBoExpectUnit() {
        return boExpectUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_expect_unit
     *
     * @param boExpectUnit the value for borrow_order.bo_expect_unit
     *
     * @mbggenerated
     */
    public void setBoExpectUnit(Integer boExpectUnit) {
        this.boExpectUnit = boExpectUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_paytype
     *
     * @return the value of borrow_order.bo_paytype
     *
     * @mbggenerated
     */
    public Integer getBoPaytype() {
        return boPaytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_paytype
     *
     * @param boPaytype the value for borrow_order.bo_paytype
     *
     * @mbggenerated
     */
    public void setBoPaytype(Integer boPaytype) {
        this.boPaytype = boPaytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_is_state
     *
     * @return the value of borrow_order.bo_is_state
     *
     * @mbggenerated
     */
    public Integer getBoIsState() {
        return boIsState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_is_state
     *
     * @param boIsState the value for borrow_order.bo_is_state
     *
     * @mbggenerated
     */
    public void setBoIsState(Integer boIsState) {
        this.boIsState = boIsState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_is_finish
     *
     * @return the value of borrow_order.bo_is_finish
     *
     * @mbggenerated
     */
    public Integer getBoIsFinish() {
        return boIsFinish;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_is_finish
     *
     * @param boIsFinish the value for borrow_order.bo_is_finish
     *
     * @mbggenerated
     */
    public void setBoIsFinish(Integer boIsFinish) {
        this.boIsFinish = boIsFinish;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_finish_time
     *
     * @return the value of borrow_order.bo_finish_time
     *
     * @mbggenerated
     */
    public Date getBoFinishTime() {
        return boFinishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_finish_time
     *
     * @param boFinishTime the value for borrow_order.bo_finish_time
     *
     * @mbggenerated
     */
    public void setBoFinishTime(Date boFinishTime) {
        this.boFinishTime = boFinishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_pay_source
     *
     * @return the value of borrow_order.bo_pay_source
     *
     * @mbggenerated
     */
    public String getBoPaySource() {
        return boPaySource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_pay_source
     *
     * @param boPaySource the value for borrow_order.bo_pay_source
     *
     * @mbggenerated
     */
    public void setBoPaySource(String boPaySource) {
        this.boPaySource = boPaySource == null ? null : boPaySource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_pay_state
     *
     * @return the value of borrow_order.bo_pay_state
     *
     * @mbggenerated
     */
    public Integer getBoPayState() {
        return boPayState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_pay_state
     *
     * @param boPayState the value for borrow_order.bo_pay_state
     *
     * @mbggenerated
     */
    public void setBoPayState(Integer boPayState) {
        this.boPayState = boPayState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.first_pay_time
     *
     * @return the value of borrow_order.first_pay_time
     *
     * @mbggenerated
     */
    public Date getFirstPayTime() {
        return firstPayTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.first_pay_time
     *
     * @param firstPayTime the value for borrow_order.first_pay_time
     *
     * @mbggenerated
     */
    public void setFirstPayTime(Date firstPayTime) {
        this.firstPayTime = firstPayTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.last_pay_time
     *
     * @return the value of borrow_order.last_pay_time
     *
     * @mbggenerated
     */
    public Date getLastPayTime() {
        return lastPayTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.last_pay_time
     *
     * @param lastPayTime the value for borrow_order.last_pay_time
     *
     * @mbggenerated
     */
    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.bo_pay_expect
     *
     * @return the value of borrow_order.bo_pay_expect
     *
     * @mbggenerated
     */
    public Integer getBoPayExpect() {
        return boPayExpect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.bo_pay_expect
     *
     * @param boPayExpect the value for borrow_order.bo_pay_expect
     *
     * @mbggenerated
     */
    public void setBoPayExpect(Integer boPayExpect) {
        this.boPayExpect = boPayExpect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.first_expect_time
     *
     * @return the value of borrow_order.first_expect_time
     *
     * @mbggenerated
     */
    public Date getFirstExpectTime() {
        return firstExpectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.first_expect_time
     *
     * @param firstExpectTime the value for borrow_order.first_expect_time
     *
     * @mbggenerated
     */
    public void setFirstExpectTime(Date firstExpectTime) {
        this.firstExpectTime = firstExpectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.last_expect_time
     *
     * @return the value of borrow_order.last_expect_time
     *
     * @mbggenerated
     */
    public Date getLastExpectTime() {
        return lastExpectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.last_expect_time
     *
     * @param lastExpectTime the value for borrow_order.last_expect_time
     *
     * @mbggenerated
     */
    public void setLastExpectTime(Date lastExpectTime) {
        this.lastExpectTime = lastExpectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column borrow_order.pay_channel
     *
     * @return the value of borrow_order.pay_channel
     *
     * @mbggenerated
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column borrow_order.pay_channel
     *
     * @param payChannel the value for borrow_order.pay_channel
     *
     * @mbggenerated
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }
}
