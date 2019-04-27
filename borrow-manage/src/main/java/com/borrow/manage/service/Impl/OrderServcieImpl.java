package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.*;
import com.borrow.manage.enums.*;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.factory.CarRepayPlanFactory;
import com.borrow.manage.model.XMap;
import com.borrow.manage.model.dto.*;
import com.borrow.manage.provider.AbstractCarRepayPlan;
import com.borrow.manage.provider.RemoteDataCollector;
import com.borrow.manage.service.OrderServcie;
import com.borrow.manage.utils.ExcelData;
import com.borrow.manage.utils.ExportExcelUtils;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import com.borrow.manage.utils.id.IdProvider;
import com.borrow.manage.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    BoProductRateDao boProductRateDao;
    @Autowired
    BoOrderItemDao boOrderItemDao;



    @Override
    @Transactional
    public ResponseResult orderAdd(OrderCreateReq orderCreateReq) {

        BorrowOrderVo borrowOrderVo = orderCreateReq.getBorrowOrder();
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(borrowOrderVo.getProductCode());
        if (borrowProduct == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        UserInfoVo userInfoVo =  orderCreateReq.getUserInfo();
        //TODO
        // 判断是否是开户 和是否借款用户
        XMap xMap = new XMap();
        xMap.put(PlatformConstant.FundsParam.IDCARD,userInfoVo.getIdcard());
        xMap.put(DataClientEnum.URL_TYPE.getUrlType(),DataClientEnum.USER_CHECK_DATA.getUrlType());
        ResponseResult<XMap> xMapResponseResult = remoteDataCollectorService.collect(xMap);
        if(!xMapResponseResult.isSucceed()) {
            return xMapResponseResult;
        }
        XMap xMapRes = xMapResponseResult.getData();
        String data = xMapRes.getString("data");
//        XMap p = JSON.parseObject(data,XMap.class);
//        String isOpen = p.getString(PlatformConstant.FundsParam.ISOPEN);
//        String userType = p.getString(PlatformConstant.FundsParam.USER_TYPE);
//        if (!PlatformConstant.FundsParam.ISOPEN_YES.equals(isOpen)) {
//            throw new BorrowException(ExceptionCode.USER_CHECK_OPEN);
//        }
//        if (!PlatformConstant.FundsParam.USER_TYPE_LOAN.equals(userType)) {
//            throw new BorrowException(ExceptionCode.USER_CHECK_IDENTITY);
//        }



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
        borrowOrder.setBoPayState(BoRepayStatusEnum.NORMAL.getCode());
        borrowOrderDao.insertBorrowOrder(borrowOrder);
        OrderAuditVo orderAuditVo = orderCreateReq.getOrderAudit();
        //TODO 可以用批量插入
        List<BoOrderAudit> boOrderAudits = convertOrderAudit(orderAuditVo, borrowOrder.getOrderId());
        boOrderAudits.stream().forEach(boOrderAudit -> {
            boOrderAuditDao.insertOrderAudit(boOrderAudit);
        });
        BoOrderItem  boOrderItem = new BoOrderItem();
        boOrderItem.setUuid(UUIDProvider.uuid());
        boOrderItem.setOrderId(borrowOrder.getOrderId());
        boOrderItem.setItemKey(BoOrderItemEnum.BO_SOURCE.getItemKey());
        boOrderItem.setItemValue(orderCreateReq.getBoOrderItem().getBoSource());
        boOrderItem.setItemDesc(BoOrderItemEnum.BO_SOURCE.getItemDesc());
        boOrderItemDao.insertItem(boOrderItem);
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
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(borrowOrder.getpCode());
        AbstractCarRepayPlan abstractCarRepayPlan = carRepayPlanFactory.getCarRepayPlan(ProductPayTypeEnum.getProductPayType(borrowProduct.getpPayType()));
        RepayPlanCalReq repayPlanCalReq = new RepayPlanCalReq();
        repayPlanCalReq.setpCode(borrowOrder.getpCode());
        repayPlanCalReq.setBoPrice(borrowOrder.getBoPrice());
        BoCarRepayPlanRes repayPlanRes = abstractCarRepayPlan.planCal(repayPlanCalReq);
        orderCostPlan(repayPlanRes,borrowOrder);
        List<BorrowRepayment> repaymentsResult = orderRepaymentPlan(repayPlanRes,borrowOrder);

        BorrowOrder bo = new BorrowOrder();
        bo.setBoIsState(BoIsStateEnum.LOAN_OVER.getCode());
        bo.setBoFinishPrice(BigDecimal.valueOf(Double.valueOf(repayPlanRes.getBoFinishPrice())));
        bo.setBoIsFinish(BoIsFinishEnum.FINISH_YES.getCode());
        bo.setBoFinishTime(new Date());
        bo.setFirstExpectTime(repaymentsResult.get(0).getBrTime());
        bo.setLastExpectTime(repaymentsResult.get(repaymentsResult.size()-1).getBrTime());
        borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(),bo);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }

    @Override
    public ResponseResult orderCancel(OrderCancelReq orderCancelReq) {
        borrowOrderDao.upBorrowOrderByOrderId(Long.valueOf(orderCancelReq.getOrderId()),BoIsStateEnum.LOAN_CANCEL.getCode());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }

    @Override
    public ResponseResult orderLoaning(String orderId) {
        borrowOrderDao.upBorrowOrderByOrderId(Long.valueOf(orderId),BoIsStateEnum.LOANING.getCode());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
    }

    @Override
    public ResponseResult makeRaise(MakeLoansReq makeLoansReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(makeLoansReq.getOrderId()));
        if (borrowOrder ==  null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        XMap<String,Object> thirdParamMap = new XMap<String, Object>();
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_NO,borrowOrder.getOrderId().toString());

        BorrowProduct borrowProduct = borrowProductDao.selByPUid(borrowOrder.getProductUid());
        thirdParamMap.put(PlatformConstant.FundsParam.PRODUCT_NAME,borrowProduct.getpName());
        thirdParamMap.put(PlatformConstant.FundsParam.REPAY_MODE,String.valueOf(borrowOrder.getBoPaytype()));
        thirdParamMap.put(PlatformConstant.FundsParam.CLOSE_PERIOD,borrowOrder.getBoExpect().toString());

        List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowProduct.getUuid());
        Map<String,String> mapRate = new HashMap();
        boProductRates.stream().forEach(boProductRate -> {
            mapRate.put(boProductRate.getRateKey(),boProductRate.getRateValue());
        });
        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_SERVICE_RATE,mapRate.get(ProductRateEnum.EARLY_SERVICE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.MONTH_SERVICE_RATE,mapRate.get(ProductRateEnum.MONTH_SERVICE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.MONTH_ACCRUAL_RATE,mapRate.get(ProductRateEnum.MONTH_ACCRUAL_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.GURANTEE_VIOLATE_RATE,mapRate.get(ProductRateEnum.GUARANTEE_VIOLATE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.SERVICE_VIOLATE_RATE,mapRate.get(ProductRateEnum.SERVICE_VIOLATE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_PAY_RATE,mapRate.get(ProductRateEnum.EARLY_PAY_RATE.getRateKey()));


        String gpsCost = mapRate.get(ProductRateEnum.GPS_COST.getRateKey());
        BigDecimal earlyServiceRate = BigDecimal.valueOf(Double.valueOf(thirdParamMap.getString(PlatformConstant.FundsParam.EARLY_SERVICE_RATE)));
        BigDecimal earlyServiceCost = borrowOrder.getBoPrice().multiply(earlyServiceRate);

        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_SERVICE_FEE,earlyServiceCost.toString());
        thirdParamMap.put(PlatformConstant.FundsParam.GPS_COST,gpsCost);

        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_TYPE,borrowOrder.getBuesType().toString());
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_AMT,borrowOrder.getBoPrice().toString());

        UserInfo userInfo = userInfoDao.selInfoByUid(borrowOrder.getUserUid());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_ID,userInfo.getIdcard());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_NAME,userInfo.getUserName());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_PHONE,userInfo.getMobile());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_INDUSTRY,userInfo.getIndustry());
        thirdParamMap.put(PlatformConstant.FundsParam.JOB_DESC,userInfo.getWorkNature());
        thirdParamMap.put(PlatformConstant.FundsParam.INCOMING_DESC,userInfo.getUserEarns());
        thirdParamMap.put(PlatformConstant.FundsParam.CREDIT_INVESTIGATION,userInfo.getCreditDec());

        List<BoOrderItem> boOrderItems = boOrderItemDao.selByorderId(borrowOrder.getOrderId());
        Map itemkeys = new HashMap();
        boOrderItems.stream().forEach( boOrderItem -> {
            itemkeys.put(boOrderItem.getItemKey(),boOrderItem.getItemValue());
        });

        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_DESC,itemkeys.get(BoOrderItemEnum.BO_SOURCE.getItemKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.GUARANTEE_INFO_DESC,borrowOrder.getBoPaySource());

        List<BoOrderAudit> boOrderAudits = boOrderAuditDao.selByOrderId(borrowOrder.getOrderId());
        List<AuditStatusVo> mapList = new ArrayList<>();
        boOrderAudits.stream().forEach(boOrderAudit -> {
            AuditStatusVo statusVo = new AuditStatusVo();
            statusVo.setId(OrderAuditEnum.getAuthCodeByKey(boOrderAudit.getAuditKey()));
            statusVo.setAudit(OrderAuditEnum.getAuthNameByKey(boOrderAudit.getAuditKey()));
            mapList.add(statusVo);
        });
        UserCar userCar = userCarDao.selByPlateNO(borrowOrder.getUserUid(),borrowOrder.getPlateNumber());
        XMap loanCarInfoMap = new XMap();
//        loanCarInfoMap.put();

        thirdParamMap.put(PlatformConstant.FundsParam.AUDIT,mapList);

        thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(),DataClientEnum.ORDER_MAKE_RAISE.getUrlType());
        //TODO 筹标
        ResponseResult<XMap> responseResult = remoteDataCollectorService.collect(thirdParamMap);
        if (!responseResult.isSucceed()) {
            throw  new BorrowException(ExceptionCode.ORDER_MAKE_RAISE_ERROR);
        }
        BorrowOrder borr = new BorrowOrder();
        borr.setBoIsState(BoIsStateEnum.LOANING.getCode());
        borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(),borr);
//        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
        return responseResult;
    }

    @Override
    public ResponseResult orderDetailSel(OrderDetailReq orderDetailReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(orderDetailReq.getOrderId()));
        if (borrowOrder == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        String userUid = borrowOrder.getUserUid();
        UserInfo userInfo = userInfoDao.selInfoByUid(userUid);
        UserCar userCar = userCarDao.selByPlateNO(userUid, borrowOrder.getPlateNumber());
        if (userInfo == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        List<BoOrderAudit> auditList = boOrderAuditDao.selByOrderId(borrowOrder.getOrderId());
        Map<String, String> auditkeys = new LinkedHashMap<>();
        auditList.stream().forEach(boOrderAudit -> {
            auditkeys.put(boOrderAudit.getAuditKey(), boOrderAudit.getAuditFileUrl());
        });
         OrderDetailRes detailRes = new OrderDetailRes();
        userInfo.getChildrenDesc();
        userInfo.getWorkNature();
        userInfo.getUserEarns();
        userInfo.getLiabilitiesDesc();
        userInfo.getGuaranteeDesc();
        detailRes.setBoPaySource(borrowOrder.getBoPaySource());
        BeanUtils.copyProperties(userInfo, detailRes);
        BeanUtils.copyProperties(userCar,detailRes);


//        $('#carModel').html(res.data.carModel);
//        $('#carColor').html(res.data.carColor);
//        $('#signTime').html(res.data.signTime);
//        $('#assessmentPrice').html(res.data.assessmentPrice);
//        $('#plateNumber').html(res.data.plateNumber);
//        $('#mileageDesc').html(res.data.mileageDesc);
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
            repayment.setSuretyStatus(SuretyStatusEnum.SURETY_STATUS_NO.getCode());
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
        userNew.setWorkNature(userInfoVo.getWorkNature());
        userNew.setUserEarns(userInfoVo.getUserEarns());
        userNew.setSex(userInfoVo.getSex());
//        `user_earns` varchar(50) NOT NULL DEFAULT '' COMMENT '收入',
//                `sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别 1男 2女',
//                `marriage_status` int(3) NOT NULL DEFAULT '3' COMMENT '1 未婚 2已婚 3其他',
//                `children_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '子女情况',
//                `liabilities_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '负债情况',
//                `guarantee_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '担保措施',
        userNew.setMarriageStatus(userInfoVo.getMarriage());
        userNew.setChildrenDesc(userInfoVo.getChildren());
        userNew.setLiabilitiesDesc(userInfoVo.getUserDebts());
        userNew.setGuaranteeDesc(userInfoVo.getUserAssure());
        return userNew;
    }

    private UserCar convertUserCarVo(UserCarVo userCarVo,String userUid){
        UserCar userCarNew = new UserCar();
        BeanUtils.copyProperties(userCarVo, userCarNew);
        userCarNew.setUuid(UUIDProvider.uuid());
        userCarNew.setUserUid(userUid);
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
        orderAuditVo.getAuditkeys().forEach((s, v) -> {
            BoOrderAudit boOrderAudit = new BoOrderAudit();
            boOrderAudit.setUuid(UUIDProvider.uuid());
            boOrderAudit.setOrderId(orderId);
            boOrderAudit.setAuditTime(new Date());
            boOrderAudit.setAuditKey(s);
            boOrderAudit.setAuthName(OrderAuditEnum.getAuthNameByKey(s));
            boOrderAudit.setAuditValue(OrderAuditEnum.getAuthCodeByKey(s));
            boOrderAudit.setAuditFileUrl(v);
            boOrderAudits.add(boOrderAudit);
        });
        return boOrderAudits;
    }
}
