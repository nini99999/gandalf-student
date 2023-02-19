package com.poshist.sys.repository;

import com.poshist.sys.entity.Pic;
import org.springframework.data.repository.CrudRepository;

public interface PicDao extends CrudRepository<Pic, Long> {
   Pic findFirstByTypeAndObjectId(String type,Long objectId);
}
