package com.loukou.css.service;

import com.loukou.css.bo.CssBaseRes;

public interface CssService {
	//发送开票提醒
	public CssBaseRes<String> sendBillNotice(String orderSnMain,String actor);
}
