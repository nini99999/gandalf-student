package com.poshist.student.vo;

import com.poshist.student.entity.Applicant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicantVO {
    private Long id;
    private Long startTime;
    private Long endTime;
    private String applicantUserId;
    private List<LeaveVO> leaveVOS;
    private String address;
    private String reason;
    private Long creatUserId;
    private String creatUserName;
    public ApplicantVO (){}
    public ApplicantVO(Applicant applicant){
        setStartTime(applicant.getStartTime().getTime());
        setEndTime(applicant.getEndTime().getTime());
        setApplicantUserId(applicant.getApplicantUserId());
        setAddress(applicant.getAddress());
        setReason(applicant.getReason());
        setId(applicant.getId());
        setCreatUserId(applicant.getCreateUser().getId());
        setCreatUserName(applicant.getCreateUser().getRealName());
    }
    public Applicant toDTO(Applicant applicant){
        applicant.setStartTime(new Date(getStartTime()));
        applicant.setEndTime(new Date(getEndTime()));
        applicant.setApplicantUserId(getApplicantUserId());
        applicant.setAddress(getAddress());
        applicant.setReason(getReason());

        return applicant;
    }

    public Long getCreatUserId() {
        return creatUserId;
    }

    public void setCreatUserId(Long creatUserId) {
        this.creatUserId = creatUserId;
    }

    public String getCreatUserName() {
        return creatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        this.creatUserName = creatUserName;
    }

    public void addLeaveVO(LeaveVO leaveVO){
        if(null==leaveVOS){
            leaveVOS=new ArrayList<>();
        }
        leaveVOS.add(leaveVO);
    }
    public List<LeaveVO> getLeaveVOS() {
        return leaveVOS;
    }

    public void setLeaveVOS(List<LeaveVO> leaveVOS) {
        this.leaveVOS = leaveVOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getApplicantUserId() {
        return applicantUserId;
    }

    public void setApplicantUserId(String applicantUserId) {
        this.applicantUserId = applicantUserId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
