package com.poshist.student.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_st_student_info")
@EntityListeners(AuditingEntityListener.class)
public class Student extends AbstractEntity {
    private static final long serialVersionUID = 5906308820867822214L;
    private String name;
    private String code;
    private String nativePlace;
    private String identityCode;
    private String cardCode;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Integer inStatus;
    private Date lastViaTime;
    private String mobile;
    private String thirdId;
    @OneToOne
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private Dictionary type;
    @OneToOne
    @JoinColumn(name = "gender_id",referencedColumnName = "id")
    private Dictionary gender;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getInStatus() {
        return inStatus;
    }

    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
    }

    public Date getLastViaTime() {
        return lastViaTime;
    }

    public void setLastViaTime(Date lastViaTime) {
        this.lastViaTime = lastViaTime;
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

    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }

    public Dictionary getGender() {
        return gender;
    }

    public void setGender(Dictionary gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
