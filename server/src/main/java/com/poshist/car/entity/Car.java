package com.poshist.car.entity;

import com.poshist.common.entity.AbstractEntity;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_car_info")
@EntityListeners(AuditingEntityListener.class)
public class Car extends AbstractEntity {
    private String carNO;
    @OneToOne
    @JoinColumn(name = "car_type_id",referencedColumnName = "id")
    private Dictionary carType;
    private Date buyTime;
    private Date insuranceTime;
    private Date maintenanceTime;
    private Date checkTime;
    private Double mileage;
    @OneToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToOne
    @JoinColumn(name = "status",referencedColumnName = "id")
    private Dictionary status;

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }

    public Dictionary getCarType() {
        return carType;
    }

    public void setCarType(Dictionary carType) {
        this.carType = carType;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Date getInsuranceTime() {
        return insuranceTime;
    }

    public void setInsuranceTime(Date insuranceTime) {
        this.insuranceTime = insuranceTime;
    }

    public Date getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(Date maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dictionary getStatus() {
        return status;
    }

    public void setStatus(Dictionary status) {
        this.status = status;
    }
}
