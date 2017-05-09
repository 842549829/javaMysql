package com.company.classes.model;

public class Paging {

    private int pageIndex = 1;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    private int pageSize = 10;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize(){
        return this.pageSize;
    }

    private int pageCount = 10;

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount(){
        return this.pageCount;
    }

    private boolean rowsCount = true;

    public void setRowsCount(boolean rowsCount){
        this.rowsCount = rowsCount;
    }

    public boolean  getRowsCount(){
        return this.rowsCount;
    }

    public int getStartRows(){
        if (this.pageIndex <= 0){
            return 0;
        }
        return (this.pageIndex-1) * this.pageSize;
    }
}
