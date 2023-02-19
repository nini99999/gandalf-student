package com.poshist.car.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Dictionary;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_car_operation_info")
@EntityListeners(AuditingEntityListener.class)
public class CarOperation extends AbstractEntity {
    private Date startTime;
    private Date endTime;
    @OneToOne
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    private Car car;
    private  String driverName;
    @OneToOne
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private Dictionary type;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }
}
