package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.*;
import com.borrow.manage.enums.*;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.factory.CarRepayPlanFactory;
import com.borrow.manage.model.XMap;
import com.borrow.manage.model.dto.*;
import com.borrow.manage.provider.AbstractCarRepayPlan;
import com.borrow.manage.provider.RemoteDataCollector;
import com.borrow.manage.service.OrderRepayServcie;
import com.borrow.manage.service.ProductService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wxn on 2018/9/18
 */
@Service
public class OrderRepayServcieImpl implements OrderRepayServcie {


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BorrowRepaymentDao borrowRepaymentDao;
    @Autowired
    private CarRepayPlanFactory carRepayPlanFactory;
    @Autowired
    private BorrowOrderDao borrowOrderDao;
    @Autowired
    private BoOrderCostDao boOrderCostDao;
    @Autowired
    private BorrowProductDao borrowProductDao;
    @Autowired
    private BoProductRateDao boProductRateDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private BoOrderPayRecordDao boOrderPayRecordDao;
    @Autowired
    private IdProvider idProvider;
    @Autowired
    RemoteDataCollector remoteDataCollectorService;

    @Autowired
    ProductService productService;

    @Autowired
    BoOverdueReduceRecordDao boOverdueReduceRecordDao;

    @Autowired
    SysUserDao sysUserDao;


    @Override
    public ResponseResult orderRepaySelList(OrderRepayListReq orderRepayListReq) {
        List<OrderRepayListRes> listRes = borrowRepaymentDao.selOrderRepayListWhere(orderRepayListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<OrderRepayListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), pageBaseRes);
    }

    @Override
    public void orderRepaySelListExport(HttpServletResponse response,
                                        OrderRepayListReq orderRepayListReq) {
        List<OrderRepayListRes> res = borrowRepaymentDao.selOrderRepayListWhereALL(orderRepayListReq);

        ExcelData data = new ExcelData();
        data.setName("待还款明细");
        List<String> titles = new ArrayList();
        titles.add("借款ID");
        titles.add("用户名");
        titles.add("手机号");
        titles.add("借款金额");
        titles.add("产品名称");
        titles.add("期数");
        titles.add("所属期数");
        titles.add("当期应还日");
        titles.add("当期应还金额");
        titles.add("是否逾期");
        titles.add("还款状态");
        titles.add("已还款时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        res.stream().forEach(orderRepayListRes -> {
            List<Object> row = new ArrayList();
            row.add(orderRepayListRes.getOrderId());
            row.add(orderRepayListRes.getUserName());
            row.add(orderRepayListRes.getUserMobile());
            row.add(orderRepayListRes.getBoPrice());
            row.add(orderRepayListRes.getProductName());
            row.add(orderRepayListRes.getBoExpect());
            row.add(orderRepayListRes.getRepayExpect());
            row.add(orderRepayListRes.getBrTime());
            row.add(orderRepayListRes.getRepayAmount());
            row.add(BoRepayStatusEnum.getNameValue(
                    Integer.valueOf(orderRepayListRes.getBoRepayStatus())));
            row.add(RepayStatusEnum.getNameValue(
                    Integer.valueOf(orderRepayListRes.getRepayStatus())));
            row.add(orderRepayListRes.getBrRepayTime());
            rows.add(row);
        });
        data.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response, "待还款明细.xlsx", data);
        } catch (Exception e) {
            logger.error("申请查询导出异常:", e);
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }


    }


    @Override
    public ResponseResult orderRepayPlanCal(RepayPlanCalReq repayPlanCalReq) {

        BorrowProduct borrowProduct = borrowProductDao.selByPcode(repayPlanCalReq.getpCode());

        AbstractCarRepayPlan carRepayPlan = carRepayPlanFactory
                .getCarRepayPlan(ProductPayTypeEnum.getProductPayType(borrowProduct.getpPayType()));
        if (carRepayPlan == null) {
            logger.error("orderRepayPlanCal:pCode is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());

        }
        BoCarRepayPlanRes carRepayPlanRes = carRepayPlan.planCal(repayPlanCalReq);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), carRepayPlanRes);
    }

    @Override
    public ResponseResult orderRepayPlan(OrderRepayPlanReq orderRepayPlanReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.valueOf(orderRepayPlanReq.getOrderId()));
        if (borrowOrder == null) {
            logger.error("orderRepayPlan:orderid is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        List<BoOrderCost> boOrderCosts = boOrderCostDao.selByOrderId(Long.valueOf(orderRepayPlanReq.getOrderId()));
        Map<String, BigDecimal> orderCostsMap = new HashMap<>();
        boOrderCosts.stream().forEach(boOrderCost -> {
            orderCostsMap.put(boOrderCost.getCostKey(), boOrderCost.getCostAmount());
        });
        List<BorrowRepayment> repaymentList = borrowRepaymentDao.selByOrderId(Long.valueOf(orderRepayPlanReq.getOrderId()));
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(borrowOrder.getpCode());
        List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowProduct.getUuid());
        Map<String, String> productRateMap = new HashMap<>();
        boProductRates.stream().forEach(boProductRate -> {
            productRateMap.put(boProductRate.getRateKey(), boProductRate.getRateValue());
        });
        BoCarRepayPlanRes repayPlanRes = new BoCarRepayPlanRes();
        repayPlanRes.setBoPrice(borrowOrder.getBoPrice().toString());
        repayPlanRes.setBoExpect(borrowOrder.getBoExpect().toString());
        BigDecimal gps = orderCostsMap.get(ProductRateEnum.GPS_COST.getRateKey());
        repayPlanRes.setGpsCost(
                gps == null ? BigDecimal.ZERO.toString() : gps.toString());
        repayPlanRes.setEarlyServiceRate(productRateMap.get(ProductRateEnum.EARLY_SERVICE_RATE.getRateKey()));

        repayPlanRes.setBoFinishPrice(borrowOrder.getBoFinishPrice().toString());
        BigDecimal serviceCost = orderCostsMap.get(ProductRateEnum.EARLY_SERVICE_COST.getRateKey());
        repayPlanRes.setEarlyServiceCost(
                serviceCost == null ? BigDecimal.ZERO.toString() : serviceCost.toString()
        );
        List<CarRepayPlanVo> repayPlans = new ArrayList<>();

        repaymentList.stream().forEach(repayment -> {
            CarRepayPlanVo carRepayPlanVo = new CarRepayPlanVo();
            carRepayPlanVo.setRepayExpect(repayment.getRepayExpect().toString());
            carRepayPlanVo.setBrTime(repayment.getBrTime());
            carRepayPlanVo.setRepayAmount(repayment.getRepayAmount().toString());
            carRepayPlanVo.setCapitalAmount(repayment.getCapitalAmount().toString());
            carRepayPlanVo.setInterestAmount(repayment.getInterestAmount().toString());
            carRepayPlanVo.setServiceFee(repayment.getServiceFee().toString());
            repayPlans.add(carRepayPlanVo);
        });
        repayPlanRes.setRepayPlans(repayPlans);

        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), repayPlanRes);
    }

    @Override
    public ResponseResult selRepayListDetail(RepayListDetailReq repayListDetailReq) {
        long orderId = Long.valueOf(repayListDetailReq.getOrderId());
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(orderId);
        if (borrowOrder == null) {
            logger.error("selRepayListDetail:orderid is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        List<BorrowRepayment> repaymentList = borrowRepaymentDao.selByOrderId(orderId);
        UserInfo userInfo = userInfoDao.selInfoByUid(borrowOrder.getUserUid());

        RepayListDetailRes detailRes = new RepayListDetailRes();
        detailRes.setOrderId(repayListDetailReq.getOrderId());
        detailRes.setUserName(userInfo.getUserName());
        detailRes.setPlateNumber(borrowOrder.getPlateNumber());
        detailRes.setBoPrice(borrowOrder.getBoPrice().toString());
        detailRes.setProductName(borrowOrder.getProductName());
        detailRes.setBoExpect(borrowOrder.getBoExpect().toString());

        List<RepayDetailVo> repayDetailVos = new ArrayList<>();
        repaymentList.stream().forEach(repayment -> {
            RepayDetailVo detailVo = new RepayDetailVo();
            detailVo.setRepayId(repayment.getRepayId().toString());
            detailVo.setRepayExpect(repayment.getRepayExpect().toString());
            detailVo.setBrTime(repayment.getBrTime());

            BigDecimal repayAmount = repayment.getCapitalAmount()
                    .add(repayment.getInterestAmount())
                    .add(repayment.getServiceFee())
                    .add(repayment.getPunishAmount())
                    .add(repayment.getFineAmount());
            detailVo.setRepayAmount(repayAmount.toString());
            detailVo.setCapitalAmount(repayment.getCapitalAmount().toString());
            detailVo.setInterestAmount(repayment.getInterestAmount().toString());
            detailVo.setServiceFee(repayment.getServiceFee().toString());
            detailVo.setPunishAmount(repayment.getPunishAmount().toString());
            detailVo.setRepayStatus(repayment.getRepayStatus().toString());
            detailVo.setBrRepayTime(repayment.getBrRepayTime());
            BigDecimal finishAmount = repayment.getRepayFinishAmount();
            detailVo.setRepayFinishAmount(finishAmount == null ? BigDecimal.ZERO.toString() : finishAmount.toString());
            detailVo.setRepayType(repayment.getRepayType().toString());
            detailVo.setFineAmount(repayment.getFineAmount().toString());
            repayDetailVos.add(detailVo);
        });
        detailRes.setRepayDetails(repayDetailVos);
        List<OrderPayRecordVo> payRecordVos = new ArrayList<>();
        List<BoOrderPayRecord> payRecordList = boOrderPayRecordDao.selInfoByOrderId(orderId);
        payRecordList.stream().forEach(boOrderPayRecord -> {
            OrderPayRecordVo payRecordVo = new OrderPayRecordVo();
            payRecordVo.setOrderId(boOrderPayRecord.getOrderId().toString());
            payRecordVo.setOrderPayId(boOrderPayRecord.getOrderPayId().toString());
            payRecordVo.setPayPrice(boOrderPayRecord.getPayPrice().toString());
            payRecordVo.setPayTime(boOrderPayRecord.getPayTime());
            payRecordVo.setPayType(boOrderPayRecord.getPayType().toString());
            payRecordVo.setPayTypeDesc(boOrderPayRecord.getPayTypeDesc());
            payRecordVos.add(payRecordVo);
        });
        detailRes.setOrderPayRecords(payRecordVos);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), detailRes);
    }

    @Override
    @Transactional
    public ResponseResult orderRepayOver(OrderPayOverReq orderPayOverReq) {
        long repayId = Long.valueOf(orderPayOverReq.getRepayId());
        BorrowRepayment repayment = borrowRepaymentDao.selByRepayId(repayId);
        if (repayment == null) {
            logger.error("orderRepayOver:repayId is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        if (!orderPayOverReq.getOrderId().equals(repayment.getOrderId().toString())) {
            logger.error("orderRepayOver:orderId is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        List<BorrowRepayment> repayments = borrowRepaymentDao.selByOrderId(repayment.getOrderId());
        int orderPayState = 0;
        if (repayment.getBoRepayStatus() == BoRepayStatusEnum.OVERDUE.getCode()) {
            orderPayState = BoRepayStatusEnum.NORMAL.getCode();
        }
        for (BorrowRepayment bre : repayments) {
            if (bre.getRepayExpect() < repayment.getRepayExpect()) {
                if (bre.getRepayStatus() == RepayStatusEnum.PAY_NO.getCode()) {
                    logger.error("orderRepayOver repayexpect is error : repayId={},repayStatus={}");
                    return ResponseResult.error(ExceptionCode.EXPECT_ERROR.getErrorCode()
                            , ExceptionCode.EXPECT_ERROR.getErrorMessage());
                }
            }
            if (bre.getRepayStatus() == RepayStatusEnum.PAY_NO.getCode()
                    && bre.getBoRepayStatus() == BoRepayStatusEnum.OVERDUE.getCode()
                    && bre.getRepayId() != repayId) {
                orderPayState = BoRepayStatusEnum.OVERDUE.getCode();
            }
        }
        if (repayment.getRepayAmount().compareTo(BigDecimal.ZERO) > 0) {
            UserInfo userInfo = userInfoDao.selInfoByUid(repayment.getUserUid());

            // 资金划拨
            XMap thirdParamMap = new XMap();
            thirdParamMap.put(PlatformConstant.FundsParam.LOAN_ID, String.valueOf(repayment.getOrderId()));
            thirdParamMap.put(PlatformConstant.FundsParam.REPAY_ID, String.valueOf(repayment.getRepayId()));
            thirdParamMap.put(PlatformConstant.FundsParam.PERIOD, String.valueOf(repayment.getRepayExpect()));
            thirdParamMap.put(PlatformConstant.FundsParam.REPAY_DATE, Utility.dateStr(repayment.getBrTime()));
            thirdParamMap.put(PlatformConstant.FundsParam.AMOUNT, repayment.getCapitalAmount().toString());
            thirdParamMap.put(PlatformConstant.FundsParam.INTEREST, repayment.getInterestAmount().toString());
            if (repayment.getBoRepayStatus() == BoRepayStatusEnum.OVERDUE.getCode()
                    || repayment.getSuretyStatus() == SuretyStatusEnum.SURETY_STATUS_YES.getCode()) {
                thirdParamMap.put(PlatformConstant.FundsParam.SERVICE_FEE, repayment.getServiceFee().toString());
                thirdParamMap.put(PlatformConstant.FundsParam.PENALTY_FEE, repayment.getPunishAmount().subtract(repayment.getReducePunishAmount()).toString());
                thirdParamMap.put(PlatformConstant.FundsParam.PENALTY_INTEREST, repayment.getFineAmount().subtract(repayment.getReduceFineAmount()).toString());
                thirdParamMap.put(PlatformConstant.FundsParam.OUTID, userInfo.getIdcard());
                thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.LOANER_OVERDUE_REPAY_REQUEST.getUrlType());
            } else {
                thirdParamMap.put(PlatformConstant.FundsParam.MONTH_SERVICE_FEE, repayment.getServiceFee().toString());
                thirdParamMap.put(PlatformConstant.FundsParam.SERVICE_VIOLATE_FEE, repayment.getPunishAmount().subtract(repayment.getReducePunishAmount()).toString());
                thirdParamMap.put(PlatformConstant.FundsParam.OUTID, userInfo.getIdcard());
                thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.ORDER_TRANSFER_FUND.getUrlType());
            }

            ResponseResult<XMap> responseResult = remoteDataCollectorService.collect(thirdParamMap);
            if (!responseResult.isSucceed()) {
                return responseResult;
            }
        }
        BorrowRepayment borrowRepayment = new BorrowRepayment();
        borrowRepayment.setRepayStatus(RepayStatusEnum.PAY_YES.getCode());
        borrowRepayment.setRepayType(RepayTypeEnum.PAY_NORMA.getCode());
        borrowRepayment.setRepayFinishAmount(repayment.getRepayAmount());
        borrowRepayment.setBrRepayTime(new Date());
        borrowRepaymentDao.updateBoRepayment(repayId, borrowRepayment);
        borrowOrderDao.upBoPayExpectCount(repayment.getOrderId());

        BoOrderPayRecord boOrderPayRecord = new BoOrderPayRecord();
        boOrderPayRecord.setUuid(UUIDProvider.uuid());
        boOrderPayRecord.setOrderId(repayment.getOrderId());
        boOrderPayRecord.setOrderPayId(idProvider.genId());
        boOrderPayRecord.setPayTime(new Date());
        boOrderPayRecord.setPayPrice(repayment.getRepayAmount());
        boOrderPayRecord.setPayType(RepayTypeEnum.PAY_NORMA.getCode());
        boOrderPayRecordDao.insertPayOrder(boOrderPayRecord);
        if (orderPayState == BoRepayStatusEnum.NORMAL.getCode()) {
            BorrowOrder borrowOrder = new BorrowOrder();
            borrowOrder.setBoPayState(BoRepayStatusEnum.NORMAL.getCode());
            borrowOrderDao.updateBorrowOrder(repayment.getOrderId(), borrowOrder);
        }
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    private OrderUpRepayCalRes repaCal(long orderId) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(orderId);
        if (borrowOrder == null) {
            logger.error("orderUpRepayCal:orderId is not exist");
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoDao.selInfoByUid(borrowOrder.getUserUid());
        OrderUpRepayCalRes repayCalRes = new OrderUpRepayCalRes();
        String payTotalAmount;
        String payExpect;
        String finishExpect;
        List<BorrowRepayment> calculateOver= new ArrayList<>();
        BigDecimal earlyPayRate = productService.getRate(ProductRateEnum.EARLY_PAY_RATE,borrowOrder.getProductUid());
        List<BorrowRepayment> repaymentList = borrowRepaymentDao.selByOrderId(orderId);

        List<BorrowRepayment> yesRepay = repaymentList.stream()
                .filter(re -> re.getRepayStatus() ==  RepayStatusEnum.PAY_YES.getCode()).collect(Collectors.toList());
        yesRepay.sort(new Comparator<BorrowRepayment>() {
            @Override
            public int compare(BorrowRepayment o1, BorrowRepayment o2) {
                return o2.getRepayExpect() - o1.getRepayExpect();
            }
        });
        List<BorrowRepayment> noRepay = repaymentList.stream()
                .filter(repayment -> repayment.getRepayStatus() == RepayStatusEnum.PAY_NO.getCode()).collect(Collectors.toList());
        if (noRepay.isEmpty()) {
            throw new BorrowException(ExceptionCode.UP_REPAY_CHECK);
        }
        List<BorrowRepayment> overdueRepay = noRepay.stream()
                .filter(bt -> bt.getBoRepayStatus() == BoRepayStatusEnum.OVERDUE.getCode()).collect(Collectors.toList());
        long count = overdueRepay.stream().filter(b-> b.getSuretyStatus() == SuretyStatusEnum.SURETY_STATUS_NO.getCode()).count();
        if (count > 0L) {
            throw new BorrowException(ExceptionCode.OVERDUE_NO_SURETY);
        }
        if (overdueRepay.isEmpty()) {
            noRepay.sort(new Comparator<BorrowRepayment>() {
                @Override
                public int compare(BorrowRepayment o1, BorrowRepayment o2) {
                    return o1.getRepayExpect() - o2.getRepayExpect();
                }
            });
            List<BorrowRepayment> noRepayCopy = new ArrayList<>(noRepay);
            BorrowRepayment currRepayment = noRepayCopy.remove(0);
            BigDecimal currTotalAmount = currRepayment.getInterestAmount()
                    .add(currRepayment.getServiceFee())
                    .add(currRepayment.getPunishAmount())
                    .add(currRepayment.getFineAmount())
                    .add(currRepayment.getCapitalAmount());
            currRepayment.setRepayFinishAmount(currTotalAmount);
            currRepayment.setRepayType(RepayTypeEnum.PAY_NORMA.getCode());

            BigDecimal noPaycapAmount = BigDecimal.valueOf(noRepayCopy.stream()
                    .mapToDouble(b -> b.getCapitalAmount().doubleValue()).sum())
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal earlyPayRateAmount = BigDecimal.ZERO;
            for (BorrowRepayment repayment : noRepayCopy) {
                repayment.setEarlyPayFee(repayment.getCapitalAmount()
                        .multiply(earlyPayRate).setScale(2, BigDecimal.ROUND_HALF_UP));
                earlyPayRateAmount = earlyPayRateAmount.add(repayment.getEarlyPayFee());
                repayment.setRepayFinishAmount(repayment.getCapitalAmount().add(repayment.getEarlyPayFee()));
                repayment.setRepayType(RepayTypeEnum.PAY_UP.getCode());

            }
            payTotalAmount = currTotalAmount
                    .add(noPaycapAmount)
                    .add(earlyPayRateAmount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            finishExpect = yesRepay.isEmpty() ? "0" : yesRepay.get(0).getRepayExpect().toString();
            payExpect = currRepayment.getRepayExpect().toString();
            calculateOver.add(currRepayment);
            calculateOver.addAll(noRepayCopy);
        }else {
            BigDecimal overTotalAmount =
                    BigDecimal.valueOf(overdueRepay.stream().mapToDouble(currRepayment-> currRepayment.getInterestAmount()
                    .add(currRepayment.getServiceFee())
                    .add(currRepayment.getPunishAmount())
                    .add(currRepayment.getFineAmount())
                    .add(currRepayment.getCapitalAmount()).doubleValue()).sum());

            List<BorrowRepayment> noRepayCopy = new ArrayList<>(noRepay);
            noRepayCopy.removeAll(overdueRepay);
            for (BorrowRepayment repayment : overdueRepay) {
                repayment.setRepayFinishAmount(
                        repayment.getInterestAmount()
                                .add(repayment.getServiceFee())
                                .add(repayment.getPunishAmount())
                                .add(repayment.getFineAmount())
                                .add(repayment.getCapitalAmount())

                );
                repayment.setRepayType(RepayTypeEnum.OVERDUE.getCode());
            }
            BorrowRepayment currRepayment = noRepayCopy.remove(0);
            BigDecimal currTotalAmount = currRepayment.getInterestAmount()
                    .add(currRepayment.getServiceFee())
                    .add(currRepayment.getPunishAmount())
                    .add(currRepayment.getFineAmount())
                    .add(currRepayment.getCapitalAmount());
            currRepayment.setRepayFinishAmount(currTotalAmount);
            currRepayment.setRepayType(RepayTypeEnum.PAY_NORMA.getCode());

            BigDecimal noPaycapAmount  = BigDecimal.valueOf(noRepayCopy.stream()
                    .mapToDouble(r-> r.getCapitalAmount().doubleValue()).sum());
            BigDecimal earlyPayRateAmount = BigDecimal.ZERO;
            for (BorrowRepayment repayment : noRepayCopy) {
                repayment.setEarlyPayFee(repayment.getCapitalAmount()
                        .multiply(earlyPayRate).setScale(2, BigDecimal.ROUND_HALF_UP));
                earlyPayRateAmount = earlyPayRateAmount.add(repayment.getEarlyPayFee());
                repayment.setRepayFinishAmount(repayment.getCapitalAmount().add(earlyPayRateAmount));
                repayment.setRepayType(RepayTypeEnum.PAY_UP.getCode());
            }
            payTotalAmount = overTotalAmount.add(currTotalAmount).add(noPaycapAmount).add(earlyPayRateAmount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            finishExpect = yesRepay.isEmpty() ? "0" : yesRepay.get(0).getRepayExpect().toString();
            payExpect = noRepay.get(0).getRepayExpect().toString();
            calculateOver.addAll(overdueRepay);
            calculateOver.add(currRepayment);
            calculateOver.addAll(noRepayCopy);
        }
        repayCalRes.setPayTotalAmount(payTotalAmount);
        repayCalRes.setPayExpect(payExpect);
        repayCalRes.setFinishExpect(finishExpect);
        repayCalRes.setExpectTotal(borrowOrder.getBoExpect().toString());
        repayCalRes.setUserName(userInfo.getUserName());
        repayCalRes.setCalculateOver(calculateOver);

        return repayCalRes;
    }

    @Override
    public ResponseResult orderUpRepayCal(OrderUpRepayCalReq orderUpRepayCalReq) {
        long orderId = Long.valueOf(orderUpRepayCalReq.getOrderId());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), repaCal(orderId));
    }

    @Override
    @Transactional
    public ResponseResult orderUpRepay(OrderUpRepayReq orderUpRepayReq) {
        long orderId = Long.valueOf(orderUpRepayReq.getOrderId());
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(orderId);
        if (borrowOrder == null) {
            logger.error("orderUpRepayCal:orderId is not exist");
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        if (borrowOrder.getBoIsState() == BoIsStateEnum.PAY_OVER.getCode()) {
            logger.error("orderUpRepayCal:orderId is not exist");
            throw new BorrowException(ExceptionCode.ORDER_ERROR);
        }
        OrderUpRepayCalRes repayCalRes = repaCal(orderId);
        List<BorrowRepayment> noRepay = repayCalRes.getCalculateOver();

        //TODO 调用理财端提前还款

        XMap thirdParamMap = new XMap();
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_ID, String.valueOf(orderId));
        thirdParamMap.put(PlatformConstant.FundsParam.REPAY_DATE,Utility.dateStrddHHmmss(new Date()));
        thirdParamMap.put(PlatformConstant.FundsParam.TOTAL_PERIODS, borrowOrder.getBoExpect());
        thirdParamMap.put(PlatformConstant.FundsParam.MUST_PERIODS, borrowOrder.getBoPayExpect()+1);
        thirdParamMap.put(PlatformConstant.FundsParam.ACTUAL_PERIODS, borrowOrder.getBoPayExpect());
        thirdParamMap.put(PlatformConstant.FundsParam.TOTAL_AMOUNT, repayCalRes.getPayTotalAmount());

        List<XMap> list = new ArrayList<>();
        for(BorrowRepayment repayment : noRepay) {
            XMap repaymentsMap = new XMap();
            repaymentsMap.put(PlatformConstant.FundsParam.REPAY_ID, repayment.getRepayId());
            repaymentsMap.put(PlatformConstant.FundsParam.REPAY_DATE,Utility.dateStrddHHmmss(repayment.getBrTime()));
            repaymentsMap.put(PlatformConstant.FundsParam.PERIOD,repayment.getRepayExpect());
            repaymentsMap.put(PlatformConstant.FundsParam.ISCOMPENSATION
                    ,repayment.getSuretyStatus() == SuretyStatusEnum.SURETY_STATUS_YES.getCode() ? true : false);
            repaymentsMap.put(PlatformConstant.FundsParam.CORPUS,repayment.getCapitalAmount());
            repaymentsMap.put(PlatformConstant.FundsParam.INTEREST,repayment.getInterestAmount());
            repaymentsMap.put(PlatformConstant.FundsParam.SERVICE_FEE,repayment.getServiceFee());
            repaymentsMap.put(PlatformConstant.FundsParam.PENALTY_FEE,repayment.getPunishAmount());
            repaymentsMap.put(PlatformConstant.FundsParam.PENALTY_INTEREST,repayment.getFineAmount());
            repaymentsMap.put(PlatformConstant.FundsParam.EARLY_PENALTY_FEE,repayment.getEarlyPayFee());
            if (repayment.getRepayType().equals(RepayTypeEnum.PAY_UP.getCode())) {
                repaymentsMap.put(PlatformConstant.FundsParam.REPAY_TYPE,PlatformConstant.FundsParam.TQ);
                repaymentsMap.put(PlatformConstant.FundsParam.INTEREST,BigDecimal.ZERO);
                repaymentsMap.put(PlatformConstant.FundsParam.SERVICE_FEE,BigDecimal.ZERO);
                repaymentsMap.put(PlatformConstant.FundsParam.PENALTY_FEE,BigDecimal.ZERO);
                repaymentsMap.put(PlatformConstant.FundsParam.PENALTY_INTEREST,BigDecimal.ZERO);
            }else if (repayment.getRepayType().equals(RepayTypeEnum.PAY_NORMA.getCode())){
                repaymentsMap.put(PlatformConstant.FundsParam.REPAY_TYPE,PlatformConstant.FundsParam.ZC);
            }else if (repayment.getRepayType().equals(RepayTypeEnum.OVERDUE.getCode())) {
                repaymentsMap.put(PlatformConstant.FundsParam.REPAY_TYPE,PlatformConstant.FundsParam.YQ);

            }
            list.add(repaymentsMap);
        }
        thirdParamMap.put(PlatformConstant.FundsParam.REPAYMENTS,list);
        thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.LOANER_EARLY_REPAY_REQUEST.getUrlType());
        ResponseResult<XMap> responseResult = remoteDataCollectorService.collect(thirdParamMap);
        if (!responseResult.isSucceed()) {
            return responseResult;
        }
        for (BorrowRepayment br : noRepay) {
            br.setRepayStatus(RepayStatusEnum.PAY_YES.getCode());
            br.setBrRepayTime(new Date());
            borrowOrderDao.upBoPayExpectCount(orderId);
            borrowRepaymentDao.updateBoRepayment(br.getRepayId(), br);
        }
        BoOrderPayRecord boOrder = new BoOrderPayRecord();
        boOrder.setUuid(UUIDProvider.uuid());
        boOrder.setOrderId(orderId);
        boOrder.setOrderPayId(idProvider.genId());
        boOrder.setPayType(RepayTypeEnum.PAY_UP.getCode());
        boOrder.setPayTime(new Date());
        boOrder.setPayPrice(BigDecimal.valueOf(Double.valueOf(repayCalRes.getPayTotalAmount())));
        boOrder.setPayTypeDesc(UpRepayTEnum.UP_AUTO_PAY_AMOUNT.getName());
        boOrderPayRecordDao.insertPayOrder(boOrder);

        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }


    @Override
    public ResponseResult orderRepaySurety(OrderPayOverReq orderPayOverReq) {
        long repayId = Long.valueOf(orderPayOverReq.getRepayId());
        BorrowRepayment repayment = borrowRepaymentDao.selByRepayId(repayId);
        if (repayment == null) {
            logger.error("orderRepaySurety:repayId is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        if (repayment.getRepayAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("orderRepaySurety:RepayAmount is 0");
            return ResponseResult.error(ExceptionCode.AMOUNT_FAIL_ZREO.getErrorCode()
                    , ExceptionCode.AMOUNT_FAIL_ZREO.getErrorMessage());
        }
        if (!orderPayOverReq.getOrderId().equals(repayment.getOrderId().toString())) {
            logger.error("orderRepaySurety:orderId is not exist");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        if (repayment.getRepayStatus() == RepayStatusEnum.PAY_YES.getCode()
                || repayment.getSuretyStatus() == SuretyStatusEnum.SURETY_STATUS_YES.getCode()) {
            logger.error("orderRepaySurety:repaystatus is 2");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        if (repayment.getBrTime().getTime() > new Date().getTime()) {
            logger.error("orderRepaySurety:time is error");
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode()
                    , ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        List<BorrowRepayment> repayments = borrowRepaymentDao.selByOrderId(repayment.getOrderId());
        for (BorrowRepayment bre : repayments) {
            if (bre.getRepayExpect() < repayment.getRepayExpect()) {
                if (bre.getRepayStatus() == RepayStatusEnum.PAY_NO.getCode()) {
                    logger.error("orderRepaySurety repayexpect is error : repayId={},repayStatus={}");
                    return ResponseResult.error(ExceptionCode.EXPECT_ERROR.getErrorCode()
                            , ExceptionCode.EXPECT_ERROR.getErrorMessage());
                }
            }
        }
//         代偿还款
        XMap thirdParamMap = new XMap();
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_ID, String.valueOf(repayment.getOrderId()));
        thirdParamMap.put(PlatformConstant.FundsParam.REPAY_ID, String.valueOf(repayment.getRepayId()));
        thirdParamMap.put(PlatformConstant.FundsParam.PERIOD, String.valueOf(repayment.getRepayExpect()));
        thirdParamMap.put(PlatformConstant.FundsParam.REPAY_DATE, Utility.dateStr(repayment.getBrTime()));
        thirdParamMap.put(PlatformConstant.FundsParam.AMOUNT, repayment.getCapitalAmount().toString());
        thirdParamMap.put(PlatformConstant.FundsParam.INTEREST, repayment.getInterestAmount().toString());
        thirdParamMap.put(PlatformConstant.FundsParam.MONTH_SERVICE_FEE, repayment.getServiceFee().toString());
        thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.COMPENSATORY_REPAY_REQUEST.getUrlType());
        ResponseResult<XMap> responseResult = remoteDataCollectorService.collect(thirdParamMap);
        if (!responseResult.isSucceed()) {
            return responseResult;
        }
        BorrowRepayment repay = new BorrowRepayment();
        repay.setSuretyStatus(SuretyStatusEnum.SURETY_STATUS_YES.getCode());
        repay.setSuretyTime(new Date());
        borrowRepaymentDao.updateBoRepayment(repayId, repay);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    @Override
    public ResponseResult overdueReduce(OverdueReduceReq req) {
        OverdueReduceRes reduceRes = new OverdueReduceRes();
        long repayId = Long.valueOf(req.getRepayId());
        BorrowRepayment repayment = borrowRepaymentDao.selByRepayId(repayId);
        reduceRes.setTotalPunishAmount(repayment.getPunishAmount().toString());
        reduceRes.setTotalFineAmount((repayment.getFineAmount().toString()));
        reduceRes.setReducePunishAmount(repayment.getReducePunishAmount().toString());
        reduceRes.setReduceFineAmount(repayment.getReduceFineAmount().toString());
        reduceRes.setActualPunishAmount(repayment.getPunishAmount()
                .subtract(repayment.getReducePunishAmount()).toString());
        reduceRes.setActualFineAmount(repayment.getFineAmount()
                .subtract(repayment.getReduceFineAmount()).toString());

        List<BoOverdueReduceRecord> reduceRecords = boOverdueReduceRecordDao.selInfoByRepaymentId(req.getRepayId());
        reduceRes.setOverdueReduceRecords(reduceRecords);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), reduceRes);
    }

    @Override
    @Transactional
    public ResponseResult overdueAddReduce(OverdueReduceAddReq reduceAddReq) {
        SysUser sysUser = sysUserDao.selByUserUid(reduceAddReq.getSysUserUid());
        BorrowRepayment repayment = borrowRepaymentDao.selByRepayId(Long.valueOf(reduceAddReq.getRepayId()));

        BigDecimal reducePunishAmountReq = BigDecimal.valueOf(Double.valueOf(reduceAddReq.getPunishAmount()));
        BigDecimal acPushishAmount= repayment.getPunishAmount().subtract(repayment.getReducePunishAmount());

        if (reducePunishAmountReq.compareTo(BigDecimal.ZERO) <0 || reducePunishAmountReq.compareTo(acPushishAmount) > 0) {
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        BigDecimal reduceFineAmountReq = BigDecimal.valueOf(Double.valueOf(reduceAddReq.getFineAmount()));
        BigDecimal acFineAmount= repayment.getFineAmount().subtract(repayment.getReduceFineAmount());

        if (reduceFineAmountReq.compareTo(BigDecimal.ZERO) <0 || reduceFineAmountReq.compareTo(acFineAmount) > 0) {
            return ResponseResult.error(ExceptionCode.PARAM_ERROR.getErrorCode(),ExceptionCode.PARAM_ERROR.getErrorMessage());
        }
        BorrowRepayment repay = new BorrowRepayment();
        repay.setReducePunishAmount(repayment.getReducePunishAmount().add(reducePunishAmountReq));
        repay.setReduceFineAmount(repayment.getReduceFineAmount().add(reduceFineAmountReq));
        borrowRepaymentDao.updateBoRepayment(repayment.getRepayId(),repay);
        BoOverdueReduceRecord reduceRecord = new BoOverdueReduceRecord();
        reduceRecord.setUuid(UUIDProvider.uuid());
        reduceRecord.setBorrowId(repayment.getOrderId().toString());
        reduceRecord.setRepaymentId(repayment.getRepayId().toString());
        reduceRecord.setReducePunishAmount(reducePunishAmountReq);
        reduceRecord.setReduceFineAmount(reduceFineAmountReq);
        reduceRecord.setOperateUserId(sysUser.getUuid());
        reduceRecord.setOperateUserName(sysUser.getLoginName());
        boOverdueReduceRecordDao.insertOverdueReduceRecord(reduceRecord);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selReduceList(RepayReduceListReq reduceListReq) {
        List<RepayReduceListRes> listRes = boOverdueReduceRecordDao.selReduceListWhere(reduceListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<RepayReduceListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), pageBaseRes);
    }
}
