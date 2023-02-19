package com.poshist.car.vo;

import com.poshist.car.entity.Driver;

public class DriverVO{
    private Long id;
    private String name;
    private String statusName;
    public DriverVO (){}
    public DriverVO(Driver driver){
        setId(driver.getId());
        setName(driver.getName());
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
