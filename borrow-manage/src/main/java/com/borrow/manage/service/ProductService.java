package com.borrow.manage.service;

import com.borrow.manage.enums.ProductRateEnum;
import com.borrow.manage.vo.ResponseResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wxn on 2018/9/17
 */
public interface ProductService {

    ResponseResult productSelList();

    BigDecimal getRate(ProductRateEnum productRateEnum,String pUid );
}
