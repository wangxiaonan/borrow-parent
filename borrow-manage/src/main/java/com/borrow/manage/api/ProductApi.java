package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.service.ProductService;
import com.borrow.manage.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wxn on 2018/9/17
 */
@RestController
public class ProductApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/borrow/product/sel", method = RequestMethod.POST)
    public ResponseResult selProductList() {
        logger.info("====>selProductList()");
        ResponseResult res = productService.productSelList();
        logger.info("<====selProductList():res={}", JSON.toJSON(res));
        return res;
    }

}
