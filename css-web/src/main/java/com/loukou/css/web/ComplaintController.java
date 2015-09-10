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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.req.ComplaintReqDto;
import com.loukou.css.resp.ComplaintRespDto;
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
}
