package com.poshist.car.vo;

import com.poshist.car.entity.CarOperation;

import java.util.Date;

public class CarOperationVO {
    private Long id;
    private String driverName;
    private Long carId;
    private String carNO;
    private Long startTime;
    private Long endTime;
    private Long typeId;
    private String typeName;
    private String backTypeName;
    public CarOperationVO(){}
    public CarOperationVO(CarOperation carOperation){
        setId(carOperation.getId());
        setStartTime(carOperation.getStartTime().getTime());
        setEndTime(carOperation.getEndTime().getTime());
        setDriverName(carOperation.getDriverName());
        setCarId(carOperation.getCar().getId());
        setCarNO(carOperation.getCar().getCarNO());
        setTypeId(carOperation.getType().getId());
        setTypeName(carOperation.getType().getName());
    }
    public CarOperation toDTO(CarOperation carOperation){
        carOperation.setStartTime(new Date(getStartTime()));
        carOperation.setEndTime(new Date(getEndTime()));
        carOperation.setDriverName(getDriverName());
        return carOperation;
    }

    public String getBackTypeName() {
        Date now=new Date();
        if(now.before(new Date(startTime))){
            return "未出车";
        }else if(now.after(new Date(endTime)))
            {
            return "正常返回";
        }else {
            return "未返回";
        }
    }

    public void setBackTypeName(String backTypeName) {
        this.backTypeName = backTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
