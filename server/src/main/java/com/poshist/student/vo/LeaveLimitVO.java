package com.poshist.student.vo;

import com.poshist.student.entity.LeaveLimit;

public class LeaveLimitVO {
    private Long departmentId;
    private String departmentName;
    private Integer limitValue;
    private Long id;
    public LeaveLimitVO(){}
    public LeaveLimitVO(LeaveLimit leaveLimit){
        setDepartmentId(leaveLimit.getDepartment().getId());
        setDepartmentName(leaveLimit.getDepartment().getName());
        setLimitValue(leaveLimit.getLimitValue());
        setId(leaveLimit.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Integer limitValue) {
        this.limitValue = limitValue;
    }

}
