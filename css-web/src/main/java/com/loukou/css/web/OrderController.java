package com.loukou.css.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.resp.CssOrderShow;
import com.loukou.css.util.DataGrid;
import com.loukou.order.service.api.BkOrderService;
import com.loukou.order.service.req.dto.CssOrderReqDto;
import com.loukou.order.service.resp.dto.BkExtmMsgDto;
import com.loukou.order.service.resp.dto.BkOrderListBaseDto;
import com.loukou.order.service.resp.dto.BkOrderListDto;
import com.loukou.order.service.resp.dto.BkOrderListRespDto;
import com.loukou.order.service.resp.dto.OrderListBaseDto;
import com.loukou.order.service.resp.dto.OrderListDto;
import com.loukou.order.service.resp.dto.CssOrderRespDto;
import com.loukou.order.service.resp.dto.OrderListRespDto;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private BkOrderService bkOrderService;
	
	@RequestMapping("/allOrder")
	public String allOrder(){
		return "orders/OrderIndex";
	}
	@RequestMapping(value = "/orderDetail/{orderSnMain}", method = RequestMethod.GET)
	public ModelAndView orderDetail(@PathVariable String orderSnMain,
			ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("orders/OrderDetail");
		BkOrderListRespDto orderDetail = bkOrderService.orderDetail(orderSnMain);
		
		if(orderDetail.getCode()==200){			
			List<BkOrderListDto> orderDetailMsgs = orderDetail.getResult().getOrderList();
			modelMap.put("operateInfo", orderDetailMsgs);
		}
		return mv;
	}
	
	@RequestMapping("/findOrder")
	@ResponseBody
	public DataGrid queryOrder(HttpServletRequest request,int page, int rows, CssOrderReqDto cssOrderReqDto){
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		BkOrderListRespDto bkOrderListRespDto = bkOrderService.queryBkOrderList(sort,order,page, rows, cssOrderReqDto);
		DataGrid grid = new DataGrid();
		grid.setTotal(bkOrderListRespDto.getResult().getOrderCount());
		List<BkOrderListDto> bkOrderListDto = bkOrderListRespDto.getResult().getOrderList();
		List<CssOrderShow> cssOrderShowList = new ArrayList<CssOrderShow>();
		for(BkOrderListDto dto: bkOrderListDto){
			CssOrderShow cssOrderShow = createCssOrderShow(dto);
			cssOrderShowList.add(cssOrderShow);
		}
		grid.setRows(cssOrderShowList);
		return grid;
	}
	private CssOrderShow createCssOrderShow(BkOrderListDto bkOrder){
		CssOrderShow cssOrderShow = new CssOrderShow();
		BkOrderListBaseDto base = bkOrder.getBase();
		BkExtmMsgDto ext = bkOrder.getExtmMsg();
		cssOrderShow.setOrderId(base.getOrderId());
		cssOrderShow.setOrderSnMain(base.getOrderSnMain());
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
		return cssOrderShow;
	}
}
