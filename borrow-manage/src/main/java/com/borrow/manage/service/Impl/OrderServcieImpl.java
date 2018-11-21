package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.*;
import com.borrow.manage.enums.*;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.factory.CarRepayPlanFactory;
import com.borrow.manage.model.XMap;
import com.borrow.manage.model.dto.*;
import com.borrow.manage.provider.AbstractCarRepayPlan;
import com.borrow.manage.provider.RemoteDataCollector;
import com.borrow.manage.provider.remotecoll.RemoteDataCollectorService;
import com.borrow.manage.service.OrderServcie;
import com.borrow.manage.utils.ExcelData;
import com.borrow.manage.utils.ExportExcelUtils;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import com.borrow.manage.utils.id.IdProvider;
import com.borrow.manage.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxn on 2018/9/12
 */
@Service
public class OrderServcieImpl implements OrderServcie {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserCarDao userCarDao;
    @Autowired
    BorrowSalesmanDao borrowSalesmanDao;
    @Autowired
    IdProvider idProvider;
    @Autowired
    BorrowProductDao borrowProductDao;
    @Autowired
    BorrowOrderDao borrowOrderDao;
    @Autowired
    BoOrderAuditDao boOrderAuditDao;

    @Autowired
    CarRepayPlanFactory carRepayPlanFactory;
    @Autowired
    BoOrderCostDao boOrderCostDao;
    @Autowired
    BorrowRepaymentDao borrowRepaymentDao;
    @Autowired
    RemoteDataCollector remoteDataCollectorService;


    @Override
    @Transactional
    public ResponseResult orderAdd(OrderCreateReq orderCreateReq) {

        BorrowOrderVo borrowOrderVo = orderCreateReq.getBorrowOrder();
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(borrowOrderVo.getProductCode());
        if (borrowProduct == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        UserInfoVo userInfoVo =  orderCreateReq.getUserInfo();
        // 判断是否存管开户
        XMap xMap = new XMap();
        xMap.put(PlatformConstant.AssertParam.IDCARD,userInfoVo.getIdcard());
        ResponseResult<XMap> xMapResponseResult = remoteDataCollectorService.collect(xMap);
        if(!xMapResponseResult.isSucceed()) {
            return xMapResponseResult;
        }
        //判断是否是开户 和是否借款用户

        UserInfo userInfo = userInfoDao.selInfoByIdcard(userInfoVo.getIdcard());
        if (userInfo == null) {
            userInfo = convertUserInfoVo(userInfoVo);
            userInfoDao.insertUserInfo(userInfo);
        }
        UserCarVo userCarVo = orderCreateReq.getUserCar();
        UserCar userCar = userCarDao.selByPlateNO(userInfo.getUuid(),userCarVo.getPlateNumber());
        if (userCar == null) {
            userCar = convertUserCarVo(userCarVo,userInfo.getUuid());
            userCarDao.insertUserCar(userCar);
        }
        BorrowSalesmanVo borrowSalesmanVo = orderCreateReq.getBorrowSalesman();
        BorrowSalesman borrowSalesman = borrowSalesmanDao.selByMobile(borrowSalesmanVo.getSalesMobile());
        if (borrowSalesman == null) {
            borrowSalesman = convertBorrowSalesmanVo(borrowSalesmanVo);
            borrowSalesmanDao.insertBorrowSalesman(borrowSalesman);
        }
        BorrowOrder borrowOrder = new BorrowOrder();
        borrowOrder.setUuid(UUIDProvider.uuid());
        borrowOrder.setOrderId(idProvider.genId());
        borrowOrder.setBuesType(1);
        borrowOrder.setUserUid(userInfo.getUuid());
        borrowOrder.setProductUid(borrowProduct.getUuid());
        borrowOrder.setProductName(borrowProduct.getpName());
        borrowOrder.setpCode(borrowProduct.getpCode());
        borrowOrder.setbCarUid(userCar.getUuid());
        borrowOrder.setPlateNumber(userCar.getPlateNumber());
        borrowOrder.setSalesmanUid(borrowSalesman.getUuid());
        borrowOrder.setSignTime(new Date());
        borrowOrder.setBoPrice(borrowOrderVo.getBoPrice());
        borrowOrder.setBoExpect(borrowProduct.getpExpect());
        borrowOrder.setBoExpectUnit(borrowProduct.getpExpectUnit());
        borrowOrder.setBoPaytype(borrowProduct.getpPayType());
        borrowOrder.setBoIsState(BoIsStateEnum.WAITING.getCode());
        borrowOrder.setBoPaySource(borrowOrderVo.getBoPaySource());
        borrowOrderDao.insertBorrowOrder(borrowOrder);
        OrderAuditVo orderAuditVo = orderCreateReq.getOrderAudit();
        //TODO 可以用批量插入
        List<BoOrderAudit> boOrderAudits = convertOrderAudit(orderAuditVo, borrowOrder.getOrderId());
        boOrderAudits.stream().forEach(boOrderAudit -> {
            boOrderAuditDao.insertOrderAudit(boOrderAudit);
        });
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }

    @Override
    public ResponseResult orderSelList(OrderListReq orderListReq) {
        List<OrderListRes> listRes = borrowOrderDao.selOrderListWhere(orderListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<OrderListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),pageBaseRes);

    }

    @Override
    public void orderSelListExport(HttpServletResponse response, OrderListReq orderListReq) {
        List<OrderListRes> res = borrowOrderDao.selOrderListWhereAll(orderListReq);
        ExcelData data = new ExcelData();
        data.setName("申请查询");
        List<String> titles = new ArrayList();
        titles.add("借款ID");
        titles.add("申请时间");
        titles.add("客户姓名");
        titles.add("身份证号");
        titles.add("手机号");
        titles.add("车牌号");
        titles.add("销售人员");
        titles.add("销售手机号");
        titles.add("借款金额");
        titles.add("借款产品名称");
        titles.add("期数");
        titles.add("状态");
        titles.add("放款时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        res.stream().forEach(orderListRes -> {
            List<Object> row = new ArrayList();
            row.add(orderListRes.getOrderId());
            row.add(orderListRes.getCreateTime());
            row.add(orderListRes.getUserName());
            row.add(orderListRes.getIdcard());
            row.add(orderListRes.getMobile());
            row.add(orderListRes.getPlateNumber());
            row.add(orderListRes.getSalesName());
            row.add(orderListRes.getSalesMobile());
            row.add(orderListRes.getBoPrice());
            row.add(orderListRes.getProductName());
            row.add(orderListRes.getBoExpect());
            row.add(BoIsStateEnum.getNameValue(
                    Integer.valueOf(orderListRes.getBoIsState())));
            row.add(orderListRes.getBoFinishTime());
            rows.add(row);
        });
        data.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response,"借款申请.xlsx",data);
        } catch (Exception e) {
            logger.error("申请查询导出异常:",e);
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
    }

    @Override
    public ResponseResult orderPaySelList(OrderPayListReq orderPayListReq) {
        List<OrderPayListRes> listRes = borrowOrderDao.selOrderPayListWhere(orderPayListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<OrderPayListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),pageBaseRes);
    }

    @Override
    public void orderPaySelListExport(HttpServletResponse response, OrderPayListReq orderPayListReq) {
        List<OrderPayListRes> res = borrowOrderDao.selOrderPayListWhereAll(orderPayListReq);

        ExcelData data = new ExcelData();
        data.setName("还款综合查询");
        List<String> titles = new ArrayList();
        titles.add("借款ID");
        titles.add("客户姓名");
        titles.add("车牌号");
        titles.add("借款金额");
        titles.add("借款产品名称");
        titles.add("期数");
        titles.add("已还期数");
        titles.add("首次还款日");
        titles.add("总还款状态");
        titles.add("还款结清时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        res.stream().forEach(orderListRes -> {
            List<Object> row = new ArrayList();
            row.add(orderListRes.getOrderId());
            row.add(orderListRes.getUserName());
            row.add(orderListRes.getPlateNumber());
            row.add(orderListRes.getBoPrice());
            row.add(orderListRes.getProductName());
            row.add(orderListRes.getBoExpect());
            row.add(orderListRes.getBoPayExpect());
            row.add(orderListRes.getFirstExpectTime());
            row.add(BoIsStateEnum.getNameValue(Integer.valueOf(orderListRes.getBoIsState())));
            row.add(orderListRes.getLastPayTime());
            rows.add(row);
        });
        data.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response,"还款综合查询.xlsx",data);
        } catch (Exception e) {
            logger.error("申请查询导出异常:",e);
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult makeLoans(MakeLoansReq makeLoansReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(makeLoansReq.getOrderId()));
        if (borrowOrder ==  null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        AbstractCarRepayPlan abstractCarRepayPlan = carRepayPlanFactory.getCarRepayPlan(ProductEnum.getProductEnum(borrowOrder.getpCode()));
        RepayPlanCalReq repayPlanCalReq = new RepayPlanCalReq();
        repayPlanCalReq.setpCode(borrowOrder.getpCode());
        repayPlanCalReq.setBoPrice(borrowOrder.getBoPrice());
        BoCarRepayPlanRes repayPlanRes = abstractCarRepayPlan.planCal(repayPlanCalReq);
        orderCostPlan(repayPlanRes,borrowOrder);
        List<BorrowRepayment> repaymentsResult = orderRepaymentPlan(repayPlanRes,borrowOrder);

        BorrowOrder bo = new BorrowOrder();
        bo.setBoIsState(BoIsStateEnum.LOAN_OVER.getCode());
        bo.setBoFinishPrice(BigDecimal.valueOf(Double.valueOf(repayPlanRes.getBoFinishPrice())));
        bo.setBoPayState(BoRepayStatusEnum.NORMAL.getCode());
        bo.setBoIsFinish(1);
        bo.setBoFinishTime(new Date());
        bo.setFirstExpectTime(repaymentsResult.get(0).getBrTime());
        borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(),bo);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }

    @Override
    public ResponseResult orderCancel(OrderCancelReq orderCancelReq) {
        borrowOrderDao.upBorrowOrderByOrderId(Long.valueOf(orderCancelReq.getOrderId()),BoIsStateEnum.LOAN_CANCEL.getCode());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }



    @Override
    public ResponseResult orderDetailSel(OrderDetailReq orderDetailReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(orderDetailReq.getOrderId()));
        if (borrowOrder == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoDao.selInfoByUid(borrowOrder.getUserUid());
        if (userInfo == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        List<BoOrderAudit> auditList = boOrderAuditDao.selByOrderId(borrowOrder.getOrderId());
        List<String> auditkeys = new ArrayList<>();
        auditList.stream().forEach(boOrderAudit -> {
            auditkeys.add(boOrderAudit.getAuditKey());
        });
        OrderDetailRes detailRes = new OrderDetailRes();
        detailRes.setBoPaySource(borrowOrder.getBoPaySource());
        detailRes.setIndustry(userInfo.getIndustry());
        detailRes.setUserEarns(userInfo.getUserEarns());
        detailRes.setWorkNature(userInfo.getWorkNature());
        detailRes.setCreditDec(userInfo.getCreditDec());
        detailRes.setAuditkeys(auditkeys);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),detailRes);
    }


    private void orderCostPlan(BoCarRepayPlanRes repayPlanRes,BorrowOrder borrowOrder){
        List<BoOrderCost> orderCosts = new ArrayList<>();
        BoOrderCost costGPS = new BoOrderCost();
        costGPS.setUuid(UUIDProvider.uuid());
        costGPS.setOrderId(borrowOrder.getOrderId());
        costGPS.setCostKey(ProductRateEnum.GPS_COST.getRateKey());
        costGPS.setCostAmount(BigDecimal.valueOf(Double.valueOf(repayPlanRes.getGpsCost())));
        costGPS.setCostDesc(ProductRateEnum.GPS_COST.getRateDesc());
        orderCosts.add(costGPS);
        BoOrderCost elalyServiceCost = new BoOrderCost();
        elalyServiceCost.setUuid(UUIDProvider.uuid());
        elalyServiceCost.setOrderId(borrowOrder.getOrderId());
        elalyServiceCost.setCostKey(ProductRateEnum.EARLY_SERVICE_COST.getRateKey());
        elalyServiceCost.setCostAmount(BigDecimal.valueOf(Double.valueOf(repayPlanRes.getEarlyServiceCost())));
        elalyServiceCost.setCostDesc(ProductRateEnum.EARLY_SERVICE_COST.getRateDesc());
        orderCosts.add(elalyServiceCost);
        orderCosts.stream().forEach(boOrderCost -> {
            boOrderCostDao.insertOrderCost(boOrderCost);
        });


    }

    private List<BorrowRepayment> orderRepaymentPlan(BoCarRepayPlanRes repayPlanRes,BorrowOrder borrowOrder){
        List<BorrowRepayment> result  = new ArrayList<>();
        repayPlanRes.getRepayPlans().stream().forEach(carRepayPlanVo -> {
            BorrowRepayment repayment = new BorrowRepayment();
            repayment.setUuid(UUIDProvider.uuid());
            repayment.setRepayId(idProvider.genId());
            repayment.setOrderId(borrowOrder.getOrderId());
            repayment.setUserUid(borrowOrder.getUserUid());
            repayment.setRepayAmount(BigDecimal.valueOf(Double.valueOf(carRepayPlanVo.getRepayAmount())));
            repayment.setCapitalAmount(BigDecimal.valueOf(Double.valueOf(carRepayPlanVo.getCapitalAmount())));
            repayment.setBrTime(Utility.getBrTime(Integer.valueOf(carRepayPlanVo.getRepayExpect())));
            repayment.setRepayExpect(Integer.valueOf(carRepayPlanVo.getRepayExpect()));
            repayment.setInterestAmount(BigDecimal.valueOf(Double.valueOf(carRepayPlanVo.getInterestAmount())));
            repayment.setServiceFee(BigDecimal.valueOf(Double.valueOf(carRepayPlanVo.getServiceFee())));
            repayment.setRepayStatus(RepayStatusEnum.PAY_NO.getCode());
            repayment.setBoRepayStatus(BoRepayStatusEnum.NORMAL.getCode());
            borrowRepaymentDao.insertRepayment(repayment);
            result.add(repayment);
        });
        return result;
    }

    private UserInfo convertUserInfoVo(UserInfoVo userInfoVo ){
        UserInfo userNew = new UserInfo();
        userNew.setUuid(UUIDProvider.uuid());
        userNew.setUserName(userInfoVo.getUserName());
        userNew.setIdcard(userInfoVo.getIdcard());
        userNew.setMobile(userInfoVo.getMobile());
        userNew.setCreditStatus(0);
        userNew.setCreditDec(userInfoVo.getCreditDec());
        userNew.setIndustry(userInfoVo.getIndustry());
        userNew.setWorkNature(userInfoVo.getWorkNature());
        userNew.setUserEarns(userInfoVo.getUserEarns());
        return userNew;
    }

    private UserCar convertUserCarVo(UserCarVo userCarVo,String userUid){
        UserCar userCarNew = new UserCar();
        userCarNew.setUuid(UUIDProvider.uuid());
        userCarNew.setUserUid(userUid);
        userCarNew.setPlateNumber(userCarVo.getPlateNumber());
        return userCarNew;
    }
    private BorrowSalesman convertBorrowSalesmanVo(BorrowSalesmanVo borrowSalesmanVo) {
        BorrowSalesman borrowSalesmanNew = new BorrowSalesman();
        borrowSalesmanNew.setUuid(UUIDProvider.uuid());
        borrowSalesmanNew.setSalesMobile(borrowSalesmanVo.getSalesMobile());
        borrowSalesmanNew.setSalesName(borrowSalesmanVo.getSalesName());
        return borrowSalesmanNew;
    }

    private List<BoOrderAudit> convertOrderAudit(OrderAuditVo orderAuditVo, long orderId){
        List<BoOrderAudit> boOrderAudits = new ArrayList<>();
        orderAuditVo.getAuditkeys().stream().forEach( s -> {
            BoOrderAudit boOrderAudit = new BoOrderAudit();
            boOrderAudit.setUuid(UUIDProvider.uuid());
            boOrderAudit.setOrderId(orderId);
            boOrderAudit.setAuditTime(new Date());
            boOrderAudit.setAuditKey(s);
            boOrderAudits.add(boOrderAudit);
        });
        return boOrderAudits;
    }
}
