package com.loukou.css.service;

import com.loukou.css.service.redis.entity.SessionEntity;

public interface SessionRedisService {
	public boolean save(SessionEntity whSessionEntity);
	
	public SessionEntity getWhSessionEntity(String sessionId);
	
	public boolean deleteWhSessionEntity(String sessionId);
}
