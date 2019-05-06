package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/12
 */
public class OrderCreateReq extends BaseReq{

    private int bussType;

    private UserInfoVo userInfo;

    private UserCarVo userCar;

    private BorrowSalesmanVo borrowSalesman;

    private BorrowOrderVo borrowOrder;

    private OrderAuditVo orderAudit;

    private BoOrderItemVo boOrderItem;

    private UserHouseInfoVo userHouseInfo;

    public int getBussType() {
        return bussType;
    }

    public void setBussType(int bussType) {
        this.bussType = bussType;
    }

    public UserHouseInfoVo getUserHouseInfo() {
        return userHouseInfo;
    }

    public void setUserHouseInfo(UserHouseInfoVo userHouseInfo) {
        this.userHouseInfo = userHouseInfo;
    }

    public BoOrderItemVo getBoOrderItem() {
        return boOrderItem;
    }

    public void setBoOrderItem(BoOrderItemVo boOrderItem) {
        this.boOrderItem = boOrderItem;
    }

    public UserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public UserCarVo getUserCar() {
        return userCar;
    }

    public void setUserCar(UserCarVo userCar) {
        this.userCar = userCar;
    }

    public BorrowSalesmanVo getBorrowSalesman() {
        return borrowSalesman;
    }

    public void setBorrowSalesman(BorrowSalesmanVo borrowSalesman) {
        this.borrowSalesman = borrowSalesman;
    }

    public BorrowOrderVo getBorrowOrder() {
        return borrowOrder;
    }

    public void setBorrowOrder(BorrowOrderVo borrowOrder) {
        this.borrowOrder = borrowOrder;
    }

    public OrderAuditVo getOrderAudit() {
        return orderAudit;
    }

    public void setOrderAudit(OrderAuditVo orderAudit) {
        this.orderAudit = orderAudit;
    }
}
