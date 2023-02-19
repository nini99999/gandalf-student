package com.poshist.sys.vo;

import com.poshist.sys.entity.Dictionary;

public class DictionaryVO {
    private Long id;
    private String name;
    private String memo;
    private String type;
    public DictionaryVO(){};
    public DictionaryVO(Dictionary dictionaryInfo){
        this.id=dictionaryInfo.getId();
        this.name=dictionaryInfo.getName();
        this.memo=dictionaryInfo.getMemo();
        this.type=dictionaryInfo.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
