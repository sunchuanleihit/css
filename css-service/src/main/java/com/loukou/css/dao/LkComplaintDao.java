
package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.loukou.css.entity.LkComplaint;


public interface LkComplaintDao extends PagingAndSortingRepository<LkComplaint, Integer>,JpaSpecificationExecutor<LkComplaint>{

	@Query("select c from LkComplaint c where id in ?1")
	List<LkComplaint> getByIds(List<Integer> idList);

	@Transactional(value="transactionManagerMall")
	@Modifying
	@Query("UPDATE LkComplaint set userName = ?2,mobile = ?3,whId = ?4,whName = ?5,goodsName = ?6,content = ?7,"
			+ "department = ?8,complaintType = ?9,handleStatus = ?10,finishTime = ?11,actor = ?12"
			+ " where id = ?1")
	int updateComplaintById(int complaintId,String userName,String mobile,int whId,String whName,String goodsName,String content,int department,
			String complaintType,int handleStatus,Date finishTime,String actor);
}

