package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BorrowSalesmanDao;
import com.borrow.manage.dao.mapper.BorrowSalesmanMapper;
import com.borrow.manage.model.dto.BorrowSalesman;
import com.borrow.manage.model.dto.BorrowSalesmanExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
@Repository
public class BorrowSalesmanDaoImpl implements BorrowSalesmanDao {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    BorrowSalesmanMapper borrowSalesmanMapper;
    @Override
    public BorrowSalesman selByMobile(String mobile) {
        logger.debug("selByMobile:mobile={}",mobile);

        BorrowSalesmanExample example = new BorrowSalesmanExample();
        example.createCriteria().andSalesMobileEqualTo(mobile);

        List<BorrowSalesman> salesmen =  borrowSalesmanMapper.selectByExample(example);
        return salesmen.isEmpty()? null:salesmen.get(0);
    }

    @Override
    public BorrowSalesman selByUid(String uuid) {
        BorrowSalesmanExample example = new BorrowSalesmanExample();
        example.createCriteria().andUuidEqualTo(uuid);
        List<BorrowSalesman> salesmen =  borrowSalesmanMapper.selectByExample(example);
        return salesmen.isEmpty()? null:salesmen.get(0);
    }

    @Override
    public void insertBorrowSalesman(BorrowSalesman borrowSalesman) {

        borrowSalesmanMapper.insertSelective(borrowSalesman);
    }

    @Override
    public void updateBorrowSalesman(String salesmanUid, BorrowSalesman borrowSalesman) {
        BorrowSalesmanExample example = new BorrowSalesmanExample();
        example.createCriteria().andUuidEqualTo(salesmanUid);
        borrowSalesmanMapper.updateByExampleSelective(borrowSalesman,example);
    }


}
