package com.loukou.css.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
	@RequestMapping("allOrder")
	public String allOrder(){
		return "allOrder";
	}
	

}
