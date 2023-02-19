package com.poshist.car.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_car_applicant_approval")
@EntityListeners(AuditingEntityListener.class)
public class CarApproval extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "approval_user_id",referencedColumnName = "id")
    private User approvalUser;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;
    private String code;
    private Integer status;
    private String opinion;
    private Date approvalTime;
    @OneToOne
    @JoinColumn(name = "applicant_id",referencedColumnName = "id")
    private CarApplicant carApplicant;

    public User getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(User approvalUser) {
        this.approvalUser = approvalUser;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public CarApplicant getCarApplicant() {
        return carApplicant;
    }

    public void setCarApplicant(CarApplicant carApplicant) {
        this.carApplicant = carApplicant;
    }
}
