package com.loukou.css.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String queryOrder(Integer pageNum, Integer pageSize, CssOrderReqDto cssOrderReqDto){
//		List<CssOrderRespDto> resultList = cssOrderService.queryOrderList(pageNum, pageSize, cssOrderReqDto);
//		System.out.println(resultList);
		return "";
		
	}
}
