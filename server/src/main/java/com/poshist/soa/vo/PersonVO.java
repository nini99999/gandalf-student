package com.poshist.soa.vo;

import com.poshist.common.utils.CommonUtils;
import com.poshist.student.entity.Student;

public class PersonVO {
    private String personId;
    private String deptId;
    private String personNo;
    private String personName;
    private Integer personGender;
    private String remark;
    private String personPhoto;
    private String certificateType;
    private String identityNo;
    private String mobile;
    private String rfid;
    private String tel1;
    private String tel2;
    private String email;
    private String roomNo;
    private String address;
    private String tenementType;

    public PersonVO() {
    }

    public PersonVO(Student student) {
        if (null == student.getThirdId()) {
            setPersonId("");
        } else {
            setPersonId(student.getThirdId());
        }
        setDeptId("");
        setPersonNo("");
        setPersonName("");
        setRemark("");
        setPersonPhoto("");
        setCertificateType("");
        setIdentityNo("");
        setMobile("");
        setRfid("");
        setTel1("");
        setTel2("");
        setEmail("");
        setRoomNo("");
        setAddress("");
        setTenementType("");
        setPersonNo(CommonUtils.generateZeroCode(19, student.getId().toString()));
        setPersonName(student.getName());
        //setDeptId("5a9dc528-e7ad-4952-9f21-a885b6564a1c");
        if (null == student.getDepartment().getThirdId()) {
            setDeptId("6fe18bf6-4616-4d68-9a0d-e42a55dc31ca");
        } else {
            setDeptId(student.getDepartment().getThirdId());
        }
        if ("男".equals(student.getGender().getName())) {
            setPersonGender(1);
        } else {
            setPersonGender(0);
        }


        if (null != student.getCode()) {
            setCertificateType("STUDENT");
            setIdentityNo(student.getCode());

        } else if (null != student.getIdentityCode()) {
            setCertificateType("GENERIDENT");
            setIdentityNo(student.getIdentityCode());
        } else {
            setCertificateType("GENERIDENT");
            setIdentityNo("110520196605232023");
        }

        if (null == student.getMobile()) {
            setMobile("15" + CommonUtils.generateZeroCode(9, student.getId().toString()));
        } else {
            setMobile(student.getMobile());
        }
        setRfid(student.getCardCode());

    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTenementType() {
        return tenementType;
    }

    public void setTenementType(String tenementType) {
        this.tenementType = tenementType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getPersonGender() {
        return personGender;
    }

    public void setPersonGender(Integer personGender) {
        this.personGender = personGender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
}
