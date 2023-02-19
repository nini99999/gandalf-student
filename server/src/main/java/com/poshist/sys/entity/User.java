package com.poshist.sys.entity;

import com.poshist.common.entity.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_SYS_USER")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractEntity {
    private String userName;
    private String password;
    private Integer status;
    private String realName;
    private String mobile;
    private String email;
    @OneToOne
    private Department department;
    @OneToMany( cascade=CascadeType.ALL,orphanRemoval=true,mappedBy = "user")

    private List<UserRole> userRoles;
    private Integer isNew;
    @OneToOne
    @JoinColumn(name = "position_id",referencedColumnName = "id")
    private Dictionary position;
    @OneToOne
    @JoinColumn(name = "gender_id",referencedColumnName = "id")
    private Dictionary gender;
    private String code;
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Dictionary getGender() {
        return gender;
    }

    public void setGender(Dictionary gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        if(null!=this.userRoles){
            this.userRoles.clear();
            this.userRoles.addAll(userRoles);
        }else {

            this.userRoles = userRoles;
        }
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Dictionary getPosition() {
        return position;
    }

    public void setPosition(Dictionary position) {
        this.position = position;
    }
}
