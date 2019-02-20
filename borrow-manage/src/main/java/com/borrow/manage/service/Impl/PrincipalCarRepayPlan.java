package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.BoProductRateDao;
import com.borrow.manage.dao.BorrowProductDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.model.dto.BoProductRate;
import com.borrow.manage.model.dto.BorrowProduct;
import com.borrow.manage.provider.AbstractCarRepayPlan;
import com.borrow.manage.provider.RepayPlan;
import com.borrow.manage.vo.BoCarRepayPlanRes;
import com.borrow.manage.vo.CarRepayPlanVo;
import com.borrow.manage.vo.RepayPlanCalReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wxn on 2018/9/19
 */
@Component("principalCarRepayPlan")
public class PrincipalCarRepayPlan  extends AbstractCarRepayPlan {

    @Override
    public BoCarRepayPlanRes planCal(RepayPlanCalReq repayPlanCalReq) {
        BorrowProduct borrowProduct = borrowProductDao.selByPcode(repayPlanCalReq.getpCode());
        List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowProduct.getUuid());
        Map<String,String> mapRate = new HashMap();
        boProductRates.stream().forEach(boProductRate -> {
            mapRate.put(boProductRate.getRateKey(),boProductRate.getRateValue());
        });

        BoCarRepayPlanRes boCarRepayPlanRes = new BoCarRepayPlanRes();
        boCarRepayPlanRes.setBoPrice(repayPlanCalReq.getBoPrice().toString());
        boCarRepayPlanRes.setBoExpect(borrowProduct.getpExpect().toString());
        boCarRepayPlanRes.setGpsCost(mapRate.get(ProductRateEnum.GPS_COST.getRateKey()));
        boCarRepayPlanRes.setEarlyServiceRate(mapRate.get(ProductRateEnum.EARLY_SERVICE_RATE.getRateKey()));
        BigDecimal gpsCost = BigDecimal.valueOf(Double.valueOf(mapRate.get(ProductRateEnum.GPS_COST.getRateKey())));
        BigDecimal earlyServiceRate = BigDecimal.valueOf(Double.valueOf(boCarRepayPlanRes.getEarlyServiceRate()));
        BigDecimal earlyServiceCost = repayPlanCalReq.getBoPrice().multiply(earlyServiceRate);
        boCarRepayPlanRes.setEarlyServiceCost(earlyServiceCost.toString());
        BigDecimal boFinishPrice =repayPlanCalReq.getBoPrice().subtract(gpsCost).subtract(earlyServiceCost);
        boFinishPrice = boFinishPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        boCarRepayPlanRes.setBoFinishPrice(boFinishPrice.toString());

        String monthAccrualRate = mapRate.get(ProductRateEnum.MONTH_ACCRUAL_RATE.getRateKey());
        String monthServiceRate = mapRate.get(ProductRateEnum.MONTH_SERVICE_RATE.getRateKey());


        List<CarRepayPlanVo> repayPlans = new ArrayList<>();
        int boExpect = Integer.valueOf(borrowProduct.getpExpect());
        for (int i = 1; i< boExpect; i++) {
            CarRepayPlanVo carRepayPlanVo = new CarRepayPlanVo();

            carRepayPlanVo.setCapitalAmount(BigDecimal.ZERO.toString());
            BigDecimal interestAmount = repayPlanCalReq.getBoPrice()
                    .multiply(BigDecimal.valueOf(Double.valueOf(monthAccrualRate))).setScale(2, BigDecimal.ROUND_HALF_UP);

            carRepayPlanVo.setInterestAmount(interestAmount.toString());
            carRepayPlanVo.setRepayExpect(String.valueOf(i));
            BigDecimal serviceFee = repayPlanCalReq.getBoPrice()
                    .multiply(BigDecimal.valueOf(Double.valueOf(monthServiceRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
            carRepayPlanVo.setServiceFee(serviceFee.toString());

            carRepayPlanVo.setRepayAmount(interestAmount.add(serviceFee).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            repayPlans.add(carRepayPlanVo);
        }
        CarRepayPlanVo lastCarRepayPlanVo = new CarRepayPlanVo();
        lastCarRepayPlanVo.setCapitalAmount(repayPlanCalReq.getBoPrice().toString());

        BigDecimal interestAmount = repayPlanCalReq.getBoPrice()
                .multiply(BigDecimal.valueOf(Double.valueOf(monthAccrualRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
        lastCarRepayPlanVo.setInterestAmount(interestAmount.toString());
        lastCarRepayPlanVo.setRepayExpect(String.valueOf(boExpect));

        BigDecimal serviceFee = repayPlanCalReq.getBoPrice()
                .multiply(BigDecimal.valueOf(Double.valueOf(monthServiceRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
        lastCarRepayPlanVo.setServiceFee(serviceFee.toString());
        BigDecimal repayAmount = repayPlanCalReq.getBoPrice().add(interestAmount).add(serviceFee);
        if (repayAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw  new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        lastCarRepayPlanVo.setRepayAmount(repayAmount.toString());
        repayPlans.add(lastCarRepayPlanVo);
        boCarRepayPlanRes.setRepayPlans(repayPlans);
        return boCarRepayPlanRes;
    }
}
