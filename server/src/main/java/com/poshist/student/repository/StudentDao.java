package com.poshist.student.repository;

import com.poshist.student.entity.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Student, Long>, JpaSpecificationExecutor<Student>  {
     Student findFirstByCardCodeAndStatus(String cardCode,Integer status);
     Integer countAllByStatus(Integer status);
     Integer countAllByInStatus(Integer inStatus);
     Student findFirstByIdentityCode(String IdenityCode);
     Student findFirstByCode(String code);
     @Query(nativeQuery = true, value = "select count(t.id) from t_st_student_info t  left join t_sys_department_info d on t.department_id=d.id  where d.code like CONCAT(?1,'%') and t.status=0")
     Integer countDepartmentCode(String code);

}
