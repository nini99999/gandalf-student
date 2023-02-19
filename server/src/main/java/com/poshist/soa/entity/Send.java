package com.poshist.soa.entity;

import com.poshist.common.entity.AbstractEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_soa_send_info")
@EntityListeners(AuditingEntityListener.class)
public class Send extends AbstractEntity {
    @Length(max = 4096)
    private String sendData;
    @Length(max = 4096)
    private String backDate;
    private Date sendTime;
    private String thirdCode;
    private Long bussId;

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public Long getBussId() {
        return bussId;
    }

    public void setBussId(Long bussId) {
        this.bussId = bussId;
    }
}
