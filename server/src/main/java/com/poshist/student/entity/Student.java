package com.poshist.student.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author poshist
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_st_student_info")
@EntityListeners(AuditingEntityListener.class)
@Data
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
    //0校外 1在校
    private Integer inStatus;
    private Date lastViaTime;
    private String mobile;
    private String thirdId;
    //0 未同步 1 同步成功
    private Integer jieShunStatus;
    //0 未同步 1 同步成功
    private Integer hikVisionStatus;
    private String faceId;
    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Dictionary type;
    @OneToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Dictionary gender;
    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

}
