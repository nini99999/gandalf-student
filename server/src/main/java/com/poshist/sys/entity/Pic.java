package com.poshist.sys.entity;

import com.poshist.common.entity.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "t_sys_pic")
@EntityListeners(AuditingEntityListener.class)
public class Pic extends AbstractEntity {
    private String type;
    private Long objectId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name=" data", columnDefinition="longblob")
    private byte[] data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
