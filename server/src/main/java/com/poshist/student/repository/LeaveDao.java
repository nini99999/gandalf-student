package com.poshist.student.repository;

import com.poshist.student.entity.Leave;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LeaveDao   extends CrudRepository<Leave, Long>, JpaSpecificationExecutor<Leave> {
    List<Leave> findAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqual( Date date1, Date date2);
    List<Leave> findAllByStatusAndEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqual(Integer status, Date date1, Date date2);
    List<Leave> findAllByStatusInAndEstimateEndTimeLessThanEqual(Integer[] status,Date date);
    @Query(nativeQuery = true, value = "select u.date,count(u.date)-1 from(select c.date_name as date from t_sys_calendar_info c where c.date_name>=?1 and c.date_name<=?2 UNION " +
            "ALL select DATE_FORMAT(t.estimate_start_time,'%Y-%m-%d') from t_st_leave_info t where t.estimate_start_time>=?1 and t.estimate_start_time<=?2  and t.data_status=0) " +
            "u group by u.date order by u.date")
    List<Object[]> findCountLeaveDaily(Date minTime, Date maxTime);
    @Query(nativeQuery = true, value = "select left(d.code,6),count(t.id) from t_st_leave_info t left join t_st_student_info u on t.student_id=u.id left join " +
            "t_sys_department_info d on u.department_id=d.id  where t.estimate_start_time>=?1 and t.estimate_start_time<=?2 and t.data_status=0 group by left(d.code,6)")
    List<Object[]> findCountLeaveDepartment(Date minDate, Date maxDate);
//    @Query(nativeQuery = true, value = "select count(t.id) from t_st_leave_info t left join t_st_student_info u on t.student_id=u.id left join t_sys_department_info d on u" +
//            ".department_id=d.id  where d.code like CONCAT(?1,'%') and DATE_FORMAT(t.estimate_start_time,'%Y-%m-%d')<=?2 and DATE_FORMAT(t.estimate_end_time,'%Y-%m-%d')>=?2  and t.data_status=0")
//    Integer countDepartmentCodeAndDate(String code,String dataStr);
//    public Leave findFirstByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStudent(Date date1, Date date2, Student student);
//
//    public Leave findFirstByEstimateStartTimeBetweenAndStudent(Date start,Date end,Student student);
//    public Leave findFirstByEstimateEndTimeBetweenAndStudent(Date start,Date end,Student student);
}
