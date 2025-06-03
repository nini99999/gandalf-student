package com.poshist.student.entity;

import com.poshist.common.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_st_leave_info")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Leave extends AbstractEntity {
    private Date estimateStartTime;
    private Date estimateEndTime;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private Integer dataStatus;
    @OneToOne
    @JoinColumn(name = "applicant_id",referencedColumnName = "id")
    private Applicant applicant;
    @OneToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

}
