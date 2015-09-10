package com.loukou.css.service;

import java.util.List;

import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.resp.ComplaintRespListDto;

public interface CssService {
	//发送开票提醒
	public CssBaseRes<String> sendBillNotice(String orderSnMain,String actor);
	
	/**
	 * 获取所有的城市站点
	 * @return
	 */
	public List<Site> getAllSite();

	/**
	 * 根据站点码获取所有微仓
	 * @param siteCode
	 * @return
	 */
	public List<Store> getStoresBySiteCode(String siteCode);

	/**
	 * 查询投诉
	 * @param orderSnMain
	 * @param weic
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param rows
	 * @return
	 */
	public ComplaintRespListDto queryComplaint(String orderSnMain, String weic, String startTime, String endTime, int page, int rows);
	
	public CssBaseRes<String> generateComplaint(String actor,int complaintId,String orderSnMain,int whId,String whName,String[] goodsNameList,String content,String creatTime,String userName,String mobile,int department,String complaintType,int handleStatus);
}