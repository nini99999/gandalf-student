package com.poshist.soa.vo;

import com.poshist.soa.entity.Via;

public class ViaVO {
    private String cardCode;
    private Integer cardType;
    private Long viaTime;
    private Integer viaType;
    private String gateId;
    private String gateInfo;
    private Integer viaResult;
    private Integer status;
    public ViaVO (){}
    public ViaVO(Via via){
        setCardCode(via.getCardCode());
        setCardType(via.getCardType());
        setViaTime(via.getViaTime().getTime());
        setGateId(via.getGateId());
        setGateInfo(via.getGateInfo());
        setViaResult(via.getViaResult());
        setViaType(via.getViaType());
        setStatus(via.getStatus());
    }
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

    public Long getViaTime() {
        return viaTime;
    }

    public void setViaTime(Long viaTime) {
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
}
