package com.loukou.css.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.auth.core.annotation.AuthCheck;
import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.processor.UserProcessor;
import com.loukou.css.req.ComplaintReqDto;
import com.loukou.css.resp.ComplaintRespDto;
import com.loukou.css.resp.ComplaintRespListDto;
import com.loukou.css.service.CssService;
import com.loukou.css.service.redis.entity.SessionEntity;
import com.loukou.css.util.DataGrid;
import com.loukou.css.utils.DateUtils;
import com.loukou.order.service.api.BkOrderService;
import com.loukou.order.service.resp.dto.BkOrderListDto;
import com.loukou.order.service.resp.dto.BkOrderListRespDto;

@Controller
@RequestMapping("/complaint")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class ComplaintController extends  BaseController{
	@Autowired
	private CssService cssService;
	
	@Autowired
	private BkOrderService bkOrderService;
	
	@Autowired 
    private UserProcessor userProcessor;
	
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
	public DataGrid findComplaint(HttpServletRequest request, int page, int rows, ComplaintReqDto complaintReqDto){
		ComplaintRespListDto respListDto = cssService.queryComplaint(complaintReqDto,page,rows);
		DataGrid grid = new DataGrid();
		grid.setTotal(respListDto.getCount());
		grid.setRows(respListDto.getComplaintList());
		return grid;
	}
	
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		String ids = request.getParameter("ids");
		if(StringUtils.isBlank(ids)){
			return ;
		}
		String[] idArr = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		if(idArr!=null && idArr.length>0){
			for(int i=0; i<idArr.length; i++){
				if(StringUtils.isNotBlank(idArr[i])){
					idList.add(Integer.parseInt(idArr[i]));
				}
			}
		}
		if(idList.size()<=0){
			return ;
		}
		List<ComplaintRespDto> list = cssService.queryComplaintByIds(idList);
		Map<Integer, String> handlerStatusMap = new HashMap<Integer, String>();
		handlerStatusMap.put(0, "待处理");
		handlerStatusMap.put(1, "处理中");
		handlerStatusMap.put(2, "已处理");
		String[] titleArr = {"时间","订单号","部门","投诉类型","投诉状态","投诉内容","用户姓名","联系方式","城市","商家","商品名称","经手人"};
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow header = sheet.createRow(0);
		for(int i=0; i<titleArr.length; i++){
			header.createCell(i).setCellValue(titleArr[i]);
		}
		for(int i=0; i<list.size(); i++){
			HSSFRow row = sheet.createRow(i+1);
			ComplaintRespDto dto = list.get(i);
			row.createCell(0).setCellValue(dto.getCreateTime());
			row.createCell(1).setCellValue(dto.getOrderSnMain());
			row.createCell(2).setCellValue(dto.getDepartment());
			row.createCell(3).setCellValue(dto.getComplaintType());
			if(dto.getHandleStatus()!=null){
				String status = handlerStatusMap.get(dto.getHandleStatus());
				row.createCell(4).setCellValue(status);
			}
			row.createCell(5).setCellValue(dto.getContent());
			row.createCell(6).setCellValue(dto.getUserName());
			row.createCell(7).setCellValue(dto.getMobile());
			row.createCell(8).setCellValue(dto.getCity());
			row.createCell(9).setCellValue(dto.getWhName());
			row.createCell(10).setCellValue(dto.getGoodsName());
			row.createCell(11).setCellValue(dto.getActor());
		}
		response.setContentType("application/vnd.ms-excel");     
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-disposition", "attachment; filename=complaint.xls");
		try {
			ServletOutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//投诉页面
	@RequestMapping(value = "/complaintMsg", method = RequestMethod.GET)
	public ModelAndView complaintMsg(HttpServletRequest request,ModelMap modelMap) {
		String orderSnMain = request.getParameter("orderSnMain");
		int complaintId = Integer.parseInt(request.getParameter("complaintId"));
		ModelAndView mv = new ModelAndView("complaint/ComplaintMsg");
		BkOrderListRespDto orderDetail = bkOrderService.orderDetail(orderSnMain);
		
		if(orderDetail.getCode()==200){
			List<BkOrderListDto> orderDetailMsgs = orderDetail.getResult().getOrderList();
			mv.addObject("orderDetailMsgs", orderDetailMsgs);
		}
		ComplaintRespDto complaintMsg=new ComplaintRespDto();
		if(complaintId>0){
			List<Integer> idList=new ArrayList<Integer>();
			idList.add(complaintId);
			List<ComplaintRespDto> complaintMsgList=cssService.queryComplaintByIds(idList);
			complaintMsg=complaintMsgList.get(0);
			String cCreateTime = complaintMsg.getCreateTime();
			String rCreateTime = DateUtils.date2DateStr(DateUtils.str2Date(cCreateTime));
			complaintMsg.setCreateTime(rCreateTime);
			String productIdList = complaintMsg.getProductId();
			List<Integer> pidList=new ArrayList<Integer>();
			if(productIdList!=null){
				String[] tempList=productIdList.split(",");
				for(String t:tempList){
					if(StringUtils.isNotBlank(t)){
						pidList.add(Integer.parseInt(t));
					}
				}
			}
			mv.addObject("pidList",pidList);
		}
		mv.addObject("complaintMsg",complaintMsg);
		mv.addObject("complaintId",complaintId);
		return mv;
	}
	
	//提交投诉
	@RequestMapping(value = "/generateComplaint", method = RequestMethod.POST)
	@ResponseBody
	public CssBaseRes<String> generateComplaint(
			@RequestParam(value = "complaintId", required = false, defaultValue = "") int complaintId,
			@RequestParam(value = "orderSnMain", required = false, defaultValue = "") String orderSnMain,
			@RequestParam(value = "whId", required = false, defaultValue = "") int whId,
			@RequestParam(value = "whName", required = false, defaultValue = "") String whName,
			@RequestParam(value = "productIds", required = false, defaultValue = "") int[] productIds,
			@RequestParam(value = "content", required = false, defaultValue = "") String content,
			@RequestParam(value = "creatTime", required = false, defaultValue = "") String creatTime,
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value = "department", required = false, defaultValue = "") int department,
			@RequestParam(value = "complaintType", required = false, defaultValue = "") int complaintType,
			@RequestParam(value = "compensationType", required = false, defaultValue = "") int compensationType,
			@RequestParam(value = "money", required = false, defaultValue = "") double money,
			@RequestParam(value = "handleStatus", required = false, defaultValue = "") int handleStatus
			){
		
		String actor = this.getAuthInfo().getUserName();
		CssBaseRes<String> res=cssService.generateComplaint(actor,complaintId,orderSnMain,whId,whName,productIds,content,creatTime,userName,mobile,department,complaintType,compensationType,money,handleStatus);
		return res;
	}
}
