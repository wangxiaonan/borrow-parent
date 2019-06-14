package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BorrowSalesman;

/**
 * Created by wxn on 2018/9/14
 */
public interface BorrowSalesmanDao {

    BorrowSalesman selByMobile(String mobile);

    BorrowSalesman selByUid(String uuid);

    void insertBorrowSalesman(BorrowSalesman borrowSalesman);

    void updateBorrowSalesman(String salesmanUid,BorrowSalesman borrowSalesman);

}


