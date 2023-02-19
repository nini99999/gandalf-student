package com.poshist.car.vo;

import com.poshist.car.entity.Car;

import java.util.Date;

public class CarVO {
    private Long id;
    private String carNO;
    private Long buyTime;
    private Long insuranceTime;
    private Long maintenanceTime;
    private Long checkTime;
    private Double mileage;
    private Long userId;
    private String userName;
    private Long statusId;
    private String statusName;
    private Long carTypeId;
    private String carTypeName;
    private Long departmentId;
    private String departmentName;

    public CarVO() {
    }

    public CarVO(Car car) {
        setId(car.getId());
        setCarNO(car.getCarNO());
        setBuyTime(car.getBuyTime().getTime());
        setInsuranceTime(car.getInsuranceTime().getTime());
        setMaintenanceTime(car.getMaintenanceTime().getTime());
        setCheckTime(car.getCheckTime().getTime());
        setMileage(car.getMileage());
        setUserId(car.getUser().getId());
        setUserName(car.getUser().getRealName());
        setStatusId(car.getStatus().getId());
        setStatusName(car.getStatus().getName());
        setCarTypeId(car.getCarType().getId());
        setCarTypeName(car.getCarType().getName());
        setDepartmentId(car.getDepartment().getId());
        setDepartmentName(car.getDepartment().getName());
    }

    public Car toDTO(Car car) {
        car.setId(getId());
        car.setCarNO(getCarNO());
        car.setBuyTime(new Date(getBuyTime()));
        car.setInsuranceTime(new Date(getInsuranceTime()));
        car.setMaintenanceTime(new Date(getMaintenanceTime()));
        car.setCheckTime(new Date(getCheckTime()));
        car.setMileage(getMileage());
        return car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Long getInsuranceTime() {
        return insuranceTime;
    }

    public void setInsuranceTime(Long insuranceTime) {
        this.insuranceTime = insuranceTime;
    }

    public Long getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(Long maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    public Long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Long checkTime) {
        this.checkTime = checkTime;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
}
