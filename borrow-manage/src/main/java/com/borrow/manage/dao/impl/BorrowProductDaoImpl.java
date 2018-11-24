package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BorrowProductDao;
import com.borrow.manage.dao.mapper.BorrowProductMapper;
import com.borrow.manage.model.dto.BorrowOrderExample;
import com.borrow.manage.model.dto.BorrowProduct;
import com.borrow.manage.model.dto.BorrowProductExample;
import com.borrow.manage.vo.PageBaseRes;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
@Repository
public class BorrowProductDaoImpl implements BorrowProductDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BorrowProductMapper borrowProductMapper;


    @Override
    public BorrowProduct selByPcode(String pCode) {
        logger.debug("selByPcode:pCode={}",pCode);
        BorrowProductExample example = new BorrowProductExample();
        example.createCriteria().andPCodeEqualTo(pCode);
        List<BorrowProduct> productList =  borrowProductMapper.selectByExample(example);
        return productList.isEmpty()?null:productList.get(0);
    }

    @Override
    public BorrowProduct selByPUid(String pUid) {
        logger.debug("selByPUid:pUid={}",pUid);
        BorrowProductExample example = new BorrowProductExample();
        example.createCriteria().andUuidEqualTo(pUid);
        List<BorrowProduct> productList =  borrowProductMapper.selectByExample(example);
        return productList.isEmpty()?null:productList.get(0);
    }

    @Override
    public List<BorrowProduct> selBorrowList() {
        BorrowProductExample borrowProductExample = new BorrowProductExample();
        return  borrowProductMapper.selectByExample(borrowProductExample);
    }

}
