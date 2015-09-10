package com.loukou.css.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.dao.ApiAuthorizationDao;
import com.loukou.css.entity.ApiAuthorizationEntity;
import com.loukou.css.service.ApiAuthorizationService;

@Service
public class ApiAuthorizationServiceImpl implements ApiAuthorizationService {
	
	@Autowired
	private  ApiAuthorizationDao apiAuthorizationDao;
	
	@Override
	public ApiAuthorizationEntity getApiAuthorizationByAppKey(String appKey)
	{
		return apiAuthorizationDao.findByAppKeyAndIsUse(appKey,0);
	}
}
