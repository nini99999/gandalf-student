package com.poshist.soa.repository;

import com.poshist.soa.entity.Via;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ViaDao extends CrudRepository<Via, Long>, JpaSpecificationExecutor<Via> {
    public List<Via> getAllByCardCodeAndCardTypeAndViaTimeBetween(String cardCode, int cardType, Date startTime, Date endTime);
}
