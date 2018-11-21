package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BorrowSalesman;

/**
 * Created by wxn on 2018/9/14
 */
public interface BorrowSalesmanDao {

    BorrowSalesman selByMobile(String mobile);

    void insertBorrowSalesman(BorrowSalesman borrowSalesman);

}


