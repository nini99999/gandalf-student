package com.poshist.student.vo;

import com.poshist.student.entity.Student;

import java.util.Date;

public class StudentVO {
    private Long id;
    private String name;
    private String code;
    private String nativePlace;
    private String identityCode;
    private String cardCode;
    private Long typeId;
    private String typeName;
    private Long genderId;
    private String genderName;
    private Long departmentId;
    private String departmentName;
    private Long startTime;
    private Long endTime;
    private Integer status;
    private Integer inStatus;
    public StudentVO(){}
    public StudentVO(Student student){
        setId(student.getId());
        setCode(student.getCode());
        setName(student.getName());
        setNativePlace(student.getNativePlace());
        setIdentityCode(student.getIdentityCode());
        if(null!=student.getType()) {
            setTypeId(student.getType().getId());
            setTypeName(student.getType().getName());
        }else{
            setTypeId(20L);
            setTypeName("本科生");
        }
        setGenderId(student.getGender().getId());
        setGenderName(student.getGender().getName());
        setDepartmentId(student.getDepartment().getId());
        setDepartmentName(student.getDepartment().getName());
        setCardCode(student.getCardCode());
        setStartTime(student.getStartTime().getTime());
        setEndTime(student.getEndTime().getTime());
        setStatus(student.getStatus());
        setInStatus(student.getInStatus());
    }
    public Student toDTO(Student student){
        student.setName(getName());
        student.setCode(getCode());
        student.setNativePlace(getNativePlace());
        student.setIdentityCode(getIdentityCode());
        student.setCardCode(getCardCode());
        student.setStartTime(new Date(getStartTime()));
        student.setEndTime(new Date(getEndTime()));
        return student;
    }

    public Integer getInStatus() {
        return inStatus;
    }

    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
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
