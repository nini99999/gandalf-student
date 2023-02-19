package com.poshist.sys.vo;

import com.poshist.sys.entity.Pic;

import javax.persistence.Transient;


public class PicVO {
    private Long id;
    private Long ObjectId;
    @Transient
    private byte[] data;
    private String type;
    public PicVO(){};
    public PicVO(Pic pic){
        setId(pic.getId());
        setObjectId(pic.getObjectId());
       // setData(pic.getData());
        setType(pic.getType());
    }
    public Pic toDTO(Pic pic){
        pic.setId(getId());
        pic.setData(getData());
        pic.setType(getType());
        pic.setObjectId(getObjectId());
        return pic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return ObjectId;
    }

    public void setObjectId(Long objectId) {
        ObjectId = objectId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
