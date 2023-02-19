package com.poshist.car.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_car_control_info")
@EntityListeners(AuditingEntityListener.class)
public class CarControl extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    private Car car;
//    @OneToOne
//    @JoinColumn(name = "driver_id",referencedColumnName = "id")
//    private User driver;
    @OneToOne
    @JoinColumn(name = "applicant_id",referencedColumnName = "id")
    private CarApplicant carApplicant;
    @OneToOne
    @JoinColumn(name = "applicant_user_id",referencedColumnName = "id")
    private User applicantUser;
    @OneToOne
    @JoinColumn(name = "control_user_id",referencedColumnName = "id")
    private User controlUser;
    @OneToOne
    @JoinColumn(name = "use_type_id",referencedColumnName = "id")
    private Dictionary useType;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;
    private Double  mileage;
    private Date startTime;
    private Date endTime;
    private String routeStart;
    private String routeMiddle;
    private String routeEnd;
    private String address;
    private String memo;
    private Date estimateStartTime;
    private Date estimateEndTime;
    private Integer status;
    private String driverName;
    private Integer alertTime;
    private String thirdID;
    private String controlCode;
    //申请人
    private String useName;
    private String leadMobile;
    private String leadPosition;
    private String leadName;
    private String carNo;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public User getControlUser() {
        return controlUser;
    }

    public void setControlUser(User controlUser) {
        this.controlUser = controlUser;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getThirdID() {
        return thirdID;
    }

    public void setThirdID(String thirdID) {
        this.thirdID = thirdID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Integer alertTime) {
        this.alertTime = alertTime;
    }

    public Dictionary getUseType() {
        return useType;
    }

    public void setUseType(Dictionary useType) {
        this.useType = useType;
    }

    public User getApplicantUser() {
        return applicantUser;
    }

    public void setApplicantUser(User applicantUser) {
        this.applicantUser = applicantUser;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }



    public CarApplicant getCarApplicant() {
        return carApplicant;
    }

    public void setCarApplicant(CarApplicant carApplicant) {
        this.carApplicant = carApplicant;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Date getEstimateStartTime() {
        return estimateStartTime;
    }

    public void setEstimateStartTime(Date estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
    }

    public Date getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(Date estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
