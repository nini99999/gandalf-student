package com.poshist.student.repository;

import com.poshist.student.entity.Leave;
import com.poshist.student.entity.LeaveLimit;
import com.poshist.sys.entity.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LeaveLimitDao   extends CrudRepository<LeaveLimit, Long>, JpaSpecificationExecutor<LeaveLimit> {
    LeaveLimit findFirstByDepartment(Department department);
}
