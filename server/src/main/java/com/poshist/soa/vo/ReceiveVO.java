package com.poshist.soa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poshist.soa.entity.Receive;

import java.util.Date;

public class ReceiveVO {
    private Long id;
    private String ip;
    private Date receiveTime;
    private String data;
    private String clientCode;
    private String msg;
    private String msgCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;
    private String version;
    private Long clientId;
    public ReceiveVO(){}
    public ReceiveVO(Receive receive){
        setIp(receive.getIp());
        setReceiveTime(receive.getReceiveTime());
        setData(receive.getData());
        setClientCode(getClientCode());
        setMsg(receive.getMsg());
        setMsgCode(receive.getMsgCode());
        setVersion(receive.getVersion());
        setId(receive.getId());
        setClientId(receive.getClientId());
    }
    public Receive toDTO(Receive receive){
        receive.setIp(getIp());
        receive.setId(getId());
        receive.setReceiveTime(getReceiveTime());
        receive.setData(getData());
        receive.setClientCode(getClientCode());
        receive.setMsg(getMsg());
        receive.setMsgCode(getMsgCode());
        receive.setVersion(getVersion());
        receive.setClientId(getClientId());
        return receive;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
