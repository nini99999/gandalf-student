package com.poshist.soa.repository;

import com.poshist.soa.entity.Receive;
import com.poshist.soa.entity.Send;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SendDao  extends CrudRepository<Send, Long>, JpaSpecificationExecutor<Receive> {
}
