package com.loukou.css.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.Order;
import com.loukou.css.entity.OrderGoods;


public interface OrderGoodsDao extends CrudRepository<OrderGoods, Integer>{

	List<OrderGoods> findByOrderId(int orderId);

	List<OrderGoods> findByOrderIdIn(Collection<Integer> orderIds);

	@Modifying
	@Query(value="DELETE FROM tcz_order_goods WHERE order_id = ?1", 
		nativeQuery=true)
	int deleteByOrderId(int orderId);
	
	@Query("SELECT o FROM OrderGoods o WHERE orderId IN (?1) AND priceDiscount>0")
	List<OrderGoods> findByOrderIdsAndPriceDiscount(List<Integer> orderId);
}
