package com.loukou.css.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.annotation.AuthPassport;
import com.loukou.css.bo.BaseRes;
import com.loukou.css.bo.OrderDetailBo;
import com.loukou.css.enums.OperateVoucherTypeEnum;

@Controller
@RequestMapping("/order")
@AuthPassport
public class OrderController {	
	@RequestMapping("/allOrder")
	public String allOrder(){
		return "orders/OrderIndex";
	}
	
	@RequestMapping(value = "/orderDetail/{orderNo}", method = RequestMethod.GET)
	public String orderDetail(@PathVariable String orderNo){
		return "orders/OrderDetail";
	}
	
//	@RequestMapping(value = "/orderDetail/{orderNo}", method = RequestMethod.GET)
//	public ModelAndView orderDetail(@PathVariable String orderNo,
//			ModelMap modelMap) {
//		ModelAndView mv = new ModelAndView("orders/OrderDetail");
//		OrderDetailBo purchase = purchaseProcessor.getPurchaseDeatil(orderNo);
//		List<PurchaseDetailBo> purchaseList = purchase.getPurchaseDetailBo();
//		BaseRes<OperateListBo> operateList = stockoutProcessor
//				.getOperateInfoByOrderId(Integer.parseInt(orderNo),
//						OperateVoucherTypeEnum.Purchase_Order.getId());
//		mv.addObject("purchase", purchase);
//		mv.addObject("purchaseList", purchaseList);
//		modelMap.put("operateInfo", operateList.getResult().getOperateBo());
//		return mv;
//	}
}
