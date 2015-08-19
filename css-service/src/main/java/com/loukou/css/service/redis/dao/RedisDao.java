package com.loukou.css.service.redis.dao;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class RedisDao implements InitializingBean {
	
	public static final String TOKEN_TOKEN_ID = "USER_SERVICE_TOKEN_TOKEN_ID_%s";	// token 的key
	public static final String TOKEN_USER_ID = "USER_SERVICE_TOKEN_USER_ID_%d";		// token 的key
	
	public static final String PHONE_VERI_CODE_KEY = "USER_SERVICE_PHONE_VERI_CODE_KEY_%s";		// 手机验证码 的key
	
	public static final String PIC_VERI_CODE_KEY = "USER_SERVICE_PIC_VERI_CODE_KEY_%s";		// 手机验证码 的key
	@Resource(name="redisTemplate")
	protected RedisTemplate<String, Object> redisTemplate;
	
	protected ValueOperations<String, Object> valueOperations = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		valueOperations = redisTemplate.opsForValue();	
	}

}
