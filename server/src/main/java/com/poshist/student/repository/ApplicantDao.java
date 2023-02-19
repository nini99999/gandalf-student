package com.poshist.student.repository;

import com.poshist.student.entity.Applicant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ApplicantDao extends CrudRepository<Applicant, Long>, JpaSpecificationExecutor<Applicant> {

}
