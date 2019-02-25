package com.borrow.manage.service.Impl;

import com.borrow.manage.dao.BoProductRateDao;
import com.borrow.manage.dao.BorrowProductDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.model.dto.BoProductRate;
import com.borrow.manage.model.dto.BorrowProduct;
import com.borrow.manage.service.ProductService;
import com.borrow.manage.vo.ProductListRes;
import com.borrow.manage.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wxn on 2018/9/17
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    BorrowProductDao borrowProductDao;
    @Autowired
    BoProductRateDao boProductRateDao;

    @Override
    public ResponseResult productSelList() {

        List<ProductListRes> listRes = new ArrayList<>();
        List<BorrowProduct> borrowProducts = borrowProductDao.selBorrowList();
        borrowProducts.stream().forEach( borrowProduct -> {
            ProductListRes res = new ProductListRes();
            List<BoProductRate> boProductRates = boProductRateDao.selProductRateByPUid(borrowProduct.getUuid());
            Map<String,String> stringMap = new HashMap<>();
            boProductRates.stream().forEach(boProductRate -> {
                stringMap.put(boProductRate.getRateKey(),boProductRate.getRateValue());
            });
            res.setProductCode(borrowProduct.getpCode());
            res.setProductName(borrowProduct.getpName());
            res.setProductExpect(String.valueOf(borrowProduct.getpExpect()));

            res.setEarlyServiceRate(stringMap.get(ProductRateEnum.EARLY_SERVICE_RATE.getRateKey()));
            res.setMonthServiceRate(stringMap.get(ProductRateEnum.MONTH_SERVICE_RATE.getRateKey()));
            res.setMonthAccrualRate(stringMap.get(ProductRateEnum.MONTH_ACCRUAL_RATE.getRateKey()));
            res.setGuaranteeViolateRate(stringMap.get(ProductRateEnum.GUARANTEE_VIOLATE_RATE.getRateKey()));
            res.setServiceViolateRate(stringMap.get(ProductRateEnum.SERVICE_VIOLATE_RATE.getRateKey()));
            res.setEarlyPayRate(stringMap.get(ProductRateEnum.EARLY_PAY_RATE.getRateKey()));
            res.setFineServiceRate(stringMap.get(ProductRateEnum.FINE_SERVICE_RATE.getRateKey()));

            res.setpPayType(String.valueOf(borrowProduct.getpPayType()));
            res.setCreateTime(borrowProduct.getCreateTime());
            listRes.add(res);

        });
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),listRes);
    }
}
