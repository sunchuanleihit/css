package com.loukou.css.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.loukou.css.annotation.AuthPassport;
import com.loukou.css.contains.PageUrls;
import com.loukou.css.service.redis.entity.SessionEntity;
import com.loukou.css.service.SessionRedisService;
import com.loukou.css.util.SessionUtil;

public class WhInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	SessionRedisService whSessionRedisService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			
			AuthPassport authPassport = handlerMethod.getMethodAnnotation(AuthPassport.class);

			if (authPassport == null && handlerMethod.getBean()!=null) {
				authPassport = handlerMethod.getBean().getClass().getAnnotation(
						AuthPassport.class);
			}

			if (authPassport != null && authPassport.needValidate()) {
				SessionEntity sessionEntity = checkLogin(request);
				if (sessionEntity==null) {
					response.sendRedirect(getLoginUrl(request));
					return false;
				}

				if (!checkAuthorityType(authPassport,sessionEntity)) {
					response.sendRedirect(getLoginUrl(request));
					return false;
				}
			}
			
			return true;
		}

		return true;
	}

	private String getLoginUrl(HttpServletRequest request){
		return request.getContextPath()+"/"+ PageUrls.LOGIN_URL;
	}
	
	private SessionEntity checkLogin(HttpServletRequest request) {
		String sessionKey = SessionUtil.getSessionKey(request,request.getSession());
		SessionEntity sessionEntity = whSessionRedisService
				.getWhSessionEntity(sessionKey);

		return sessionEntity;
	}

	private boolean checkAuthorityType(AuthPassport authPassport,SessionEntity sessionEntity) {
		return true;
	}
}
