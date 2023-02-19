package com.poshist.car.repository;

import com.poshist.car.entity.Car;
import com.poshist.sys.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarDao extends CrudRepository<Car, Long> , JpaSpecificationExecutor<Car> {
    public List<Car> findAllByStatusNotOrderByMileage(Dictionary status);
    public List<Car> findAllByStatusNotAndCarTypeOrderByMileage(Dictionary status,Dictionary carType);
    public List<Car> findAllByStatusIn(List<Dictionary> status);
    public Car findFirstByCarNO(String carNO);
}
