package com.poshist.car.vo;

import com.poshist.car.entity.CarApproval;

import java.util.Date;

public class CarApprovalVO {
    private Long id;
    private Long carApplicantId;
    private String code;
    private Integer status;
    private String opinion;
    private Date approvalTime;
    private Long approvalUserId;
    private String approvalUserName;
    private Long departmentId;
    private String departmentName;
    private CarApplicantVO carApplicant;
    public CarApprovalVO(CarApproval carApproval){
        setCarApplicantId(carApproval.getCarApplicant().getId());
        setId(carApproval.getId());
        setCode(carApproval.getCode());
        setStatus(carApproval.getStatus());
        setOpinion(carApproval.getOpinion());
        setApprovalTime(carApproval.getApprovalTime());
        setApprovalUserId(carApproval.getApprovalUser().getId());
        setApprovalUserName(carApproval.getApprovalUser().getRealName());
        setDepartmentId(carApproval.getDepartment().getId());
        setDepartmentName(carApproval.getDepartment().getName());
    }
    public CarApproval toDTO(CarApproval carApproval){
        carApproval.setStatus(getStatus());
        carApproval.setOpinion(getOpinion());
        return carApproval;
    }

    public CarApplicantVO getCarApplicant() {
        return carApplicant;
    }

    public void setCarApplicant(CarApplicantVO carApplicant) {
        this.carApplicant = carApplicant;
    }

    public CarApprovalVO(){}

    public Long getCarApplicantId() {
        return carApplicantId;
    }

    public void setCarApplicantId(Long carApplicantId) {
        this.carApplicantId = carApplicantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Long approvalUserId) {
        this.approvalUserId = approvalUserId;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
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
