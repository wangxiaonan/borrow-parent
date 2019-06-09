package com.borrow.manage.service.Impl;

import com.alibaba.fastjson.JSON;
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
import org.apache.commons.lang.StringUtils;
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
        UserInfoVo userInfoVo = orderCreateReq.getUserInfo();
        //TODO
        // 判断是否是开户 和是否借款用户
        XMap xMap = new XMap();
        xMap.put(PlatformConstant.FundsParam.IDCARD, userInfoVo.getIdcard());
        xMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.USER_CHECK_DATA.getUrlType());
        ResponseResult<XMap> xMapResponseResult = remoteDataCollectorService.collect(xMap);
        if (!xMapResponseResult.isSucceed()) {
            return xMapResponseResult;
        }
        XMap xMapRes = xMapResponseResult.getData();
        String data = xMapRes.getString("data");
        XMap p = JSON.parseObject(data, XMap.class);
        String isOpen = p.getString(PlatformConstant.FundsParam.ISOPEN);
        String userType = p.getString(PlatformConstant.FundsParam.USER_TYPE);
        if (!PlatformConstant.FundsParam.ISOPEN_YES.equals(isOpen)) {
            throw new BorrowException(ExceptionCode.USER_CHECK_OPEN);
        }
        if (!PlatformConstant.FundsParam.USER_TYPE_LOAN.equals(userType)) {
            throw new BorrowException(ExceptionCode.USER_CHECK_IDENTITY);
        }

//        UserInfo userInfo = userInfoDao.selInfoByIdcard(userInfoVo.getIdcard());
//        if (userInfo == null) {
        UserInfo userInfo = convertUserInfoVo(userInfoVo);
        userInfoDao.insertUserInfo(userInfo);
//        }
        BorrowSalesmanVo borrowSalesmanVo = orderCreateReq.getBorrowSalesman();
//        BorrowSalesman borrowSalesman = borrowSalesmanDao.selByMobile(borrowSalesmanVo.getSalesMobile());
//        if (borrowSalesman == null) {
        BorrowSalesman borrowSalesman = convertBorrowSalesmanVo(borrowSalesmanVo);
        borrowSalesmanDao.insertBorrowSalesman(borrowSalesman);
//        }
        UserCarVo userCarVo = orderCreateReq.getUserCar();
        UserCar userCar = null;
        if (orderCreateReq.getBussType() == BussTypeEnum.CARD.getCode()) {
            userCar = convertUserCarVo(userCarVo, userInfo.getUuid());
            userCarDao.insertUserCar(userCar);
        }
        BorrowOrder borrowOrder = new BorrowOrder();
        borrowOrder.setUuid(UUIDProvider.uuid());
        borrowOrder.setOrderId(idProvider.genId());
        borrowOrder.setBuesType(orderCreateReq.getBussType());
        borrowOrder.setUserUid(userInfo.getUuid());
        borrowOrder.setProductUid(borrowProduct.getUuid());
        borrowOrder.setProductName(borrowProduct.getpName());
        borrowOrder.setpCode(borrowProduct.getpCode());
        borrowOrder.setbCarUid(userCar == null ? "" : userCar.getUuid());
        borrowOrder.setPlateNumber(userCar == null ? "" : userCar.getPlateNumber());
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
        BoOrderItem boOrderItem = new BoOrderItem();
        boOrderItem.setUuid(UUIDProvider.uuid());
        boOrderItem.setOrderId(borrowOrder.getOrderId());
        boOrderItem.setItemKey(BoOrderItemEnum.BO_SOURCE.getItemKey());
        boOrderItem.setItemValue(orderCreateReq.getBoOrderItem().getBoSource());
        boOrderItem.setItemDesc(BoOrderItemEnum.BO_SOURCE.getItemDesc());
        Map<String, String> imageUrl = orderCreateReq.getBoOrderItem().getImageUrl();
        boOrderItemDao.insertItem(boOrderItem);
        if (orderCreateReq.getBussType() == BussTypeEnum.CARD.getCode() && imageUrl != null) {
            imageUrl.forEach((k, v) -> {
                String s = BoOrderCarItem.gettemDesc(k);
                BoOrderItem boOrderItemTemp = new BoOrderItem();
                boOrderItemTemp.setUuid(UUIDProvider.uuid());
                boOrderItemTemp.setOrderId(borrowOrder.getOrderId());
                boOrderItemTemp.setItemKey(k);
                boOrderItemTemp.setItemValue(v);
                boOrderItemTemp.setItemDesc(s);
                if (StringUtils.isNotEmpty(s)) {
                    boOrderItemDao.insertItem(boOrderItemTemp);
                }
            });
        }
        UserHouseInfoVo houseInfoVo = orderCreateReq.getUserHouseInfo();
        if (orderCreateReq.getBussType() == BussTypeEnum.HOUSE.getCode() ) {
            BoOrderItem boOrderName = new BoOrderItem();
            boOrderName.setUuid(UUIDProvider.uuid());
            boOrderName.setOrderId(borrowOrder.getOrderId());
            boOrderName.setItemKey(BoOrderHouseItem.HOUSE_NAME.getItemKey());
            boOrderName.setItemValue(houseInfoVo.getHouseName());
            boOrderName.setItemDesc(BoOrderHouseItem.HOUSE_NAME.getItemDesc());
            boOrderItemDao.insertItem(boOrderName);
            BoOrderItem boHousePart = new BoOrderItem();
            boHousePart.setUuid(UUIDProvider.uuid());
            boHousePart.setOrderId(borrowOrder.getOrderId());
            boHousePart.setItemKey(BoOrderHouseItem.HOUSE_PART.getItemKey());
            boHousePart.setItemValue(houseInfoVo.getHousePart());
            boHousePart.setItemDesc(BoOrderHouseItem.HOUSE_PART.getItemDesc());
            boOrderItemDao.insertItem(boHousePart);
            BoOrderItem boHouseNum = new BoOrderItem();
            boHouseNum.setUuid(UUIDProvider.uuid());
            boHouseNum.setOrderId(borrowOrder.getOrderId());
            boHouseNum.setItemKey(BoOrderHouseItem.HOUSE_NUM.getItemKey());
            boHouseNum.setItemValue(houseInfoVo.getHouseNum());
            boHouseNum.setItemDesc(BoOrderHouseItem.HOUSE_NUM.getItemDesc());
            boOrderItemDao.insertItem(boHouseNum);
            BoOrderItem boHouseArea = new BoOrderItem();
            boHouseArea.setUuid(UUIDProvider.uuid());
            boHouseArea.setOrderId(borrowOrder.getOrderId());
            boHouseArea.setItemKey(BoOrderHouseItem.HOUSE_AREA.getItemKey());
            boHouseArea.setItemValue(houseInfoVo.getHouseArea());
            boHouseArea.setItemDesc(BoOrderHouseItem.HOUSE_AREA.getItemDesc());
            boOrderItemDao.insertItem(boHouseArea);

            BoOrderItem boHouseAttr = new BoOrderItem();
            boHouseAttr.setUuid(UUIDProvider.uuid());
            boHouseAttr.setOrderId(borrowOrder.getOrderId());
            boHouseAttr.setItemKey(BoOrderHouseItem.HOUSE_ATTR.getItemKey());
            boHouseAttr.setItemValue(houseInfoVo.getHouseAttr());
            boHouseAttr.setItemDesc(BoOrderHouseItem.HOUSE_ATTR.getItemDesc());
            boOrderItemDao.insertItem(boHouseAttr);

            BoOrderItem boHouseAddress = new BoOrderItem();
            boHouseAddress.setUuid(UUIDProvider.uuid());
            boHouseAddress.setOrderId(borrowOrder.getOrderId());
            boHouseAddress.setItemKey(BoOrderHouseItem.HOUSE_ADDRESS.getItemKey());
            boHouseAddress.setItemValue(houseInfoVo.getHouseAddress());
            boHouseAddress.setItemDesc(BoOrderHouseItem.HOUSE_ADDRESS.getItemDesc());
            boOrderItemDao.insertItem(boHouseAddress);

            BoOrderItem boHouseDate = new BoOrderItem();
            boHouseDate.setUuid(UUIDProvider.uuid());
            boHouseDate.setOrderId(borrowOrder.getOrderId());
            boHouseDate.setItemKey(BoOrderHouseItem.HOUSE_DATE.getItemKey());
            boHouseDate.setItemValue(houseInfoVo.getHouseDate());
            boHouseDate.setItemDesc(BoOrderHouseItem.HOUSE_DATE.getItemDesc());
            boOrderItemDao.insertItem(boHouseDate);

            BoOrderItem boHousePrice = new BoOrderItem();
            boHousePrice.setUuid(UUIDProvider.uuid());
            boHousePrice.setOrderId(borrowOrder.getOrderId());
            boHousePrice.setItemKey(BoOrderHouseItem.HOUSE_PRICE.getItemKey());
            boHousePrice.setItemValue(houseInfoVo.getHousePrice());
            boHousePrice.setItemDesc(BoOrderHouseItem.HOUSE_PRICE.getItemDesc());
            boOrderItemDao.insertItem(boHousePrice);

            BoOrderItem boHouseidcardPicUrl = new BoOrderItem();
            boHouseidcardPicUrl.setUuid(UUIDProvider.uuid());
            boHouseidcardPicUrl.setOrderId(borrowOrder.getOrderId());
            boHouseidcardPicUrl.setItemKey(BoOrderHouseItem.HOUSE_IDCARD_PIC_URL.getItemKey());
            boHouseidcardPicUrl.setItemValue(houseInfoVo.getHouseidcardPicUrl());
            boHouseidcardPicUrl.setItemDesc(BoOrderHouseItem.HOUSE_IDCARD_PIC_URL.getItemDesc());
            if (!StringUtils.isEmpty(boHouseidcardPicUrl.getItemValue())) {
                boOrderItemDao.insertItem(boHouseidcardPicUrl);
            }
            BoOrderItem boHousePicUrl = new BoOrderItem();
            boHousePicUrl.setUuid(UUIDProvider.uuid());
            boHousePicUrl.setOrderId(borrowOrder.getOrderId());
            boHousePicUrl.setItemKey(BoOrderHouseItem.HOUSE_PIC_URL.getItemKey());
            boHousePicUrl.setItemValue(houseInfoVo.getHousePicUrl());
            boHousePicUrl.setItemDesc(BoOrderHouseItem.HOUSE_PIC_URL.getItemDesc());

            if (!StringUtils.isEmpty(boHousePicUrl.getItemValue())) {
                boOrderItemDao.insertItem(boHousePicUrl);
            }

            BoOrderItem boHouseAuthorityCardPicUrl = new BoOrderItem();
            boHouseAuthorityCardPicUrl.setUuid(UUIDProvider.uuid());
            boHouseAuthorityCardPicUrl.setOrderId(borrowOrder.getOrderId());
            boHouseAuthorityCardPicUrl.setItemKey(BoOrderHouseItem.HOUSE_AUTHORITY_CARD_PIC_URL.getItemKey());
            boHouseAuthorityCardPicUrl.setItemValue(houseInfoVo.getHouseAuthorityCardPicUrl());
            boHouseAuthorityCardPicUrl.setItemDesc(BoOrderHouseItem.HOUSE_AUTHORITY_CARD_PIC_URL.getItemDesc());
            if (!StringUtils.isEmpty(boHouseAuthorityCardPicUrl.getItemValue())) {
                boOrderItemDao.insertItem(boHouseAuthorityCardPicUrl);
            }


            BoOrderItem boHouseGuaranteePicUrl = new BoOrderItem();
            boHouseGuaranteePicUrl.setUuid(UUIDProvider.uuid());
            boHouseGuaranteePicUrl.setOrderId(borrowOrder.getOrderId());
            boHouseGuaranteePicUrl.setItemKey(BoOrderHouseItem.HOUSE_GUARANTEE_PIC_URL.getItemKey());
            boHouseGuaranteePicUrl.setItemValue(houseInfoVo.getHouseGuaranteePicUrl());
            boHouseGuaranteePicUrl.setItemDesc(BoOrderHouseItem.HOUSE_GUARANTEE_PIC_URL.getItemDesc());

            if (!StringUtils.isEmpty(boHouseGuaranteePicUrl.getItemValue())) {
                boOrderItemDao.insertItem(boHouseGuaranteePicUrl);
            }


            BoOrderItem boHouseLetterCommitmentPicUrl = new BoOrderItem();
            boHouseLetterCommitmentPicUrl.setUuid(UUIDProvider.uuid());
            boHouseLetterCommitmentPicUrl.setOrderId(borrowOrder.getOrderId());
            boHouseLetterCommitmentPicUrl.setItemKey(BoOrderHouseItem.HOUSE_LETTER_COMMITMENT_PIC_URL.getItemKey());
            boHouseLetterCommitmentPicUrl.setItemValue(houseInfoVo.getHouseLetterCommitmentPicUrl());
            boHouseLetterCommitmentPicUrl.setItemDesc(BoOrderHouseItem.HOUSE_LETTER_COMMITMENT_PIC_URL.getItemDesc());

            if (!StringUtils.isEmpty(boHouseLetterCommitmentPicUrl.getItemValue())) {
                boOrderItemDao.insertItem(boHouseLetterCommitmentPicUrl);
            }

            BoOrderItem boHouseAuthOtherPicurl = new BoOrderItem();
            boHouseAuthOtherPicurl.setUuid(UUIDProvider.uuid());
            boHouseAuthOtherPicurl.setOrderId(borrowOrder.getOrderId());
            boHouseAuthOtherPicurl.setItemKey(BoOrderHouseItem.HOUSE_AUTH_OTHER_PIC_URL.getItemKey());
            boHouseAuthOtherPicurl.setItemValue(houseInfoVo.getHouseAuthOtherPicurl());
            boHouseAuthOtherPicurl.setItemDesc(BoOrderHouseItem.HOUSE_AUTH_OTHER_PIC_URL.getItemDesc());
            if (!StringUtils.isEmpty(boHouseAuthOtherPicurl.getItemValue())) {
                boOrderItemDao.insertItem(boHouseAuthOtherPicurl);
            }

        }

        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    @Override
    public ResponseResult orderSelList(OrderListReq orderListReq) {
        List<OrderListRes> listRes = borrowOrderDao.selOrderListWhere(orderListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<OrderListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), pageBaseRes);

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
            ExportExcelUtils.exportExcel(response, "借款申请.xlsx", data);
        } catch (Exception e) {
            logger.error("申请查询导出异常:", e);
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
    }

    @Override
    public ResponseResult orderPaySelList(OrderPayListReq orderPayListReq) {
        List<OrderPayListRes> listRes = borrowOrderDao.selOrderPayListWhere(orderPayListReq);
        PageBaseRes pageBaseRes = new PageBaseRes<OrderPayListRes>(listRes);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), pageBaseRes);
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
            ExportExcelUtils.exportExcel(response, "还款综合查询.xlsx", data);
        } catch (Exception e) {
            logger.error("申请查询导出异常:", e);
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult makeLoans(MakeLoansReq makeLoansReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(makeLoansReq.getOrderId()));
        if (borrowOrder == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(borrowOrder.getpCode());
        AbstractCarRepayPlan abstractCarRepayPlan = carRepayPlanFactory.getCarRepayPlan(ProductPayTypeEnum.getProductPayType(borrowProduct.getpPayType()));
        RepayPlanCalReq repayPlanCalReq = new RepayPlanCalReq();
        repayPlanCalReq.setpCode(borrowOrder.getpCode());
        repayPlanCalReq.setBoPrice(borrowOrder.getBoPrice());
        BoCarRepayPlanRes repayPlanRes = abstractCarRepayPlan.planCal(repayPlanCalReq);
        orderCostPlan(repayPlanRes, borrowOrder);
        List<BorrowRepayment> repaymentsResult = orderRepaymentPlan(repayPlanRes, borrowOrder);

        BorrowOrder bo = new BorrowOrder();
        bo.setBoIsState(BoIsStateEnum.LOAN_OVER.getCode());
        bo.setBoFinishPrice(BigDecimal.valueOf(Double.valueOf(repayPlanRes.getBoFinishPrice())));
        bo.setBoIsFinish(BoIsFinishEnum.FINISH_YES.getCode());
        bo.setBoFinishTime(new Date());
        bo.setFirstExpectTime(repaymentsResult.get(0).getBrTime());
        bo.setLastExpectTime(repaymentsResult.get(repaymentsResult.size() - 1).getBrTime());
        borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(), bo);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    @Override
    public ResponseResult orderCancel(OrderCancelReq orderCancelReq) {
        borrowOrderDao.upBorrowOrderByOrderId(Long.valueOf(orderCancelReq.getOrderId()), BoIsStateEnum.LOAN_CANCEL.getCode());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    @Override
    public ResponseResult orderLoaning(String orderId) {
        borrowOrderDao.upBorrowOrderByOrderId(Long.valueOf(orderId), BoIsStateEnum.LOANING.getCode());
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), null);
    }

    @Override
    public ResponseResult makeRaise(MakeLoansReq makeLoansReq) {
        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(makeLoansReq.getOrderId()));
        if (borrowOrder == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        XMap<String, Object> thirdParamMap = new XMap<String, Object>();
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_NO, borrowOrder.getOrderId().toString());

        BorrowProduct borrowProduct = borrowProductDao.selByPUid(borrowOrder.getProductUid());
        thirdParamMap.put(PlatformConstant.FundsParam.PRODUCT_NAME, borrowProduct.getpName());
        thirdParamMap.put(PlatformConstant.FundsParam.REPAY_MODE, String.valueOf(borrowOrder.getBoPaytype()));
        thirdParamMap.put(PlatformConstant.FundsParam.CLOSE_PERIOD, borrowOrder.getBoExpect().toString());

        List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowProduct.getUuid());
        Map<String, String> mapRate = new HashMap();
        boProductRates.stream().forEach(boProductRate -> {
            mapRate.put(boProductRate.getRateKey(), boProductRate.getRateValue());
        });
        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_SERVICE_RATE, mapRate.get(ProductRateEnum.EARLY_SERVICE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.MONTH_SERVICE_RATE, mapRate.get(ProductRateEnum.MONTH_SERVICE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.MONTH_ACCRUAL_RATE, mapRate.get(ProductRateEnum.MONTH_ACCRUAL_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.GURANTEE_VIOLATE_RATE, mapRate.get(ProductRateEnum.GUARANTEE_VIOLATE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.SERVICE_VIOLATE_RATE, mapRate.get(ProductRateEnum.SERVICE_VIOLATE_RATE.getRateKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_PAY_RATE, mapRate.get(ProductRateEnum.EARLY_PAY_RATE.getRateKey()));

        String gpsCost = mapRate.get(ProductRateEnum.GPS_COST.getRateKey());
        BigDecimal earlyServiceRate = BigDecimal.valueOf(Double.valueOf(thirdParamMap.getString(PlatformConstant.FundsParam.EARLY_SERVICE_RATE)));
        BigDecimal earlyServiceCost = borrowOrder.getBoPrice().multiply(earlyServiceRate);

        thirdParamMap.put(PlatformConstant.FundsParam.EARLY_SERVICE_FEE, earlyServiceCost.toString());
        thirdParamMap.put(PlatformConstant.FundsParam.GPS_COST, gpsCost);

        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_TYPE, borrowOrder.getBuesType().toString());
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_AMT, borrowOrder.getBoPrice().toString());

        UserInfo userInfo = userInfoDao.selInfoByUid(borrowOrder.getUserUid());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_ID, userInfo.getIdcard());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_NAME, userInfo.getUserName());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_PHONE, userInfo.getMobile());
        thirdParamMap.put(PlatformConstant.FundsParam.LOANER_INDUSTRY, userInfo.getWorkNature());
        thirdParamMap.put(PlatformConstant.FundsParam.JOB_DESC, userInfo.getWorkNature());
        thirdParamMap.put(PlatformConstant.FundsParam.INCOMING_DESC, userInfo.getUserEarns());
        thirdParamMap.put(PlatformConstant.FundsParam.CREDIT_INVESTIGATION, userInfo.getCreditDec());

        List<BoOrderItem> boOrderItems = boOrderItemDao.selByorderId(borrowOrder.getOrderId());
        Map<String, String> itemkeys = new HashMap();
        boOrderItems.stream().forEach(boOrderItem -> {
            itemkeys.put(boOrderItem.getItemKey(), boOrderItem.getItemValue());
        });

        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_DESC, itemkeys.get(BoOrderItemEnum.BO_SOURCE.getItemKey()));
        thirdParamMap.put(PlatformConstant.FundsParam.GUARANTEE_INFO_DESC, borrowOrder.getBoPaySource());

        List<BoOrderAudit> boOrderAudits = boOrderAuditDao.selByOrderId(borrowOrder.getOrderId());
        Map auditsUrlkeys = new HashMap();
        boOrderAudits.stream().forEach(orderAudit -> {
            auditsUrlkeys.put(orderAudit.getAuditKey(), orderAudit.getAuditFileUrl());
        });
        List<AuditStatusVo> mapList = new ArrayList<>();
        boOrderAudits.stream().forEach(boOrderAudit -> {
            AuditStatusVo statusVo = new AuditStatusVo();
            statusVo.setId(OrderAuditEnum.getAuthCodeByKey(boOrderAudit.getAuditKey()));
            statusVo.setAudit(OrderAuditEnum.getAuthNameByKey(boOrderAudit.getAuditKey()));
            mapList.add(statusVo);
        });
        thirdParamMap.put(PlatformConstant.FundsParam.AUDIT, mapList);
        XMap loanCarInfoMap = new XMap();
        if (borrowProduct.getBussType()  == BussTypeEnum.CARD.getCode()) {
            UserCar userCar = userCarDao.selByPlateNO(borrowOrder.getUserUid(), borrowOrder.getPlateNumber());
            loanCarInfoMap.put(PlatformConstant.FundsParam.CAR_MODEL, userCar.getCarModel());
            loanCarInfoMap.put(PlatformConstant.FundsParam.COLOR, userCar.getCarColor());
            loanCarInfoMap.put(PlatformConstant.FundsParam.REGISTTIME, userCar.getSignTime());
            loanCarInfoMap.put(PlatformConstant.FundsParam.ESTIMATEVALUE, userCar.getAssessmentPrice());
            loanCarInfoMap.put(PlatformConstant.FundsParam.CAR_NUMBER, userCar.getPlateNumber());
            loanCarInfoMap.put(PlatformConstant.FundsParam.CAR_MILEAGE, userCar.getMileageDesc());
        }
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_CAR_INFO, loanCarInfoMap);
        XMap borrowerInfoMap = new XMap();
        borrowerInfoMap.put(PlatformConstant.FundsParam.LOANER_NAME, userInfo.getUserName());
        borrowerInfoMap.put(PlatformConstant.FundsParam.LOANER_PHONE, userInfo.getMobile());
        borrowerInfoMap.put(PlatformConstant.FundsParam.LOANER_INDUSTRY, userInfo.getWorkNature());
        borrowerInfoMap.put(PlatformConstant.FundsParam.JOB_DESC, userInfo.getWorkNature());
        borrowerInfoMap.put(PlatformConstant.FundsParam.INCOMING_DESC, userInfo.getUserEarns());
        borrowerInfoMap.put(PlatformConstant.FundsParam.CREDIT_INVESTIGATION, userInfo.getCreditDec());
        borrowerInfoMap.put(PlatformConstant.FundsParam.MARRIAGE_STATUS, userInfo.getMarriageStatus());
        borrowerInfoMap.put(PlatformConstant.FundsParam.CHILDREN_DESC, userInfo.getChildrenDesc());
        borrowerInfoMap.put(PlatformConstant.FundsParam.DEBT_DESC, userInfo.getLiabilitiesDesc());
        borrowerInfoMap.put(PlatformConstant.FundsParam.GURANTEE, userInfo.getGuaranteeDesc());
        borrowerInfoMap.put(PlatformConstant.FundsParam.FIRSTREPAY, borrowOrder.getBoPaySource());
        thirdParamMap.put(PlatformConstant.FundsParam.BORROWER_INFO, borrowerInfoMap);
        //房产信息
        XMap loanHuoseInfo = new XMap();
        if (borrowProduct.getBussType()  == BussTypeEnum.HOUSE.getCode()) {
            loanHuoseInfo.put(PlatformConstant.FundsParam.OWNER, itemkeys.get(BoOrderHouseItem.HOUSE_NAME.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.COOWNER, itemkeys.get(BoOrderHouseItem.HOUSE_PART.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.HUOSENO, itemkeys.get(BoOrderHouseItem.HOUSE_NUM.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.HUOSEAREA, itemkeys.get(BoOrderHouseItem.HOUSE_AREA.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.HUOSETYPE, itemkeys.get(BoOrderHouseItem.HOUSE_ATTR.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.REGISTTIME, itemkeys.get(BoOrderHouseItem.HOUSE_DATE.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.HUOSEADDRESS, itemkeys.get(BoOrderHouseItem.HOUSE_ADDRESS.getItemKey()));
            loanHuoseInfo.put(PlatformConstant.FundsParam.ESTIMATEVALUE, itemkeys.get(BoOrderHouseItem.HOUSE_PRICE.getItemKey()));
        }
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_HUOSE_INFO, loanHuoseInfo);
        //图片添加 车贷
        List<LoanPicInfoVo> picInfoVos = new ArrayList<>();
        if (borrowProduct.getBussType()  == BussTypeEnum.CARD.getCode()) {
            boOrderItems.stream().forEach(boOrderItem -> {
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_IDARD.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_VEHICLE_LICENSE.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.POLLING_LICENSE.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.CAR_MILEAGE.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.INSURANCE_POLICY.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.LETTER_COMMITMENT.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_OTHER.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
            });
        }
        if (borrowProduct.getBussType()  == BussTypeEnum.HOUSE.getCode()) {
            boOrderItems.stream().forEach(boOrderItem -> {
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_IDCARD_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_AUTHORITY_CARD_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_GUARANTEE_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_LETTER_COMMITMENT_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_AUTH_OTHER_PIC_URL.getItemKey())) {
                    LoanPicInfoVo loanPicInfo = new LoanPicInfoVo();
                    loanPicInfo.setName(boOrderItem.getItemDesc());
                    loanPicInfo.setUrl(boOrderItem.getItemValue());
                    picInfoVos.add(loanPicInfo);
                }
            });
        }
        thirdParamMap.put(PlatformConstant.FundsParam.LOAN_PIC_INFO, picInfoVos);

        thirdParamMap.put(DataClientEnum.URL_TYPE.getUrlType(), DataClientEnum.ORDER_MAKE_RAISE.getUrlType());
        //TODO 筹标
        ResponseResult<XMap> responseResult = remoteDataCollectorService.collect(thirdParamMap);
        if (!responseResult.isSucceed()) {
            throw new BorrowException(ExceptionCode.ORDER_MAKE_RAISE_ERROR);
        }
        BorrowOrder borr = new BorrowOrder();
        borr.setBoIsState(BoIsStateEnum.LOANING.getCode());
        borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(), borr);
//        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),null);
        return responseResult;
    }

    @Override
    public ResponseResult orderDetailSel(OrderDetailReq orderDetailReq) {
        OrderDetailRes detailRes = new OrderDetailRes();

        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(Long.parseLong(orderDetailReq.getOrderId()));
        if (borrowOrder == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        String userUid = borrowOrder.getUserUid();
        UserInfo userInfo = userInfoDao.selInfoByUid(userUid);
        if (userInfo == null) {
            throw new BorrowException(ExceptionCode.PARAM_ERROR);
        }

        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        detailRes.setUserInfo(userInfoVo);
        List<BoOrderAudit> auditList = boOrderAuditDao.selByOrderId(borrowOrder.getOrderId());
        List<String> auditkeys = new ArrayList<>();
        auditList.stream().forEach(boOrderAudit -> {
            auditkeys.add(boOrderAudit.getAuditKey());
        });
        detailRes.setAuditkeys(auditkeys);

        List<BoOrderItem> boOrderItems = boOrderItemDao.selByorderId(Long.parseLong(orderDetailReq.getOrderId()));
        if (borrowOrder.getBuesType().equals(BussTypeEnum.CARD.getCode())) {
            UserCar userCar = userCarDao.selByPlateNO(userUid, borrowOrder.getPlateNumber());
            UserCarItemVo userCarItemVo = new UserCarItemVo();
            BeanUtils.copyProperties(userCar,userCarItemVo);
            boOrderItems.stream().forEach(boOrderItem -> {
                if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_IDARD.getItemKey())) {
                    userCarItemVo.setAuthIdcardUrl(boOrderItem.getItemValue());
                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_VEHICLE_LICENSE.getItemKey())) {
                    userCarItemVo.setVehicleLicenseUrl(boOrderItem.getItemValue());
                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.POLLING_LICENSE.getItemKey())) {
                    userCarItemVo.setPollingLicenseUrl(boOrderItem.getItemValue());

                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.CAR_MILEAGE.getItemKey())) {
                    userCarItemVo.setCarSkinUrl(boOrderItem.getItemValue());

                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.INSURANCE_POLICY.getItemKey())) {
                    userCarItemVo.setInsurancePolicyUrl(boOrderItem.getItemValue());

                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.LETTER_COMMITMENT.getItemKey())) {
                    userCarItemVo.setLetterCommitmentUrl(boOrderItem.getItemValue());
                }else if (boOrderItem.getItemKey().equals(BoOrderCarItem.AUTH_OTHER.getItemKey())) {
                    userCarItemVo.setAuthOtherUrl(boOrderItem.getItemValue());
                }
            });
            detailRes.setUserCarItemVo(userCarItemVo);

        }else if (borrowOrder.getBuesType().equals(BussTypeEnum.HOUSE.getCode())) {
            UserHouseInfoVo userHouseInfoVo = new UserHouseInfoVo();
            Map<String, String> itemkeys = new HashMap();
            boOrderItems.stream().forEach(boOrderItem -> {
                itemkeys.put(boOrderItem.getItemKey(), boOrderItem.getItemValue());
            });
            userHouseInfoVo.setHouseName(itemkeys.get(BoOrderHouseItem.HOUSE_NAME.getItemKey()));
            userHouseInfoVo.setHousePart(BoOrderHouseItem.HOUSE_PART.getItemKey());
            userHouseInfoVo.setHouseNum(itemkeys.get(BoOrderHouseItem.HOUSE_NUM.getItemKey()));
            userHouseInfoVo.setHouseArea(itemkeys.get(BoOrderHouseItem.HOUSE_AREA.getItemKey()));
            userHouseInfoVo.setHouseAttr(itemkeys.get(BoOrderHouseItem.HOUSE_ATTR.getItemKey()));
            userHouseInfoVo.setHouseDate(itemkeys.get(BoOrderHouseItem.HOUSE_DATE.getItemKey()));
            userHouseInfoVo.setHouseAddress(itemkeys.get(BoOrderHouseItem.HOUSE_ADDRESS.getItemKey()));
            userHouseInfoVo.setHousePrice(itemkeys.get(BoOrderHouseItem.HOUSE_PRICE.getItemKey()));

            boOrderItems.stream().forEach(boOrderItem -> {
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_IDCARD_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHouseidcardPicUrl(boOrderItem.getItemValue());
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHousePicUrl(boOrderItem.getItemValue());

                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_AUTHORITY_CARD_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHouseAuthorityCardPicUrl(boOrderItem.getItemValue());
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_GUARANTEE_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHouseGuaranteePicUrl(boOrderItem.getItemValue());
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_LETTER_COMMITMENT_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHouseLetterCommitmentPicUrl(boOrderItem.getItemValue());
                }
                if (boOrderItem.getItemKey().equals(BoOrderHouseItem.HOUSE_AUTH_OTHER_PIC_URL.getItemKey())) {
                    userHouseInfoVo.setHouseAuthOtherPicurl(boOrderItem.getItemValue());
                }
            });
            detailRes.setUserHouseInfo(userHouseInfoVo);
        }
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(), detailRes);
    }


    private void orderCostPlan(BoCarRepayPlanRes repayPlanRes, BorrowOrder borrowOrder) {
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

    private List<BorrowRepayment> orderRepaymentPlan(BoCarRepayPlanRes repayPlanRes, BorrowOrder borrowOrder) {
        List<BorrowRepayment> result = new ArrayList<>();
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

    private UserInfo convertUserInfoVo(UserInfoVo userInfoVo) {
        UserInfo userNew = new UserInfo();
        userNew.setUuid(UUIDProvider.uuid());
        userNew.setUserName(userInfoVo.getUserName());
        userNew.setIdcard(userInfoVo.getIdcard());
        userNew.setMobile(userInfoVo.getMobile());
        userNew.setCreditStatus(0);
        userNew.setWorkNature(userInfoVo.getWorkNature());
        userNew.setUserEarns(userInfoVo.getUserEarns());
        userNew.setSex(userInfoVo.getSex());

        userNew.setMarriageStatus(userInfoVo.getMarriage());
        userNew.setChildrenDesc(userInfoVo.getChildren());
        userNew.setLiabilitiesDesc(userInfoVo.getUserDebts());
        userNew.setGuaranteeDesc(userInfoVo.getUserAssure());
        return userNew;
    }

    private UserCar convertUserCarVo(UserCarVo userCarVo, String userUid) {
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

    private List<BoOrderAudit> convertOrderAudit(OrderAuditVo orderAuditVo, long orderId) {
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
