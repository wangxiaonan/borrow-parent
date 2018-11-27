package com.borrow.manage.task;

import com.borrow.manage.dao.BoProductRateDao;
import com.borrow.manage.dao.BorrowOrderDao;
import com.borrow.manage.dao.BorrowRepaymentDao;
import com.borrow.manage.dao.TaskLockDao;
import com.borrow.manage.enums.BoIsStateEnum;
import com.borrow.manage.enums.BoRepayStatusEnum;
import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.enums.RepayStatusEnum;
import com.borrow.manage.model.dto.BoProductRate;
import com.borrow.manage.model.dto.BorrowOrder;
import com.borrow.manage.model.dto.BorrowRepayment;
import com.borrow.manage.model.dto.TaskLock;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import com.borrow.manage.utils.id.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by wxn on 2018/9/26
 */
@Component
@EnableScheduling
public class BorrowStateTask {


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BorrowOrderDao borrowOrderDao;
    @Autowired
    private TaskLockDao taskLockDao;

    @Scheduled(cron = "0 0 2 * * ?")
    public void overdueCal(){

        logger.info("**********开始执行订单状态变更************");
        TaskLock taskLock = new TaskLock();
        try {
            taskLock.setUuid(UUIDProvider.uuid());
            taskLock.setType(2);
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
            List<BorrowOrder> borrowOrders =  borrowOrderDao.selByState(BoIsStateEnum.LOAN_OVER.getCode());
            if (borrowOrders.isEmpty()) {
                logger.info("**********无可更改************");
                return;
            }
            for (BorrowOrder borrowOrder: borrowOrders) {
                BorrowOrder order = new BorrowOrder();
                order.setBoIsState(BoIsStateEnum.PAYING.getCode());
                borrowOrderDao.updateBorrowOrder(borrowOrder.getOrderId(),order);
            }
            TaskLock tasSu = new TaskLock();
            tasSu.setStaus(2);
            taskLockDao.updateTaskLock(taskLock.getUuid(),tasSu);
        }catch (Exception e) {
            TaskLock tasSu = new TaskLock();
            tasSu.setStaus(3);
            taskLockDao.updateTaskLock(taskLock.getUuid(),tasSu);
            logger.error("订单状态变更执行失败:",e);
            return;
        }
        logger.info("**********结束执行订单状态变更************");

    }
}
