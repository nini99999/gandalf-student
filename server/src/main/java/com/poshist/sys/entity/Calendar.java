package com.poshist.sys.entity;

import com.poshist.common.entity.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_calendar_info")
@EntityListeners(AuditingEntityListener.class)
public class Calendar extends AbstractEntity {
    private String dateName;
    private Integer status;

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
