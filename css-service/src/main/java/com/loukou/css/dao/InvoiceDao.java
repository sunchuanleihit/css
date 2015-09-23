package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.loukou.css.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Integer> {
	@Query("SELECT o FROM Invoice o WHERE orderSnMain=?1")
	List<Invoice> findByOrderSnMain(String orderSnMain);
	
	@Transactional
	@Modifying
	@Query("UPDATE Invoice set orderAmount = ?2 where invoiceId = ?1")
	int updateOrderAmount(int invoiceId,double orderAmount);
	
	@Transactional
	@Modifying
	@Query("UPDATE Invoice set valid = ?2 where order_sn_main = ?1")
	int updateOrderValid(String orderSnMain,int valid);
}