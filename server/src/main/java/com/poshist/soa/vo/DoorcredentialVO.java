package com.poshist.soa.vo;

import com.poshist.common.utils.CommonUtils;
import com.poshist.student.entity.Leave;

import java.util.List;

public class DoorcredentialVO {
    private String personId;
    private String credentialType;
    private String credentialNo;
    private String faceNo;
    private String status;
    private String startTime;
    private String endTime;
    private static List<EquipmentVO> equipmentIdList;
    private String startTime1;
    private String endTime1;
    public DoorcredentialVO(){}
    public DoorcredentialVO(Leave leave){
        if(null==leave.getStudent().getThirdId()){
            setPersonId("");
        }else {
            setPersonId(leave.getStudent().getThirdId());
        }
        setCredentialType("1");
        setCredentialNo(leave.getStudent().getCardCode());
        setFaceNo(CommonUtils.generateZeroCode(5,leave.getStudent().getId().toString()));
        setStatus("1");
        setStartTime(CommonUtils.dateToSimpleStr(leave.getEstimateStartTime()));
        setEndTime(CommonUtils.dateToSimpleStr(leave.getEstimateEndTime()));
        setStartTime1("00:00");
        setEndTime1("23:59");
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialNo() {
        return credentialNo;
    }

    public void setCredentialNo(String credentialNo) {
        this.credentialNo = credentialNo;
    }

    public String getFaceNo() {
        return faceNo;
    }

    public void setFaceNo(String faceNo) {
        this.faceNo = faceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<EquipmentVO> getEquipmentIdList() {
        return equipmentIdList;
    }

    public void setEquipmentIdList(List<EquipmentVO> equipmentIdList) {
        this.equipmentIdList = equipmentIdList;
    }
}
