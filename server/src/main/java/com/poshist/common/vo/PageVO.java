package com.poshist.common.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageVO {
    private int pageSize;
    private long dataCount;
    private int pageCount;

    private List<Object> data=new ArrayList();
    public PageVO(){};
    public PageVO(Map paraMap){
        if(null!=(String)paraMap.get("pageSize")&&!"null".equals((String)paraMap.get("pageSize"))) {
            setPageSize(Integer.valueOf((String) paraMap.get("pageSize")));
        }else{
            setPageSize(60000);
        }
        if(null!=(String)paraMap.get("pageCount")&&!"null".equals((String)paraMap.get("pageCount"))) {
            setPageCount(Integer.valueOf((String) paraMap.get("pageCount")));
        }else{
            setPageCount(1);
        }
    }

    public void addData(Object o){
        data.add(o);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
