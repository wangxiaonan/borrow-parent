package com.borrow.manage.dao.impl;

import com.borrow.manage.dao.BoProductRateDao;
import com.borrow.manage.dao.mapper.BoProductRateMapper;
import com.borrow.manage.model.dto.BoProductRate;
import com.borrow.manage.model.dto.BoProductRateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wxn on 2018/9/17
 */
@Repository
public class BoProductRateDaoImpl  implements BoProductRateDao{

    @Autowired
    BoProductRateMapper boProductRateMapper;

    @Override
    public List<BoProductRate> selProductRateByPUid(String pUid) {
        BoProductRateExample example = new BoProductRateExample();
        example.createCriteria().andPUidEqualTo(pUid);
        return boProductRateMapper.selectByExample(example);
    }
}
