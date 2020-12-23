package com.xunfang.pojo;

//需要显示第几页，待显示的页面（当前页）
public class Pager {
    private int curPage;
    //每页显示的记录数
    private int perPageRows;
    //总记录数
    private int rowCount;
    //总页数
    private int pageCount;

    //显示当前页的第一个数据的下标（这页第一个数据索引）limit ?,?
    public int getFirstLimitParam(){
        return (this.curPage-1)*this.perPageRows;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPerPageRows() {
        return perPageRows;
    }

    public void setPerPageRows(int perPageRows) {
        this.perPageRows = perPageRows;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    //根据rowCount和 perPageRows 计算总页数
    public int getPageCount() {
        return (rowCount+perPageRows-1)/perPageRows;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}

