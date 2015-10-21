package com.loukou.css.interceptor;

import java.util.HashSet;
import java.util.Set;

import com.loukou.auth.core.interceptor.AuthInterceptor;

public class MyAuthInterceptor extends AuthInterceptor {


	private Set<String> whiteList = new HashSet<String>();
	
	
	public Set<String> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(Set<String> whiteList) {
		this.whiteList = whiteList;
	}
	
	
	
	
	
}
