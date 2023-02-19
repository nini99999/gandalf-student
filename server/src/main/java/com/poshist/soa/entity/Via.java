package com.poshist.soa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poshist.common.entity.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_soa_via_info")
@EntityListeners(AuditingEntityListener.class)
public class Via extends AbstractEntity {
    private String cardCode;
    private Integer cardType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date viaTime;
    private Integer viaType;
    private String gateId;
    private String gateInfo;
    private Integer viaResult;
    private Integer status;
    @OneToOne
    @JoinColumn(name = "receive_id",referencedColumnName = "id")
    private Receive receive;


    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Date getViaTime() {
        return viaTime;
    }

    public void setViaTime(Date viaTime) {
        this.viaTime = viaTime;
    }

    public Integer getViaType() {
        return viaType;
    }

    public void setViaType(Integer viaType) {
        this.viaType = viaType;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getGateInfo() {
        return gateInfo;
    }

    public void setGateInfo(String gateInfo) {
        this.gateInfo = gateInfo;
    }

    public Integer getViaResult() {
        return viaResult;
    }

    public void setViaResult(Integer viaResult) {
        this.viaResult = viaResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Receive getReceive() {
        return receive;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }
}
