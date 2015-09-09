package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.ComplaintHandler;

public interface ComplaintHandlerDao extends CrudRepository<ComplaintHandler, Integer>, JpaSpecificationExecutor<ComplaintHandler>{
	
	List<ComplaintHandler> findByTypeAndTidIn(Integer type, List<Integer> tids);
	
}
