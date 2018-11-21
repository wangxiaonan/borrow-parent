package com.borrow.manage.vo;

/**
 * Created by wxn on 2018/9/12
 */
public class OrderCreateReq {


    private UserInfoVo userInfo;

    private UserCarVo userCar;

    private BorrowSalesmanVo borrowSalesman;

    private BorrowOrderVo borrowOrder;

    private OrderAuditVo orderAudit;

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
