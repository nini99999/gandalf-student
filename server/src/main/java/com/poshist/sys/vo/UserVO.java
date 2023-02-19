package com.poshist.sys.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poshist.common.Constant;
import com.poshist.sys.entity.User;

public class UserVO {
    private Long id;
    private String userName;
    private String realName;
    private String roleName;
    private Long roleId;
    private String roleMemo;
    private String departmentName;
    private Long departmentId;
    @JsonProperty("isNew")
    private boolean isNew;
    private Long positionId;
    private String positionName;
    private Long genderId;
    private String genderName;
    private String code;
    private String mobile;
    private String password;
    private String oldPassword;
    private String cardNo;
    public UserVO() {
    }

    public UserVO(User user) {
        this.userName = user.getUserName();
        this.realName = user.getRealName();
        this.roleName = user.getUserRoles().get(0).getRole().getName();
        this.roleId = user.getUserRoles().get(0).getRole().getId();
        this.roleMemo = user.getUserRoles().get(0).getRole().getMemo();
        this.departmentId = user.getDepartment().getId();
        this.departmentName = user.getDepartment().getName();
        if(null!=user.getPosition()){
        this.positionId = user.getPosition().getId();
        this.positionName = user.getPosition().getName();
        }
        this.genderId = user.getGender().getId();
        this.genderName = user.getGender().getName();
        this.code = user.getCode();
        this.mobile=user.getMobile();
        this.cardNo=user.getCardNo();
        this.id=user.getId();
        if (Constant.VALID == user.getIsNew()) {
            this.isNew = true;
        } else {
            this.isNew = false;
        }
    }
    public void toDTO(User user){
        user.setMobile(getMobile());
        user.setRealName(getRealName());
        user.setUserName(getUserName());
        user.setCode(getCode());
        user.setCardNo(getCardNo());
        user.setId(getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
