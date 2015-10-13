package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.TczAdmin;

public interface TczAdminDao extends CrudRepository<TczAdmin, Integer>{
	
	@Query("select ad from TczAdmin ad where ad.id = ?1 or ad.parentId = ?1")
	List<TczAdmin> getAllCallCenterAdmin(Integer manager);

}
