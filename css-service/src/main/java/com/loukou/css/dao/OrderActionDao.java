package com.loukou.css.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.loukou.css.entity.OrderAction;


public interface OrderActionDao extends CrudRepository<OrderAction, Integer>{

	List<OrderAction> findByActionTimeBetweenAndActionIn(Date start, Date end, List<Integer> actions);

}
