package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.Store;


public interface StoreDao extends CrudRepository<Store, Integer>{

	/**
	 * 所有生鲜的店铺
	 * @return
	 */
	@Query("SELECT storeId FROM Store s WHERE storeTaotype = 1")
	List<Integer> getAllFreshStore();

	List<Store> findByStoreType(String storeType);

	List<Store> findByTelBusiness(String telBusiness);

	List<Store> findByStoreIdIn(List<Integer> storeIds);
	
	List<Store> findBySellSiteAndStoreType(String sellSite, String storeType, Pageable Pageable);
}
