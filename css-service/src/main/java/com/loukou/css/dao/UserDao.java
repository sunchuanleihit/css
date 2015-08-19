package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.loukou.css.entity.UserEntity;

public interface UserDao extends CrudRepository<UserEntity, Integer> {
	/**
	 *查询所有用户 
	 **/
	@Query("select c from UserEntity c")
	List<UserEntity> getAllUser();

/*	@Query("SELECT c FROM UserEntity c where c.userName like :uc% and c.userRealname like %:un% and c.userType = ?3")*/
	Page<UserEntity> findByUserCodeLikeAndUserNameLikeAndIsUseAndUserIdIn(String userCode, String userName, int isUse,Iterable<Integer> userIds,Pageable pageRequest);
	
	/*@Query("SELECT c FROM UserEntity c where c.userName like :uc% and c.userRealname like %:un%")*/
	Page<UserEntity> findByUserCodeLikeAndUserNameLikeAndUserIdIn(String userCode, String userName, Iterable<Integer> userIds,Pageable pageRequest);
	
	/*@Query("SELECT c FROM UserEntity c where c.userName like :uc% and c.userRealname like %:un% and c.userType = ?3")*/
	Page<UserEntity> findByUserCodeLikeAndUserNameLikeAndIsUse(String userCode, String userName, int isUse,Pageable pageRequest);
	
	/*@Query("SELECT c FROM UserEntity c where c.userName like :uc% and c.userRealname like %:un%")*/
	Page<UserEntity> findByUserCodeLikeAndUserNameLike(String userCode, String userName,Pageable pageRequest);
	
	
	UserEntity findByUserCode(String userCode);
	
	UserEntity findByUserId(int userId);
	
	@Modifying
	@Query("update UserEntity set userName = ?2,userRealname= ?3 where userId = ?1 ")
	int updateByUserId(int userId,String userName , String userRealname);
	
	@Modifying
	@Query("update UserEntity set userPwd = ?2 where userId = ?1 ")
	int updateUserPwd(int userId,String userPwd);
	
	@Modifying
	@Transactional
	@Query("update UserEntity set isUse = ?2 where userId = ?1 ")
	int closeOrOpenUser(int userId,int userType);
	
	List<UserEntity> findByUserIdIn(List<Integer> ids);
	
	List<UserEntity> findByUserNameLike(String userName);
}
