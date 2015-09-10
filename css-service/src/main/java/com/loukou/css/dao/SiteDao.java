package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.Site;

public interface SiteDao extends CrudRepository<Site, Integer>, JpaSpecificationExecutor<Site>{
	
	@Query("select s from Site s where s.status = 1 order by s.id ASC")
	List<Site> getAllSite();
	
	

}
