package com.poshist.car.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_car_applicant_info")
@EntityListeners(AuditingEntityListener.class)
public class CarApplicant extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "applicant_user_id",referencedColumnName = "id")
    private User applicantUser;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;
    private String applicantCode;
    @OneToOne
    @JoinColumn(name = "car_type_id",referencedColumnName = "id")
    private Dictionary carType;
    @OneToOne
    @JoinColumn(name = "use_type_id",referencedColumnName = "id")
    private Dictionary useType;
    private String leadName;
    private Integer carCount;
    private Integer isArea;
    private Date startTime;
    private Date endTime;
    private String reason;
    private String routeStart;
    private String routeMiddle;
    private String routeEnd;
    private Integer status;
    private String refuseReason;
    private String leadMobile;
    //含义改为上车地点
    private String leadPosition;
    @OneToMany
    @JoinColumn(name = "applicant_id")
    @OrderBy("code")
    private List<CarApproval> carApprovals;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getApplicantCode() {
        return applicantCode;
    }

    public void setApplicantCode(String applicantCode) {
        this.applicantCode = applicantCode;
    }

    public Dictionary getCarType() {
        return carType;
    }

    public void setCarType(Dictionary carType) {
        this.carType = carType;
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

    public List<CarApproval> getCarApprovals() {
        return carApprovals;
    }

    public void setCarApprovals(List<CarApproval> carApprovals) {
        this.carApprovals = carApprovals;
    }
}
