package com.borrow.manage.vo;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wxn on 2018/9/15
 */
public class PageBaseRes <T> implements Serializable{


    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    public PageBaseRes(List<T> rows) {
        init(rows);
    }

    /**
     * 初始化
     *
     * @param rows
     */
    private void init(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.total = page.getTotal();
            this.rows = page.getResult();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}

