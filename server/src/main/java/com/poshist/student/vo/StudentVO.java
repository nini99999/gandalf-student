package com.poshist.student.vo;

import com.poshist.student.entity.Student;
import lombok.Data;

import java.util.Date;
@Data
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
    private String faceId;

    public StudentVO() {
    }

    public StudentVO(Student student) {
        setId(student.getId());
        setCode(student.getCode());
        setName(student.getName());
        setNativePlace(student.getNativePlace());
        setIdentityCode(student.getIdentityCode());
        setFaceId(student.getFaceId());
        if (null != student.getType()) {
            setTypeId(student.getType().getId());
            setTypeName(student.getType().getName());
        } else {
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

    public Student toDTO(Student student) {
        student.setName(getName());
        student.setCode(getCode());
        student.setNativePlace(getNativePlace());
        student.setIdentityCode(getIdentityCode());
        student.setCardCode(getCardCode());
        student.setStartTime(new Date(getStartTime()));
        student.setEndTime(new Date(getEndTime()));
        return student;
    }
}
