package com.poshist.car.repository;

import com.poshist.car.entity.CarApplicant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CarApplicantDao extends CrudRepository<CarApplicant, Long>, JpaSpecificationExecutor<CarApplicant> {
}
