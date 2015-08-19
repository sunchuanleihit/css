package com.loukou.css.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.loukou.css.processor.ApiAuthorizationProcessor;

public class ApiInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private ApiAuthorizationProcessor apiAuthorizationProcessor;
	
	public static final String APP_KEY = "app_key";

	public static final String CALL_ID = "call_Id";

	public static final String SIG = "sig";


	private List<String> whiteList;

	public List<String> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(List<String> whiteList) {
		this.whiteList = whiteList;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Map<String, String> params = parseParams(request);

		String appKey = params.get(APP_KEY);
		String callid=params.get(CALL_ID);
		String sig=params.get(SIG);
		boolean result= apiAuthorizationProcessor.VerificationApiAuthorization(appKey, sig, callid);
		if(result)
		{
			return true;
		}
		else
		{
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return false;
		}
		
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseParams(HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Enumeration<String> paramsE = request.getParameterNames();
		while (paramsE.hasMoreElements()) {
			String paramName = paramsE.nextElement();
			paramsMap.put(paramName, request.getParameter(paramName));
		}

		return paramMapConvert(paramsMap);
	}

	private static Map<String, String> paramMapConvert(
			Map<String, Object> paramMap) {
		Map<String, String> paramStrMap = new HashMap<String, String>();
		if (paramMap != null) {
			for (String key : paramMap.keySet()) {
				Object val = paramMap.get(key);
				if (val instanceof Object[]) {
					Object[] values = (Object[]) val;
					if (values != null && values.length > 0) {
						val = values[0];
					} else {
						val = null;
					}
				}
				paramStrMap.put(key, String.valueOf(val));
			}
		}

		return paramStrMap;
	}
	
}
