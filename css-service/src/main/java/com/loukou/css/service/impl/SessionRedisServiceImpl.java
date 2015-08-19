package com.loukou.css.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.service.redis.dao.SessionRedisDao;
import com.loukou.css.service.redis.entity.SessionEntity;
import com.loukou.css.service.SessionRedisService;

@Service
public class SessionRedisServiceImpl implements SessionRedisService{
	
	@Autowired
	private SessionRedisDao SessionRedisDao;
	
	@Override
	public boolean save(SessionEntity whSessionEntity) {
		return SessionRedisDao.setSessionEntity(whSessionEntity);
	}

	@Override
	public SessionEntity getWhSessionEntity(String sessionId) {
		return SessionRedisDao.getSession(sessionId);
	}

	@Override
	public boolean deleteWhSessionEntity(String sessionId) {
		return SessionRedisDao.deleteSessionEntity(sessionId);
	}
}
