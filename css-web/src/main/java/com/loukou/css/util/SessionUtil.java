package com.loukou.css.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static String getSessionKey(HttpServletRequest request,HttpSession session){
		return request.getContextPath()+"_"+session.getId();
	}
}
