package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.loukou.css.entity.OrderPay;

public interface OrderPayDao extends CrudRepository<OrderPay, Integer>{

	List<OrderPay> findByOrderId(int orderId);
	
	@Query(value="SELECT * FROM tcz_order_pay WHERE order_sn_main = ?1 AND status = 'succ' ", nativeQuery=true)
	List<OrderPay> findByOrderSnMain(String orderSnMain);

	@Modifying
	@Query(value="DELETE FROM tcz_order_pay WHERE order_id = ?1 AND status = ?2 ", nativeQuery=true)
	int deleteByOrderIdAndStatus(int orderId, String status);
	
	@Query(value="SELECT SUM(money) FROM tcz_order_pay WHERE order_sn_main = ?1", nativeQuery=true)
	Double getPayedAmountByOrderSnMain(String orderSnMain);

	List<OrderPay> findByOrderSnMainAndStatus(String orderSnMain, String status);

	List<OrderPay> findByOrderIdAndStatus(int orderId, String status);
	
	@Query("SELECT o FROM OrderPay o WHERE orderId IN (?1) AND paymentId NOT IN (?2)")
	List<OrderPay> getByOrderIdsAndNotPaymentId(List<Integer> orderId,List<Integer> paymentId);
}
