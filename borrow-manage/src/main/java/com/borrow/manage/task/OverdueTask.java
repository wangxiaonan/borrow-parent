package com.borrow.manage.task;

import com.borrow.manage.dao.*;
import com.borrow.manage.enums.BoRepayStatusEnum;
import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.enums.RepayStatusEnum;
import com.borrow.manage.model.dto.*;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import com.borrow.manage.utils.id.IdProvider;
import com.borrow.manage.utils.id.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by wxn on 2018/9/26
 */
@Component
@EnableScheduling
public class OverdueTask{


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BorrowRepaymentDao borrowRepaymentDao;
    @Autowired
    private BorrowOrderDao borrowOrderDao;
    @Autowired
    private BoProductRateDao boProductRateDao;
    @Autowired
    private TaskLockDao taskLockDao;

    @Scheduled(cron = "0 0 1 * * ?")
    public void overdueCal(){

        logger.info("**********开始执行逾期计算************");
        TaskLock taskLock = new TaskLock();
        try {
            taskLock.setUuid(UUIDProvider.uuid());
            taskLock.setType(1);
            taskLock.setExcTime(Utility.getUnixTime());
            taskLock.setStaus(1);
            taskLock.setIp(IpUtils.getHostIp());
            taskLockDao.insertTaskLock(taskLock);
        }catch (Exception e) {
            logger.info("**********其它服务已执行************");
            return;
        }
        try {
            Set<Long> orderIds = new HashSet<>();
            List<BorrowRepayment> repaymentList =  borrowRepaymentDao.selOverdueListCal();
            if (repaymentList.isEmpty()) {
                logger.info("**********无可计算期数************");
                return;
            }
            for (BorrowRepayment repayment: repaymentList) {
                    if(repayment.getCapitalAmount().compareTo(BigDecimal.ZERO)  ==0) {
                        BorrowRepayment rep = new BorrowRepayment();
                        rep.setRepayStatus(RepayStatusEnum.PAY_YES.getCode());
                        rep.setRepayFinishAmount(repayment.getRepayAmount());
                        borrowRepaymentDao.updateBoRepayment(repayment.getRepayId(),rep);
                        borrowOrderDao.upBoPayExpectCount(repayment.getOrderId());
                    }else {
                        orderIds.add(repayment.getOrderId());
                        BorrowOrder borrowOrder = borrowOrderDao.selByOrderId(repayment.getOrderId());
                        List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowOrder.getProductUid());

                        Map<String,String> productRateMap = new HashMap<>();
                        boProductRates.stream().forEach(boProductRate -> {
                            productRateMap.put(boProductRate.getRateKey(),boProductRate.getRateValue());
                        });

                        String rateValuestr = productRateMap.get(ProductRateEnum.SERVICE_VIOLATE_RATE.getRateKey());
                        rateValuestr = rateValuestr == null ? "0":rateValuestr;
                        BigDecimal rateValue = BigDecimal.valueOf(Double.valueOf(rateValuestr));

                        String fineRateStr = productRateMap.get(ProductRateEnum.FINE_SERVICE_RATE.getRateKey());
                        fineRateStr = fineRateStr == null ? "0":fineRateStr;
                        BigDecimal fineRate = BigDecimal.valueOf(Double.valueOf(fineRateStr));


                        BigDecimal rateDay = BigDecimal.valueOf(Utility.getOverdueDay(repayment.getBrTime()));
                        BorrowRepayment rep = new BorrowRepayment();
                        rep.setBoRepayStatus(BoRepayStatusEnum.OVERDUE.getCode());
                        BigDecimal punishAmount = repayment.getCapitalAmount().multiply(rateValue).multiply(rateDay);
                        rep.setPunishAmount(punishAmount);

                        borrowRepaymentDao.updateBoRepayment(repayment.getRepayId(),rep);
                    }
            }
            for (Long orderId : orderIds) {
                BorrowOrder borrowOrder = new BorrowOrder();
                borrowOrder.setBoPayState(BoRepayStatusEnum.OVERDUE.getCode());
                borrowOrderDao.updateBorrowOrder(orderId,borrowOrder);
            }
            TaskLock tasSu = new TaskLock();
            tasSu.setStaus(2);
            taskLockDao.updateTaskLock(taskLock.getUuid(),tasSu);
        }catch (Exception e) {
            TaskLock tasSu = new TaskLock();
            tasSu.setStaus(3);
            taskLockDao.updateTaskLock(taskLock.getUuid(),tasSu);
            logger.error("逾期计算 执行失败:",e);
            return;
        }
        logger.info("**********结束执行逾期计算************");

    }
}
