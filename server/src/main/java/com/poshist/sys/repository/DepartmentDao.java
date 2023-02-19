package com.poshist.sys.repository;

import com.poshist.sys.entity.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentDao extends CrudRepository<Department, Long> {
    List<Department> findAllByParentDepartmentIdAndStatusOrderById(Long parentId, Integer status);

    List<Department> findAllByStatusAndAndCodeLike(Integer status, String code);

    Department findFirstByName(String name);

    Department findFirstByCode(String code);
    Department findFirstByNameAndParentDepartment(String name,Department department);
}
