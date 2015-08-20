package com.loukou.css.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loukou.css.util.DataGrid;
import com.loukou.order.service.api.CssOrderService;
import com.loukou.order.service.api.OrderService;
import com.loukou.order.service.req.dto.CssOrderReqDto;
import com.loukou.order.service.resp.dto.CssOrderRespDto;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CssOrderService cssOrderService;
	
	@RequestMapping("/allOrder")
	public String allOrder(){
		return "orders/OrderIndex";
	}
	
//	public String orderDetail(){
//		
//	}
	@RequestMapping("/findOrder")
	@ResponseBody
	public DataGrid queryOrder(int page, int rows, CssOrderReqDto cssOrderReqDto){
		List<CssOrderRespDto> resultList = cssOrderService.queryOrderList(page, rows, cssOrderReqDto);
		DataGrid grid = new DataGrid();
		grid.setTotal(1574641);
		grid.setRows(resultList);
		return grid;
	}
}
