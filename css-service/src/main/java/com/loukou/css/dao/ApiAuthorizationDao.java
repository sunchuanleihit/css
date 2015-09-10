package com.loukou.css.dao;

import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.ApiAuthorizationEntity;

public interface ApiAuthorizationDao extends CrudRepository<ApiAuthorizationEntity, Integer> {

	ApiAuthorizationEntity findByAppKeyAndIsUse(String appKey,int isUse);
}
