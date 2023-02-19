package com.poshist.soa.vo;

import com.poshist.car.entity.CarControl;
import com.poshist.common.utils.CommonUtils;

public class SJBlackWhiteListVO {
    private String blackGuid;
    private String plateNumber;
    private Integer blackWhiteType;
    private String startDate;
    private String endDate;
    private String reason;
    private String remark;
    public SJBlackWhiteListVO(CarControl carControl){

        setReason("");
        setRemark("");
        if(null==carControl.getThirdID()){
            setBlackGuid("");
        }else{
            setBlackGuid(carControl.getThirdID());
        }

        setPlateNumber(carControl.getCar().getCarNO());
        setBlackWhiteType(3);
        setStartDate(CommonUtils.dateToStr(carControl.getEstimateStartTime()));
        setEndDate(CommonUtils.dateToStr(carControl.getEstimateEndTime()));
    }
public SJBlackWhiteListVO(){}
    public String getBlackGuid() {
        return blackGuid;
    }

    public void setBlackGuid(String blackGuid) {
        this.blackGuid = blackGuid;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getBlackWhiteType() {
        return blackWhiteType;
    }

    public void setBlackWhiteType(Integer blackWhiteType) {
        this.blackWhiteType = blackWhiteType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
