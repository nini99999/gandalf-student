package com.poshist.student.vo;

import com.poshist.student.entity.Leave;

public class LeaveVO {
    private Long estimateStartTime;
     private Long estimateEndTime;
    private Long startDate;
    private Long endDate;
    private Long startTime;
    private Long endTime;
    private Integer status;
    private String statusName;
    private Long studentId;
    private String studentName;
    private String department;
    private Long applicantId;
    private String address;
    private String reason;
    private Long id;
    private Long departmentId;
    private Long creatUserId;
    private String creatUserName;
    private Long queryStartTime;
    private Long queryEndTime;
    public LeaveVO() {
    }

    public LeaveVO(Leave leave) {
        setEstimateStartTime(leave.getEstimateStartTime().getTime());
        setEstimateEndTime(leave.getEstimateEndTime().getTime());
        if(null!=leave.getStartDate()){
            setStartDate(leave.getStartDate().getTime());
        }
        if(null!=leave.getEndDate()){
            setEndDate(leave.getEndDate().getTime());
        }
        setStatus(leave.getStatus());
        setStudentId(leave.getStudent().getId());
        setStudentName(leave.getStudent().getName());
        setDepartment(leave.getStudent().getDepartment().getName());
        setApplicantId(leave.getApplicant().getId());
        setAddress(leave.getApplicant().getAddress());
        setReason(leave.getApplicant().getReason());
        setDepartmentId(leave.getStudent().getDepartment().getId());
        setId(leave.getId());
        setCreatUserId(leave.getApplicant().getCreateUser().getId());
        setCreatUserName(leave.getApplicant().getCreateUser().getRealName());
    }

    public Long getQueryStartTime() {
        return queryStartTime;
    }

    public Long getStartTime() {
        return startDate;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endDate;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setQueryStartTime(Long queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public Long getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(Long queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public String getCreatUserName() {
        return creatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        this.creatUserName = creatUserName;
    }

    public Long getCreatUserId() {
        return creatUserId;
    }

    public void setCreatUserId(Long creatUserId) {
        this.creatUserId = creatUserId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        switch (status){
            case 0:
                return "未外出";
            case 1:
                return "已外出";
            case 2:
                return "已返回";
            case 3:
                return "超时未归";
            case 4:
                return "超时返回";
            case 5:
                return "非假外出";
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
