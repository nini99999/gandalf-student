package com.poshist.soa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poshist.common.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_soa_via_info")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Via extends AbstractEntity {
    private static final long serialVersionUID = 5728063878362407972L;
    private Long studentId;
    private String cardCode;
    private Integer cardType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date viaTime;
    private Integer viaType;
    private String gateId;
    private String gateInfo;
    private Integer viaResult;
    private Integer status;
    @OneToOne
    @JoinColumn(name = "receive_id",referencedColumnName = "id")
    private Receive receive;


}
