package com.poshist.car.repository;

import com.poshist.car.entity.CarOperation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CarOperationDao extends CrudRepository<CarOperation, Long>, JpaSpecificationExecutor<CarOperation> {
    public List<CarOperation> findAllByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndStatus(Date date1, Date date2, Integer status);
    public List<CarOperation> findAllByStatus(Integer status);
    public Integer countAllByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Date date1, Date date2);
}
