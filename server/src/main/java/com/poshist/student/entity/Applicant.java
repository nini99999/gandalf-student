package com.poshist.student.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_st_applicant_info")
@EntityListeners(AuditingEntityListener.class)
public class Applicant  extends AbstractEntity {
    private Date startTime;
    private Date  endTime;
    private String applicantUserId;
    @OneToOne
    @JoinColumn(name = "create_user_id",referencedColumnName = "id")
    private User createUser;
    private String address;
    private String reason;

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

    public String getApplicantUserId() {
        return applicantUserId;
    }

    public void setApplicantUserId(String applicantUserId) {
        this.applicantUserId = applicantUserId;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
