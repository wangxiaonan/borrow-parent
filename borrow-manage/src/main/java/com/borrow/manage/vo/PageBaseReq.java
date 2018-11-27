package com.borrow.manage.vo;

import com.alibaba.fastjson.JSON;

/**
 * Created by wxn on 2018/9/15
 */
public class PageBaseReq extends BaseReq {
    private int pageNo = 1;
    private int pageSize = 10;

    public PageBaseReq() {
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
