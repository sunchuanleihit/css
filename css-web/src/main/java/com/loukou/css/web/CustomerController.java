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

import com.loukou.css.resp.CssOrderShow;
import com.loukou.css.util.DataGrid;
import com.loukou.order.service.api.BkOrderService;
import com.loukou.order.service.req.dto.CssOrderReqDto;
import com.loukou.order.service.resp.dto.BkCouponListRespDto;
import com.loukou.order.service.resp.dto.BkExtmMsgDto;
import com.loukou.order.service.resp.dto.BkOrderListBaseDto;
import com.loukou.order.service.resp.dto.BkOrderListDto;
import com.loukou.order.service.resp.dto.BkOrderListRespDto;
import com.loukou.order.service.resp.dto.BkTxkDto;
import com.loukou.order.service.resp.dto.BkTxkRecordListRespDto;
import com.loukou.order.service.resp.dto.BkVaccountListResultRespDto;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private BkOrderService bkOrderService;
	
	/**
	 * 客户信息页面
	 * @return
	 */
	@RequestMapping(value="/customerInfo")
	public ModelAndView customerInfo(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("customer/customerInfo");
		String buyerId = request.getParameter("buyerId");
		if(!StringUtils.isBlank(buyerId)){
			mv.addObject("buyerId",buyerId);
		}
		return mv;
	}
	
	@RequestMapping(value="/customerOrders")
	@ResponseBody
	public DataGrid customerOrders(HttpServletRequest request,int page, int rows){
		DataGrid grid = new DataGrid();
		String buyerIdStr = request.getParameter("buyerId");
		Integer buyerId = 0;
		if(StringUtils.isNotBlank(buyerIdStr)){
			buyerId = Integer.parseInt(buyerIdStr);
		}
		CssOrderReqDto cssOrderReqDto = new CssOrderReqDto();
		cssOrderReqDto.setBuyerId(buyerId);
		BkOrderListRespDto bkOrderRespDto = bkOrderService.queryBkOrderListByBuyerId(page, rows,buyerId);
		List<BkOrderListDto> bkOrderList = bkOrderRespDto.getResult().getOrderList();
		Integer totalCount = bkOrderRespDto.getResult().getOrderCount();
		List<CssOrderShow> cssOrderShowList = new ArrayList<CssOrderShow>();
		for(BkOrderListDto tmp: bkOrderList){
			CssOrderShow cssOrderShow = createCssOrderShow(tmp);
			cssOrderShowList.add(cssOrderShow);
		}
		grid.setRows(cssOrderShowList);
		grid.setTotal(totalCount);
		return grid;
	}
	/**
	 * 虚拟账号流水
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="virtualAccount")
	@ResponseBody
	public DataGrid virtualAccount(HttpServletRequest request,int page, int rows){
		DataGrid grid = new DataGrid();
		String buyerIdStr = request.getParameter("buyerId");
		Integer buyerId = 0;
		if(StringUtils.isNotBlank(buyerIdStr)){
			buyerId = Integer.parseInt(buyerIdStr);
		}
		BkVaccountListResultRespDto respDto = bkOrderService.queryBkVaccountResult(page, rows, buyerId);
		grid.setTotal(respDto.getTotalElement());
		grid.setRows(respDto.getBkVaccountRespDtoList());
		return grid;
	}
	/**
	 * 优惠券列表
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="couponList")
	@ResponseBody
	public DataGrid couponList(HttpServletRequest request,int page, int rows){
		DataGrid grid = new DataGrid();
		String buyerIdStr = request.getParameter("buyerId");
		Integer buyerId = 0;
		if(StringUtils.isNotBlank(buyerIdStr)){
			buyerId = Integer.parseInt(buyerIdStr);
		}
		BkCouponListRespDto respDto = bkOrderService.queryCouponListByUserId(page,rows, buyerId);
		grid.setTotal(respDto.getResult().getTotal());
		grid.setRows(respDto.getResult().getBkCouponList());
		return grid;
	}
	/**
	 * 淘心卡列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="txkList")
	@ResponseBody
	public DataGrid txkList(HttpServletRequest request,HttpServletResponse response){
		DataGrid grid = new DataGrid();
		String buyerIdStr = request.getParameter("buyerId");
		Integer buyerId = 0;
		if(StringUtils.isNotBlank(buyerIdStr)){
			buyerId = Integer.parseInt(buyerIdStr);
		}
		if(buyerId == 0){
			return grid;
		}
		List<BkTxkDto> txkList = bkOrderService.queryTxkListByUserId(buyerId);
		grid.setRows(txkList);
		grid.setTotal(txkList.size());
		return grid;
	}
	
	@RequestMapping(value="txkRecord")
	@ResponseBody
	public DataGrid txkRecord(HttpServletRequest request,int page, int rows){
		DataGrid grid = new DataGrid();
		String buyerIdStr = request.getParameter("buyerId");
		Integer buyerId = 0;
		if(StringUtils.isNotBlank(buyerIdStr)){
			buyerId = Integer.parseInt(buyerIdStr);
		}
		if(buyerId == 0){
			return grid;
		}
		
		BkTxkRecordListRespDto resp = bkOrderService.queryTxkRecordListByUserId(page, rows, buyerId);
		grid.setRows(resp.getRecordList());
		grid.setTotal(resp.getTotal());
		return grid;
	}
	private CssOrderShow createCssOrderShow(BkOrderListDto bkOrder){
		CssOrderShow cssOrderShow = new CssOrderShow();
		BkOrderListBaseDto base = bkOrder.getBase();
		BkExtmMsgDto ext = bkOrder.getExtmMsg();
		cssOrderShow.setOrderId(base.getOrderId());
		cssOrderShow.setOrderSnMain(base.getOrderSnMain());
		cssOrderShow.setTaoOrderSn(base.getTaoOrderSn());
		cssOrderShow.setCityName(base.getSellSite());
		cssOrderShow.setSource(base.getSourceName());
		cssOrderShow.setNeedShiptime(base.getNeedShipTime());
		cssOrderShow.setStatus(base.getStatusName());
		cssOrderShow.setNeedInvoice(base.getNeedInvoice());
		cssOrderShow.setInvoiceNo(base.getInvoiceNo());
		cssOrderShow.setBuyerName(base.getBuyerName());
		cssOrderShow.setPayNames(base.getPayName());
		cssOrderShow.setOrderAmount(base.getOrderAmount());
		cssOrderShow.setGoodsAmount(base.getGoodsAmount());
		cssOrderShow.setOrderPaid(base.getOrderPaid());
		cssOrderShow.setOrderNotPaid(base.getOrderNotPaid());
		cssOrderShow.setConsignee(ext.getConsignee());
		cssOrderShow.setRegionName(ext.getRegionName());
		cssOrderShow.setAddress(ext.getAddress());
		cssOrderShow.setPhoneMob(ext.getPhoneMob());
		cssOrderShow.setIsPrint(base.getPrinted());
		cssOrderShow.setPayStatus(base.getPayStatus());
		cssOrderShow.setFinishedTime(base.getFinishedTimeStr());
		cssOrderShow.setAddTime(base.getAddTimeStr());
		cssOrderShow.setPostScript(base.getPostscript());
		cssOrderShow.setPayMessage(base.getPayMessage());
		cssOrderShow.setSellerName(base.getSellerName());
		cssOrderShow.setShippingFee(base.getShippingFee());
		return cssOrderShow;
	}

}
