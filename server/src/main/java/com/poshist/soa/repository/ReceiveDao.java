package com.poshist.soa.repository;

import com.poshist.soa.entity.Receive;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReceiveDao extends CrudRepository<Receive, Long>, JpaSpecificationExecutor<Receive> {
}
