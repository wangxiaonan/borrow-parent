package com.borrow.manage.dao;

import com.borrow.manage.model.dto.BoProductRate;

import java.util.List;

/**
 * Created by wxn on 2018/9/17
 */
public interface BoProductRateDao {

    List<BoProductRate> selProductRateByPUid(String pUid);
}
