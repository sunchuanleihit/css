package com.loukou.css.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.Complaint;

public interface ComplaintDao extends CrudRepository<Complaint, Integer>, JpaSpecificationExecutor<Complaint>{

}
