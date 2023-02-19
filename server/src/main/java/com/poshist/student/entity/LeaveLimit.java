package com.poshist.student.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "t_st_leave_limit")
@EntityListeners(AuditingEntityListener.class)
public class LeaveLimit extends AbstractEntity {
    private Integer limitValue;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;

    public Integer getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Integer limitValue) {
        this.limitValue = limitValue;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
