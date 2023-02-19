package com.poshist.car.repository;

import com.poshist.car.entity.CarApplicant;
import com.poshist.car.entity.CarApproval;
import com.poshist.sys.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarApprovalDao  extends CrudRepository<CarApproval, Long>, JpaSpecificationExecutor<CarApproval> {
    public List<CarApproval> findByApprovalUserAndStatus(User user,Integer status);
    public CarApproval findFirstByCarApplicantAndCode(CarApplicant carApplicant,String code);
    public List<CarApproval> findByApprovalUser(User user);
}
