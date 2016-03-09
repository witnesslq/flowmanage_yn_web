package com.ultrapower.flowmanage.common.vo;

import java.util.List;

/**
* 分页实体类
* @author zhengWei
*
*/
public class Page {
    //默认的每页记录数
    public static final int DEFAULT_PAGE_SIZE = 10;
    // 每页的记录数
    private int pageSize = DEFAULT_PAGE_SIZE;
    // 当前页
    private int pageNo = 1;
    // 总行数
    private int rowCount;
    // 总页数
    private int pageCount;
    // 每页的记录
    private List resultList;

    public Page() {}

    public Page(int pageNo) {
       this.pageNo = pageNo;
    }

    public Page(int pageSize, int pageNo) {
       this.pageSize = pageSize;
       this.pageNo = pageNo;
    }

    public int getPageSize() {
       return pageSize;
    }

    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }

    public int getPageNo() {
       return pageNo;
    }

    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }

    public int getRowCount() {
       return rowCount;
    }

    public void setRowCount(int rowCount) {
       this.rowCount = rowCount;
       if(rowCount % pageSize == 0) {
           this.pageCount = rowCount / pageSize;
       } else {
           this.pageCount = rowCount / pageSize + 1;
       }
    }

    public int getPageCount() {
       return pageCount;
    }

    public void setPageCount(int pageCount) {
       this.pageCount = pageCount;
    }

    public List getResultList() {
       return resultList;
    }

    public void setResultList(List resultList) {
       this.resultList = resultList;
    }
}


