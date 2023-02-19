package com.poshist.car.vo;

import com.poshist.car.entity.CarApplicant;
import com.poshist.car.entity.CarApproval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarApplicantVO {
    private Long id;
    private Long applicantUserId;
    private String applicantUserName;
    private Long departmentId;
    private String departmentName;
    private String applicantCode;
    private String leadName;
    private Integer carCount;
    private Integer isArea;
    private Long startTime;
    private Long endTime;
    private String reason;
    private String routeStart;
    private String routeMiddle;
    private String routeEnd;
    private Integer status;
    private Long carTypeId;
    private String carTypeName;
    private Long useTypeId;
    private String useTypeName;
    private String leadMobile;
    public List<CarApprovalVO> carApprovalVOS;
    public Long controlId;
    private String refuseReason;
    private String leadPosition;
    public CarApplicantVO() {
    }

    public CarApplicantVO(CarApplicant carApplicant) {
        setLeadMobile(carApplicant.getLeadMobile());
        setId(carApplicant.getId());
        setApplicantUserId(carApplicant.getApplicantUser().getId());
        setApplicantUserName(carApplicant.getApplicantUser().getRealName());
        setDepartmentId(carApplicant.getApplicantUser().getDepartment().getId());
        setDepartmentName(carApplicant.getApplicantUser().getDepartment().getName());
        setApplicantCode(carApplicant.getApplicantCode());
        setLeadName(carApplicant.getLeadName());
        setCarCount(carApplicant.getCarCount());
        setIsArea(carApplicant.getIsArea());
        setStartTime(carApplicant.getStartTime().getTime());
        setEndTime(carApplicant.getEndTime().getTime());
        setReason(carApplicant.getReason());
        setRouteStart(carApplicant.getRouteStart());
        setRouteMiddle(carApplicant.getRouteMiddle());
        setRouteEnd(carApplicant.getRouteEnd());
        setStatus(carApplicant.getStatus());
        setCarTypeId(carApplicant.getCarType().getId());
        setCarTypeName(carApplicant.getCarType().getName());
        setUseTypeId(carApplicant.getUseType().getId());
        setUseTypeName(carApplicant.getUseType().getName());
        setRefuseReason(carApplicant.getRefuseReason());
        setLeadPosition(carApplicant.getLeadPosition());
        carApprovalVOS = new ArrayList<>();
        if(null!=carApplicant.getCarApprovals()) {
            for (CarApproval carApproval : carApplicant.getCarApprovals()) {
                carApprovalVOS.add(new CarApprovalVO(carApproval));
            }
        }
    }

    public CarApplicant toDTO(CarApplicant carApplicant) {
        carApplicant.setLeadName(getLeadName());
        carApplicant.setCarCount(getCarCount());
        carApplicant.setIsArea(getIsArea());
        carApplicant.setStartTime(new Date(getStartTime()));
        carApplicant.setEndTime(new Date(getEndTime()));
        carApplicant.setReason(getReason());
        carApplicant.setRouteStart(getRouteStart());
        carApplicant.setRouteMiddle(getRouteMiddle());
        carApplicant.setRouteEnd(getRouteEnd());
        carApplicant.setRefuseReason(getRefuseReason());
        carApplicant.setLeadMobile(getLeadMobile());
        carApplicant.setLeadPosition(getLeadPosition());
        return carApplicant;
    }

    public String getLeadPosition() {
        return leadPosition;
    }

    public void setLeadPosition(String leadPosition) {
        this.leadPosition = leadPosition;
    }

    public String getLeadMobile() {
        return leadMobile;
    }

    public void setLeadMobile(String leadMobile) {
        this.leadMobile = leadMobile;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public List<CarApprovalVO> getCarApprovalVOS() {
        return carApprovalVOS;
    }

    public void setCarApprovalVOS(List<CarApprovalVO> carApprovalVOS) {
        this.carApprovalVOS = carApprovalVOS;
    }

    public Long getControlId() {
        return controlId;
    }

    public void setControlId(Long controlId) {
        this.controlId = controlId;
    }

    public Long getUseTypeId() {
        return useTypeId;
    }

    public void setUseTypeId(Long useTypeId) {
        this.useTypeId = useTypeId;
    }

    public String getUseTypeName() {
        return useTypeName;
    }

    public void setUseTypeName(String useTypeName) {
        this.useTypeName = useTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicantUserId() {
        return applicantUserId;
    }

    public void setApplicantUserId(Long applicantUserId) {
        this.applicantUserId = applicantUserId;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getApplicantCode() {
        return applicantCode;
    }

    public void setApplicantCode(String applicantCode) {
        this.applicantCode = applicantCode;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getIsArea() {
        return isArea;
    }

    public void setIsArea(Integer isArea) {
        this.isArea = isArea;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteMiddle() {
        return routeMiddle;
    }

    public void setRouteMiddle(String routeMiddle) {
        this.routeMiddle = routeMiddle;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Long carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}
