package com.poshist.car.repository;

import com.poshist.car.entity.Driver;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DriverDao extends CrudRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
}
