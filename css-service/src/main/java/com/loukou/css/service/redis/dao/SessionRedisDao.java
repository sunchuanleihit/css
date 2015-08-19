package com.loukou.css.service.redis.dao;

import org.springframework.stereotype.Repository;
import com.loukou.css.service.redis.entity.SessionEntity;

@Repository
public class SessionRedisDao extends RedisDao {

	public SessionEntity getSession(String sessionId){
		return (SessionEntity) valueOperations.get(sessionId);
	}
	
	public boolean setSessionEntity(SessionEntity sessionEntity){
		valueOperations.set(sessionEntity.getSessionId(), sessionEntity);
		
		return true;
	}
	
	public boolean deleteSessionEntity(String sessionId){
		if(getSession(sessionId)==null){
			return true;
		}
		
		redisTemplate.delete(sessionId);
		
		return true;
	}
}
