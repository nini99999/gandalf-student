package com.poshist.car.vo;

import com.poshist.car.entity.CarControl;
import com.poshist.sys.entity.User;

import java.util.Date;

public class CarControlVO {
    private Long id;
    private Double mileage;
    private Long startTime;
    private Long endTime;
    private String routeStart;
    private String routeMiddle;
    private String routeEnd;
    private String address;
    private String memo;
    private Long estimateStartTime;
    private Long estimateEndTime;
    private Integer status;
    private Long carApplicantId;
    private Long driverId;
    private String driverName;
    private String driverMobile;
    private Long carId;
    private String carNO;
    private Long applicantUserId;
    private String applicantUserName;
    private String applicantUserMobile;
    private String applicantUserDepartment;
    private Long useTypeId;
    private String useTypeName;
    private String backTypeName;
    private Integer alertTime;
    private Long estimateStartMaxTime;
    private Long estimateStartMinTime;
    private Long estimateEndMaxTime;
    private Long estimateEndMinTime;
    private Long startMaxTime;
    private Long startMinTime;
    private Long endMaxTime;
    private Long endMinTime;
    private String controlCode;
    private Long controlUserId;
    private String controlUserName;
    private User controlUser;
    private String useName;
    private String leadMobile;
    private String leadPosition;
    private String leadName;
    private Long departmentId;
    private String departmentName;
    public CarControlVO() {
    }

    public CarControlVO(CarControl carControl) {
        setMileage(carControl.getMileage());
        if (null != carControl.getStartTime()) {
            setStartTime(carControl.getStartTime().getTime());
        }
        if (null != carControl.getEndTime()) {
            setEndTime(carControl.getEndTime().getTime());
        }
        setRouteStart(carControl.getRouteStart());
        setRouteMiddle(carControl.getRouteMiddle());
        setRouteEnd(carControl.getRouteEnd());
        setAddress(carControl.getAddress());
        setMemo(carControl.getMemo());
        setEstimateStartTime(carControl.getEstimateStartTime().getTime());
        setEstimateEndTime(carControl.getEstimateEndTime().getTime());
        setStatus(carControl.getStatus());
        if (null != carControl.getCarApplicant()) {
            setCarApplicantId(carControl.getCarApplicant().getId());
        }
        setDriverName(carControl.getDriverName());
        setCarId(carControl.getCar().getId());
        if(null==carControl.getCarNo()) {
            setCarNO(carControl.getCar().getCarNO());
        }else{
            setCarNO(carControl.getCarNo());
        }
        setApplicantUserId(carControl.getApplicantUser().getId());
        setApplicantUserName(carControl.getApplicantUser().getRealName());
        setApplicantUserMobile(carControl.getApplicantUser().getMobile());
        setApplicantUserDepartment(carControl.getApplicantUser().getDepartment().getName());
        setId(carControl.getId());
        setUseTypeId(carControl.getUseType().getId());
        setUseTypeName(carControl.getUseType().getName());
        setAlertTime(carControl.getAlertTime());
        setControlCode(carControl.getControlCode());
        setControlUserName(carControl.getControlUser().getRealName());
        setControlUserId(carControl.getControlUser().getId());
        setLeadMobile(carControl.getLeadMobile());
        setLeadName(carControl.getLeadName());
        setLeadPosition(carControl.getLeadPosition());
        if(null!=carControl.getDepartment()) {
            setDepartmentId(carControl.getDepartment().getId());
            setDepartmentName(carControl.getDepartment().getName());
        }
        setUseName(carControl.getUseName());
    }

    public CarControl toDTO(CarControl carControl) {
        if (carControl.getId() == null) {
            carControl.setRouteStart(getRouteStart());
            carControl.setRouteMiddle(getRouteMiddle());
            carControl.setRouteEnd(getRouteEnd());
            carControl.setAddress(getAddress());
            carControl.setMemo(getMemo());
            carControl.setDriverName(getDriverName());
            carControl.setLeadPosition(getLeadPosition());
            carControl.setLeadName(getLeadName());
            carControl.setLeadMobile(getLeadMobile());
            if(null!=getEstimateEndTime()) {
                carControl.setEstimateEndTime(new Date(getEstimateEndTime()));
                carControl.setEstimateStartTime(new Date(getEstimateStartTime()));
            }
        }
        carControl.setMileage(getMileage());
        if(null!=getStartTime()) {
            carControl.setStartTime(new Date(getStartTime()));
        }
        if(null!=getEndTime()) {
            carControl.setEndTime(new Date(getEndTime()));
        }
        carControl.setAlertTime(getAlertTime());
        carControl.setUseName(getUseName());
        return carControl;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLeadMobile() {
        return leadMobile;
    }

    public void setLeadMobile(String leadMobile) {
        this.leadMobile = leadMobile;
    }

    public String getLeadPosition() {
        return leadPosition;
    }

    public void setLeadPosition(String leadPosition) {
        this.leadPosition = leadPosition;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public Long getControlUserId() {
        return controlUserId;
    }

    public void setControlUserId(Long controlUserId) {
        this.controlUserId = controlUserId;
    }

    public String getControlUserName() {
        return controlUserName;
    }

    public void setControlUserName(String controlUserName) {
        this.controlUserName = controlUserName;
    }

    public User getControlUser() {
        return controlUser;
    }

    public void setControlUser(User controlUser) {
        this.controlUser = controlUser;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public Long getStartMaxTime() {
        return startMaxTime;
    }

    public void setStartMaxTime(Long startMaxTime) {
        this.startMaxTime = startMaxTime;
    }

    public Long getStartMinTime() {
        return startMinTime;
    }

    public void setStartMinTime(Long startMinTime) {
        this.startMinTime = startMinTime;
    }

    public Long getEndMaxTime() {
        return endMaxTime;
    }

    public void setEndMaxTime(Long endMaxTime) {
        this.endMaxTime = endMaxTime;
    }

    public Long getEndMinTime() {
        return endMinTime;
    }

    public void setEndMinTime(Long endMinTime) {
        this.endMinTime = endMinTime;
    }

    public Long getEstimateStartMaxTime() {
        return estimateStartMaxTime;
    }

    public void setEstimateStartMaxTime(Long estimateStartMaxTime) {
        this.estimateStartMaxTime = estimateStartMaxTime;
    }

    public Long getEstimateStartMinTime() {
        return estimateStartMinTime;
    }

    public void setEstimateStartMinTime(Long estimateStartMinTime) {
        this.estimateStartMinTime = estimateStartMinTime;
    }

    public Long getEstimateEndMaxTime() {
        return estimateEndMaxTime;
    }

    public void setEstimateEndMaxTime(Long estimateEndMaxTime) {
        this.estimateEndMaxTime = estimateEndMaxTime;
    }

    public Long getEstimateEndMinTime() {
        return estimateEndMinTime;
    }

    public void setEstimateEndMinTime(Long estimateEndMinTime) {
        this.estimateEndMinTime = estimateEndMinTime;
    }

    public Long getUseTypeId() {
        return useTypeId;
    }

    public void setUseTypeId(Long useTypeId) {
        this.useTypeId = useTypeId;
    }

    public String getBackTypeName() {
        Date now = new Date();
        if (now.before(new Date(estimateStartTime))) {
            return "未出车";
        } else {
            if (null == endTime) {
                return "未返回";
            } else {
                if (endTime <= estimateEndTime) {
                    return "正常返回";
                } else {
                    return "超时返回";
                }
            }
        }
    }

    public Integer getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Integer alertTime) {
        this.alertTime = alertTime;
    }

    public void setBackTypeName(String backTypeName) {
        this.backTypeName = backTypeName;
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

    public String getApplicantUserDepartment() {
        return applicantUserDepartment;
    }

    public void setApplicantUserDepartment(String applicantUserDepartment) {
        this.applicantUserDepartment = applicantUserDepartment;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getEstimateStartTime() {
        return estimateStartTime;
    }

    public void setEstimateStartTime(Long estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
    }

    public Long getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(Long estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCarApplicantId() {
        return carApplicantId;
    }

    public void setCarApplicantId(Long carApplicantId) {
        this.carApplicantId = carApplicantId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
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

    public String getApplicantUserMobile() {
        return applicantUserMobile;
    }

    public void setApplicantUserMobile(String applicantUserMobile) {
        this.applicantUserMobile = applicantUserMobile;
    }
}
