package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.utils.PasswordHelper;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.Utility;
import com.borrow.manage.vo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wxn on 2018/9/15
 */
public class Test {
    public static void main(String[] args) {
//        HashMap map = new HashMap();
//        for (int i = 0;i<8;i++) {
//            System.out.println(UUIDProvider.uuid());
//        }
        System.out.println(PasswordHelper.encryptPassword("123456"));

//        System.out.println(PasswordHelper.encryptPassword("123456"));

//        System.out.println(UUIDProvider.uuid());
//        System.out.println(UUIDProvider.uuid());
//        System.out.println(UUIDProvider.uuid());
//        System.out.println(UUIDProvider.uuid());
//        System.out.println(UUIDProvider.uuid());

//        Lock lock = new ReentrantLock();
//        lock.lock();
//        try {
//            lock.tryLock(1, TimeUnit.DAYS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        BlockingQueue blockingQueue = new ArrayBlockingQueue(50);
//        try {
//            blockingQueue.take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
//        System.out.println(reentrantReadWriteLock.hashCode());
//        CarRepayPlanVo carRepayPlanVo = new CarRepayPlanVo();
//        carRepayPlanVo.setCapitalAmount(BigDecimal.ZERO.toString());
//        carRepayPlanVo.setInterestAmount(BigDecimal.ZERO.toString());
//        carRepayPlanVo.setRepayExpect(String.valueOf(1));
//        carRepayPlanVo.setServiceFee(BigDecimal.ZERO.toString());
//        carRepayPlanVo.setRepayAmount(BigDecimal.ZERO.toString());
//
//        System.out.println(JSON.toJSONString(carRepayPlanVo));
//        long rest=1537259908226L/1000L;
//        System.out.println(rest);
//        OrderRepayListReq repayListReq = new OrderRepayListReq();
//        repayListReq.setUserName("李四");
//        repayListReq.setMobile("15143020432");
//        repayListReq.setBoRepayStatus("1");
//        repayListReq.setPlateNumber("1111");
//        repayListReq.setpCode("0001");
//        repayListReq.setBrTimeStart("2018-09-01");
//        repayListReq.setBrTimeEnd("2018-09-02");
//
//        System.out.println(JSON.toJSONString(repayListReq));
//        OrderPayListReq orderListReq = new OrderPayListReq();
//        orderListReq.setUserName("李四");
//        orderListReq.setMobile("15143020432");
//        orderListReq.setBoPayState("2");
//        orderListReq.setPlateNumber("1111");
//        orderListReq.setpCode("0001");
//
//        orderListReq.setPageNo(1);
//        orderListReq.setPageSize(10);
//        System.out.println(JSON.toJSONString(orderListReq));


//        OrderListReq orderListReq = new OrderListReq();
//        orderListReq.setUserName("李四");
//        orderListReq.setMobile("15143020432");
//        orderListReq.setBoIsState("1");
//        orderListReq.setPlateNumber("1111");
//        orderListReq.setpCode("0001");
//
//        orderListReq.setPageNo(1);
//        orderListReq.setPageSize(10);
//        System.out.println(JSON.toJSONString(orderListReq));



//        ---------------------
//        OrderCreateReq orderCreateReq = new OrderCreateReq();
//
//        UserInfoVo userInfo = new UserInfoVo();
//        userInfo.setIdcard("220724199007085354");
//        userInfo.setMobile("15143020432");
//        userInfo.setUserName("李四");
//        userInfo.setCreditDec("征信良好");
//        userInfo.setIndustry("技术");
//        userInfo.setUserEarns("900000");
//        userInfo.setWorkNature("程序员");
//        orderCreateReq.setUserInfo(userInfo);
//
//        UserCarVo userCar = new UserCarVo();
////        userCar.setCarName("宝马车");
//        userCar.setPlateNumber("123456");
//        orderCreateReq.setUserCar(userCar);
//
//        BorrowSalesmanVo borrowSalesman = new BorrowSalesmanVo();
//        borrowSalesman.setSalesMobile("15143020432");
//        borrowSalesman.setSalesName("王五");
//        orderCreateReq.setBorrowSalesman(borrowSalesman);
//
//        BorrowOrderVo borrowOrder = new BorrowOrderVo();
//        borrowOrder.setProductCode("0001");
//        borrowOrder.setBoPrice(BigDecimal.valueOf(10000));
//        borrowOrder.setBoPaySource("工资");
//        orderCreateReq.setBorrowOrder(borrowOrder);
//
//        OrderAuditVo orderAudit = new OrderAuditVo();
//        List auditkeys = new ArrayList();
//        auditkeys.add("AUTH_IDCARD");
//        auditkeys.add("AUTH_CARPHOTO");
//        auditkeys.add("VEHICLE_LICENSE");
//        auditkeys.add("APPLY_TABLE");
//        auditkeys.add("MORTGAGE");
//        orderAudit.setAuditkeys(auditkeys);
//        orderCreateReq.setOrderAudit(orderAudit);
//
//        System.out.println(JSON.toJSON(orderCreateReq));




    }
}
