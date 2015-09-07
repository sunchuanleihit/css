package com.loukou.css.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.MemberRate;


public interface MemberRateDao extends CrudRepository<MemberRate, Integer> {
	@Query("SELECT o FROM MemberRate o WHERE userName=?1 AND status=15 AND (type=8 or type=9 or type=10)")
	MemberRate getMsgByUserName(String userName);
}
