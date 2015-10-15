package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.OrderReturn;

public interface OrderReturnDao extends CrudRepository<OrderReturn, Integer>,JpaSpecificationExecutor<OrderReturn>{
	
	List<OrderReturn> findByAddTimeBetweenAndOrderStatus(String startDate, String endDate, int orderStatus);
	
}
