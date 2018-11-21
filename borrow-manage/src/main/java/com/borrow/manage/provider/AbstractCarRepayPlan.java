package com.borrow.manage.provider;

import com.borrow.manage.dao.BoProductRateDao;
import com.borrow.manage.dao.BorrowProductDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.exception.BorrowException;
import com.borrow.manage.model.dto.BoProductRate;
import com.borrow.manage.model.dto.BorrowProduct;
import com.borrow.manage.vo.BoCarRepayPlanRes;
import com.borrow.manage.vo.CarRepayPlanVo;
import com.borrow.manage.vo.RepayPlanCalReq;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wxn on 2018/9/19
 */
public abstract class AbstractCarRepayPlan implements RepayPlan<BoCarRepayPlanRes,RepayPlanCalReq> {

    @Autowired
    private BorrowProductDao borrowProductDao;
    @Autowired
    private BoProductRateDao boProductRateDao;

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

        List<CarRepayPlanVo> repayPlans = new ArrayList<>();
        int boExpect = Integer.valueOf(borrowProduct.getpExpect());

        for (int i = 1; i< boExpect; i++) {
            CarRepayPlanVo carRepayPlanVo = new CarRepayPlanVo();
            carRepayPlanVo.setCapitalAmount(BigDecimal.ZERO.toString());
            carRepayPlanVo.setInterestAmount(BigDecimal.ZERO.toString());
            carRepayPlanVo.setRepayExpect(String.valueOf(i));
            carRepayPlanVo.setServiceFee(BigDecimal.ZERO.toString());
            carRepayPlanVo.setRepayAmount(BigDecimal.ZERO.toString());
            repayPlans.add(carRepayPlanVo);
        }
        BigDecimal monthServiceRate = BigDecimal.valueOf(
                Double.valueOf(mapRate.get(ProductRateEnum.MONTH_SERVICE_RATE.getRateKey())));
        BigDecimal monthAccrualRate = BigDecimal.valueOf(
                Double.valueOf(mapRate.get(ProductRateEnum.MONTH_ACCRUAL_RATE.getRateKey())));

        BigDecimal monthServiceCost = repayPlanCalReq.getBoPrice()
                .multiply(monthServiceRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal monthAccrualCost = repayPlanCalReq.getBoPrice()
                .multiply(monthAccrualRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal capitalAmount = repayPlanCalReq.getBoPrice();
        CarRepayPlanVo lastCarRepayPlanVo = new CarRepayPlanVo();
        lastCarRepayPlanVo.setCapitalAmount(capitalAmount.toString());
        lastCarRepayPlanVo.setInterestAmount(monthAccrualCost.toString());
        lastCarRepayPlanVo.setRepayExpect(String.valueOf(boExpect));
        lastCarRepayPlanVo.setServiceFee(monthServiceCost.toString());
        BigDecimal repayAmount = capitalAmount.add(monthServiceCost).add(monthAccrualCost);
        if (repayAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw  new BorrowException(ExceptionCode.PARAM_ERROR);
        }
        lastCarRepayPlanVo.setRepayAmount(capitalAmount.add(monthServiceCost).add(monthAccrualCost).toString());
        repayPlans.add(lastCarRepayPlanVo);
        boCarRepayPlanRes.setRepayPlans(repayPlans);
        return boCarRepayPlanRes;
    }

}
