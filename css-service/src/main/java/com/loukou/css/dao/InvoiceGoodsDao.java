package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.InvoiceGoods;

public interface InvoiceGoodsDao extends CrudRepository<InvoiceGoods, Integer> {
	@Query("SELECT o FROM Invoice o WHERE orderSnMain=?1")
	List<InvoiceGoods> findByOrderSnMain(String orderSnMain);
}