package com.poshist.car.repository;

import com.poshist.car.entity.Car;
import com.poshist.car.entity.CarApplicant;
import com.poshist.car.entity.CarControl;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CarControlDao extends CrudRepository<CarControl, Long>, JpaSpecificationExecutor<CarControl> {
    //    @Query(value = " from CarControl c where NOT  ((c.estimateEndTime < ?1) OR (c.estimateStartTime > ?2)) and c.status=0")
//    public List<CarControl> findAllVALIDControl(Date startTime, Date endTime);
    List<CarControl> findAllByCarApplicant(CarApplicant carApplicant);

    Integer countAllByCarApplicantAndStatus(CarApplicant carApplicant, Integer status);

    List<CarControl> findAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStatus(Date date1, Date date2, Integer status);

    Integer countAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStatus(Date date1, Date date2, Integer status);

    @Query(nativeQuery = true, value = "select u.ddate,count(u.ddate)-1 from(select c.date_name as ddate from t_sys_calendar_info c where c.date_name>=?1 and c.date_name<=?2 UNION ALL select DATE_FORMAT(t.estimate_start_time,'%Y-%m-%d') from t_car_control_info t where t.estimate_start_time>=?1 and t.estimate_start_time<=?2) u group by u.ddate order by u.ddate")
    List<Object[]> findCountCarControlDaily(Date minDate, Date maxDate);
    @Query(nativeQuery = true, value = "select left(d.code,6),count(t.id) from t_car_control_info t left join t_sys_user u on t.applicant_user_id=u.id left join t_sys_department_info d on u.department_id=d.id  where t.estimate_start_time>=?1 and t.estimate_start_time<=?2 group by left(d.code,6)")
    List<Object[]> findCountCarControlDepartment(Date minDate, Date maxDate);
    CarControl findFirstByCarAndStatusAndEstimateStartTimeLessThanEqual(Car car,Integer status,Date date);
    @Query(nativeQuery = true, value = "select count(*) from t_car_control_info t where DATE_FORMAT(t.estimate_start_time,'%Y-%m-%d')=?1")
    Integer countAllByDay(String dateStr);
    List<CarControl> findAllByStatus(Integer status);
}