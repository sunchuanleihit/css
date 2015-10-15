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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.util.DataGrid;
import com.loukou.order.service.api.CoupService;
import com.loukou.order.service.req.dto.CoupRuleAddReqDto;
import com.loukou.order.service.req.dto.CoupRuleReqDto;
import com.loukou.order.service.req.dto.CoupTypeReqDto;
import com.loukou.order.service.resp.dto.BkCouponTypeListDto;
import com.loukou.order.service.resp.dto.BkRespDto;
import com.loukou.order.service.resp.dto.CoupRuleDto;
import com.loukou.order.service.resp.dto.CoupRuleRespDto;
import com.loukou.order.service.resp.dto.CoupTypeRespDto;
import com.loukou.order.service.resp.dto.CouponListDto;
import com.serverstarted.base.service.resp.dto.RespDto;
import com.serverstarted.base.service.resp.dto.RespListDto;
import com.serverstarted.product.service.api.CategoryService;
import com.serverstarted.product.service.resp.dto.CategoryRespDto;

@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
	private CoupService coupService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/ruleList")
	public ModelAndView ruleList(){
		List<BkCouponTypeListDto> typeList = coupService.findAllCoupType();
		ModelAndView mv = new ModelAndView("/coupon/ruleList");
		if(typeList !=null && typeList.size() >0){
			List<BkCouponTypeListDto> coupTypeList = new ArrayList<BkCouponTypeListDto>();
			coupTypeList.add(null);
			for(BkCouponTypeListDto dto: typeList){
				if("客服专用".equals(dto.getTitle())){
					coupTypeList.set(0, dto);
				}else{
					coupTypeList.add(dto);
				}
			}
			mv.addObject("coupTypeList", coupTypeList);
		}
		return mv;
	}
	
	@RequestMapping("/addCoupRulePage")
	public ModelAndView addCoupRulePage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/coupon/addCoupRule");
		String typeIdStr = request.getParameter("typeId");
		List<BkCouponTypeListDto> typeList = new ArrayList<BkCouponTypeListDto>();
		if(StringUtils.isNotBlank(typeIdStr)){
			Integer typeId = Integer.parseInt(typeIdStr);
			BkCouponTypeListDto dto = coupService.queryCoupTypeById(typeId);
			
			if(dto != null){
				typeList.add(dto);
			}
			mv.addObject("defaultType", 1);
		}
		mv.addObject("coupTypeList", typeList);
		return mv;
	}
	
	@RequestMapping("/addCoupRule")
	@ResponseBody
	public Map<String, String> addCoupRule(CoupRuleAddReqDto dto){
		String result = coupService.addCoupRule(dto);
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", result);
		return map;
	}
	
	@RequestMapping("/findRule")
	@ResponseBody
	public DataGrid findRule(HttpServletRequest request,CoupRuleReqDto coupRuleReqDto){
		DataGrid grid = new DataGrid();
		Integer pageSize = request.getParameter("limit") == null ? 10: Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = (request.getParameter("offset") == null ? 0: Integer.parseInt(request.getParameter("offset")))/pageSize;//当前页码
		CoupRuleRespDto respDto = coupService.queryCoupRule(coupRuleReqDto, pageNum, pageSize);
		grid.setTotal(respDto.getCount());
		grid.setRows(respDto.getCoupRuleList());
		return grid;
	}
	
	@RequestMapping("/getRuleDetail")
	public ModelAndView getRuleDetail(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/coupon/ruleDetail");
		String ruleIdStr = request.getParameter("ruleId");
		Integer ruleId = Integer.parseInt(ruleIdStr);
		CoupRuleDto ruleDto = coupService.queryCoupRuleById(ruleId);
		if(ruleDto != null){
			if("分类券".equals(ruleDto.getScope()) && StringUtils.isNotBlank(ruleDto.getOutId()) ){
				String[] outIdArr = ruleDto.getOutId().split(",");
				String categories = "";
				for(int i=0; i<outIdArr.length; i++){
					if(StringUtils.isNotBlank(outIdArr[i])){
						Integer id = Integer.parseInt(outIdArr[i]);
						RespDto<CategoryRespDto> resp = categoryService.getCategoryById(id);
						if(resp!=null && resp.getCode() == 204){
							CategoryRespDto category = resp.getResult();
							if(category !=null){
								categories += ","+category.getName();
							}
						}
					}
				}
				if(StringUtils.isNotBlank(categories)){
					categories = categories.substring(1);
					ruleDto.setCategoryName(categories);
				}
			}
			mv.addObject("rule",ruleDto);
		}
		return mv;
	}
	
	@RequestMapping("/updateCoupUse")
	@ResponseBody
	public String updateCoupUse(HttpServletRequest request){
		String ruleIdStr = request.getParameter("ruleId");
		String isuseStr = request.getParameter("isuse");
		if(StringUtils.isBlank(ruleIdStr) || StringUtils.isBlank(isuseStr)){
			return "参数错误";
		}
		Integer ruleId = Integer.parseInt(ruleIdStr);
		Integer isuse = Integer.parseInt(isuseStr);
		coupService.updateCoupRuleIsuse(ruleId,isuse);
		return "success";
	}
	
	@RequestMapping("/addCouponNumber")
	@ResponseBody
	public Map<String, String> addCouponNumber(HttpServletRequest request){
		String ruleIdStr = request.getParameter("ruleId");
		String numberStr = request.getParameter("number");
		Map<String, String> returnMap = new HashMap<String, String>();
		if(StringUtils.isBlank(ruleIdStr) || StringUtils.isBlank(numberStr)){
			returnMap.put("result", "参数错误");
			return returnMap;
		}
		Integer ruleId = Integer.parseInt(ruleIdStr);
		Integer number = Integer.parseInt(numberStr);
		String result = coupService.addCouponNumber(ruleId, number);
		returnMap.put("result", result);
		return returnMap;
	}
	
	@RequestMapping("/exportCoup")
	public void exportCoup(HttpServletRequest request, HttpServletResponse response){
		String ruleIdStr = request.getParameter("ruleId");
		if(StringUtils.isBlank(ruleIdStr)){
			return;
		}
		Integer ruleId = Integer.parseInt(ruleIdStr);
		List<CouponListDto> coupList = coupService.queryCoupListByRuleId(ruleId);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow header = sheet.createRow(0);
		String[] titleArr = {"优惠券ID","优惠券名","用户ID","用户名","开始时间","结束时间","券码","金额","最小金额","生成时间","使用时间","是否启用","是否使用"};
		for(int i=0; i<titleArr.length; i++){
			header.createCell(i).setCellValue(titleArr[i]);
		}
		for(int i=0; i<coupList.size(); i++){
			CouponListDto dto = coupList.get(i);
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(dto.getId());
			row.createCell(1).setCellValue(dto.getCouponName());
			row.createCell(2).setCellValue(dto.getUserId());
			row.createCell(3).setCellValue(dto.getUserName());
			row.createCell(4).setCellValue(dto.getStarttime());
			row.createCell(5).setCellValue(dto.getEndtime());
			row.createCell(6).setCellValue(dto.getCommoncode());
			row.createCell(7).setCellValue(dto.getMoney());
			row.createCell(8).setCellValue(dto.getMinprice());
			row.createCell(9).setCellValue(dto.getCreatetime());
			row.createCell(10).setCellValue(dto.getUsedtime());
			row.createCell(11).setCellValue(dto.getCanuse());
			row.createCell(12).setCellValue(dto.getIschecked());
		}
		response.setContentType("application/vnd.ms-excel");     
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-disposition", "attachment; filename=order.xls");
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
	
	@RequestMapping("/typeList")
	public String typeList(){
		return "/coupon/typeList";
	}
	
	@RequestMapping("/findType")
	@ResponseBody
	public DataGrid findType(HttpServletRequest request){
		DataGrid grid = new DataGrid();
		Integer pageSize = request.getParameter("limit") == null ? 10: Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = (request.getParameter("offset") == null ? 0: Integer.parseInt(request.getParameter("offset")))/pageSize;//当前页码
		CoupTypeRespDto resp = coupService.findCoupType(pageSize, pageNum);
		grid.setRows(resp.getCoupTypeList());
		grid.setTotal(resp.getCount());
		return grid;
	}
	
	@RequestMapping("/addCoupType")
	@ResponseBody
	public Map<String, String> addCoupType(CoupTypeReqDto typeDto){
		String result = coupService.addOrUpdateCoupType(typeDto);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("info", result);
		return resultMap;
	}
	
	@RequestMapping("/deleteType")
	@ResponseBody
	public String deleteType(HttpServletRequest request){
		String idStr = request.getParameter("id");
		if(StringUtils.isBlank(idStr)){
			return "error";
		}
		Integer id = Integer.parseInt(idStr);
		coupService.deleteCoupType(id);
		return "success";
	}

	@RequestMapping("/coupList")
	public ModelAndView coupListPage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/coupon/coupList");
		String ruleId = request.getParameter("ruleId");
		mv.addObject("ruleId", ruleId);
		return mv;
	}
	
	@RequestMapping("/findCoup")
	@ResponseBody
	public DataGrid findCoup(HttpServletRequest request){
		DataGrid grid = new DataGrid();
		String commoncode = request.getParameter("commoncode");
		String username = request.getParameter("username");
		String ruleIdStr = request.getParameter("ruleId");
		Integer ruleId = null;
		if(StringUtils.isNotBlank(ruleIdStr)){
			ruleId = Integer.parseInt(ruleIdStr);
		}
		Integer pageSize = request.getParameter("limit") == null ? 10: Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = (request.getParameter("offset") == null ? 0: Integer.parseInt(request.getParameter("offset")))/pageSize;//当前页码
		BkRespDto respDto = coupService.queryCoupList(ruleId, commoncode, username, pageSize, pageNum);
		grid.setTotal(respDto.getCount());
		grid.setRows(respDto.getList());
		return grid;
	}
	
	@RequestMapping("/sendCoupon")
	@ResponseBody
	public Map<String, String> sendCoupon(HttpServletRequest request){
		Map<String, String> result = new HashMap<String, String>();
		String idStr = request.getParameter("id");
		String username = request.getParameter("username");
		if(StringUtils.isBlank(idStr) || StringUtils.isBlank(username)){
			result.put("code", "参数错误");
			return result;
		}
		Integer id = Integer.parseInt(idStr);
		String resultCode = "";
		if(coupService.isCallCenterCoup(id)){
			resultCode = coupService.sendCoupon(id, username);
		}else{
			resultCode = "非客服专用券";
		}
		result.put("code", resultCode);
		return result;
	}
	
	@RequestMapping("/stopCoup")
	@ResponseBody
	public Map<String, String> stopCoup(HttpServletRequest request){
		Map<String, String> codeMap = new HashMap<String, String>();
		String idStr = request.getParameter("id");
		String result = "";
		if(StringUtils.isBlank(idStr)){
			result = "参数错误";
		}
		Integer id = Integer.parseInt(idStr);
		if(coupService.isCallCenterCoup(id)){
			result = coupService.deleteCoup(id);
		}else{
			result = "非客服专用券";
		}
		codeMap.put("code", result);
		return codeMap;
	}
	
	@RequestMapping("/statisticRule")
	@ResponseBody
	public Map<String, Integer> statisticRule(HttpServletRequest request){
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		String idStr = request.getParameter("id");
		if(StringUtils.isBlank(idStr)){
			return null;
		}
		Integer id = Integer.parseInt(idStr);
		List<CouponListDto>  coupList = coupService.queryCoupListByRuleId(id);
		int getNum = 0;
		int useNum = 0;
		if(coupList!=null && coupList.size()>0){
			for(CouponListDto tmp: coupList){
				if("已使用".equals(tmp.getIschecked())){
					useNum ++;
				}
			}
			getNum = coupList.size();
		}
		numMap.put("getNum", getNum);
		numMap.put("useNum", useNum);
		return numMap;
	}
	
	@RequestMapping("getChildCategories")
	@ResponseBody
	public List<CategoryRespDto> getChildCategories(@RequestParam("id") int id){
		RespListDto<CategoryRespDto> respListDto = categoryService.getChildCategories(id);
		if (respListDto.getCode() == 200) {
			return respListDto.getResult().getList();
		} else {
			return new ArrayList<CategoryRespDto>();
		}
	}
	
}
