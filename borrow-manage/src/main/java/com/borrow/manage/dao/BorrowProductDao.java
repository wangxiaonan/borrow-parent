package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BorrowProduct;

import java.util.List;

/**
 * Created by wxn on 2018/9/14
 */
public interface BorrowProductDao {

    BorrowProduct selByPcode(String pCode);

    List<BorrowProduct> selBorrowList();

}
