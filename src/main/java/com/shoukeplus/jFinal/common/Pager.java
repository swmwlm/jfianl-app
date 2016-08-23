package com.shoukeplus.jFinal.common;


import java.util.List;

public class Pager<T> {

    private Integer pageNo = 1;// 页码，默认是第一页
    private Integer pageSize = 10;// 每页显示的记录数，默认是10
    private Integer totalRecord;// 总记录数
    private Integer totalPage;// 总页数
    private List<T> results;// 对应的当前页记录

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize
                : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
