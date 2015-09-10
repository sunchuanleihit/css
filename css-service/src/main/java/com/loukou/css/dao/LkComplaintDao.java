
package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.loukou.css.entity.LkComplaint;


public interface LkComplaintDao extends PagingAndSortingRepository<LkComplaint, Integer>,JpaSpecificationExecutor<LkComplaint>{

	@Query("select c from LkComplaint c where id in ?1")
	List<LkComplaint> getByIds(List<Integer> idList);
	
}

