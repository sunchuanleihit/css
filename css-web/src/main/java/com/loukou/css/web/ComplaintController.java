package com.loukou.css.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.resp.ComplaintRespListDto;
import com.loukou.css.service.CssService;
import com.loukou.css.util.DataGrid;

@Controller
@RequestMapping("/complaint")
public class ComplaintController {
	@Autowired
	private CssService cssService;
	
	@RequestMapping(value="/complaintList")
	public ModelAndView complaintList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("complaint/complaintList");
		return mv;
	}

	@RequestMapping("/getAllCity")
	@ResponseBody
	public List<Site> getAllCity(){
		List<Site> siteList  = cssService.getAllSite();
		return siteList;
	}
	
	@RequestMapping("/getStores")
	@ResponseBody
	public List<Store> getStores(HttpServletRequest request,HttpServletResponse response){
		String siteCode = request.getParameter("siteCode");
		List<Store> storeList = new ArrayList<Store>();
		if(StringUtils.isBlank(siteCode)){
			return null;
		}
		storeList = cssService.getStoresBySiteCode(siteCode);
		return storeList;
	}
	
	@RequestMapping("/findComplaint")
	@ResponseBody
	public DataGrid findComplaint(HttpServletRequest request, int page, int rows){
		String orderSnMain = request.getParameter("orderSnMain");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String weic = request.getParameter("weic");
		ComplaintRespListDto respListDto = cssService.queryComplaint(orderSnMain,weic,startTime,endTime,page,rows);
		DataGrid grid = new DataGrid();
		grid.setTotal(respListDto.getCount());
		grid.setRows(respListDto.getComplaintList());
		return grid;
	}
}
