package com.loukou.css.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.auth.core.annotation.AuthCheck;
import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.entity.Store;
import com.loukou.css.enums.OrderReturnOrderType;
import com.loukou.css.processor.UserProcessor;
import com.loukou.css.resp.CssOrderShow;
import com.loukou.css.service.CssService;
import com.loukou.css.service.redis.entity.SessionEntity;
import com.loukou.css.util.DataGrid;
import com.loukou.order.service.api.BkOrderService;
import com.loukou.order.service.bo.BaseRes;
import com.loukou.order.service.req.dto.BkOrderRemarkReqDto;
import com.loukou.order.service.req.dto.CssOrderReqDto;
import com.loukou.order.service.resp.dto.BkExtmMsgDto;
import com.loukou.order.service.resp.dto.BkOrderActionRespDto;
import com.loukou.order.service.resp.dto.BkOrderListBaseDto;
import com.loukou.order.service.resp.dto.BkOrderListDto;
import com.loukou.order.service.resp.dto.BkOrderListRespDto;
import com.loukou.order.service.resp.dto.BkOrderPayDto;
import com.loukou.order.service.resp.dto.BkOrderRemarkDto;
import com.loukou.order.service.resp.dto.BkOrderRemarkListRespDto;
import com.loukou.order.service.resp.dto.BkOrderReturnDto;
import com.loukou.order.service.resp.dto.BkOrderReturnListRespDto;
import com.loukou.order.service.resp.dto.GoodsListDto;

@Controller
@RequestMapping("/order")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class OrderController extends  BaseController{
	@Autowired
	private BkOrderService bkOrderService;
	
	@Autowired
	private CssService cssService;
	
	@Autowired 
    private UserProcessor userProcessor;
	
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
			mv.addObject("orderDetailMsgs", orderDetailMsgs);
			int finished=0;
			double allGoodsAmount = 0;
			double allShippingFee = 0;
			double allDiscount = 0;
			double allShoudPay = 0;
			double allPaid = 0;
			double allNotPaid = 0;
			String allUseCouponNo = "";
			boolean multiplePaymentRefund = false;
			boolean cancel = true;
			boolean resetCancel = true;
			for(BkOrderListDto od:orderDetailMsgs){
				if(od.getBase().getStatus()==15){
					finished=1;
				}
				BkOrderListBaseDto base = od.getBase();
				if(base !=null){
					allGoodsAmount += base.getGoodsAmount();
					allShippingFee += base.getShippingFee();
					allDiscount += base.getDiscount();
					if(StringUtils.isNotBlank(base.getUseCouponNo()) && !allUseCouponNo.contains(","+base.getUseCouponNo())){
						allUseCouponNo += ","+base.getUseCouponNo();
					}
					allShoudPay += base.getGoodsAmount() + base.getShippingFee();
					allPaid += base.getOrderPaid();
					if(base.getStatus() == 2 || (base.getStatus() ==1 && base.getOrderPaid() > 0)){
						multiplePaymentRefund = true;
					}
					if(base.getStatus() >= 8 || base.getStatus() == 2){//已打包或已作废则不能作废订单
						cancel = false;
					}
					if(base.getStatus() !=2 && base.getStatus() !=1){//所有订单
						resetCancel = false;
					}
				}
			}
			allNotPaid = allShoudPay - allPaid;
			mv.addObject("allGoodsAmount", allGoodsAmount);
			mv.addObject("allShippingFee", allShippingFee);
			mv.addObject("allDiscount", allDiscount);
			mv.addObject("allShoudPay", allShoudPay);
			mv.addObject("allPaid", allPaid);
			mv.addObject("allNotPaid", allNotPaid);
			mv.addObject("finished", finished);
			mv.addObject("multiplePaymentRefund", multiplePaymentRefund );
			mv.addObject("cancel", cancel);
			mv.addObject("resetCancel", resetCancel);
			if(StringUtils.isNotBlank(allUseCouponNo)){
				mv.addObject("allUseCouponNo", allUseCouponNo.substring(1));
			}
		}
		
		String checker="";
		String checkTime="";
		List<BkOrderActionRespDto> resultList = bkOrderService.getOrderActions(orderSnMain);
		for(BkOrderActionRespDto rl:resultList){
			if(rl.getAction()==3){
				checker=rl.getActor();
				checkTime=rl.getActionTime();
			}
		}
		mv.addObject("checker", checker);
		mv.addObject("checkTime", checkTime);
		
		ArrayList<String> timeList = new ArrayList<String>();
		for(int i=9; i<22; i++){
			String temp=i+":00-"+(i+1)+":00";
			if(i==9){
				temp="0"+i+":00-"+(i+1)+":00";
			}
			
			timeList.add(temp);
		}
		
		mv.addObject("timeList", timeList);
		
		int remarkCount = 0;
		//查找订单下留言
		List<BkOrderRemarkDto> remarkList = bkOrderService.queryOrderRemark(orderSnMain, 0);
		if(remarkList!=null && remarkList.size()>0){
			remarkCount +=  remarkList.size();
		}
		//查找订单下交接
		remarkList = bkOrderService.queryOrderRemark(orderSnMain, 1);
		if(remarkList!=null && remarkList.size()>0){
			remarkCount +=  remarkList.size();
		}
		mv.addObject("remarkCount",	remarkCount);
		
		//是否有退货、退款信息
		String returnStr = "";
		List<BkOrderReturnDto> bkOrderReturnList = bkOrderService.getOrderReturnByOrderSnMain(orderSnMain);
		for(BkOrderReturnDto orderReturn : bkOrderReturnList){
			String type = OrderReturnOrderType.parseType(orderReturn.getOrderType()).getType();
			returnStr += "<font color=red>*</font>"+orderReturn.getActor()+"于"+orderReturn.getAddTime()+"申请了"+
					type+",金额"+orderReturn.getReturnAmount()+"元，备注："+orderReturn.getPostscript()+"<br/>";
		}
		mv.addObject("returnStr", returnStr);
		
		//获取全部支付方式
		List<BkOrderPayDto> paymentList = bkOrderService.getPaymentList();
		mv.addObject("paymentList", paymentList);
		return mv;
	}
	
	//修改订单期望送货时间
	@RequestMapping(value = "/changeOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseRes<String> changeOrder(
			@RequestParam(value = "orderSnMain", required = false, defaultValue = "") String orderSnMain,
			@RequestParam(value = "needShiptime", required = false, defaultValue = "") String needShiptime,
			@RequestParam(value = "needShiptimeSlot", required = false, defaultValue = "") String needShiptimeSlot,
			@RequestParam(value = "invoiceHeader", required = false, defaultValue = "") String invoiceHeader,
			@RequestParam(value = "phoneMob", required = false, defaultValue = "") String phoneMob
			){
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.changeOrder(orderSnMain,needShiptime,needShiptimeSlot,invoiceHeader,phoneMob,actor);
		return res;
	}
	
	/**
	 * 所有订单的查询
	 * @param request
	 * @param page
	 * @param rows
	 * @param cssOrderReqDto
	 * @return
	 */
	@RequestMapping("/findOrder")
	@ResponseBody
	public DataGrid queryOrder(HttpServletRequest request,int page, int rows, CssOrderReqDto cssOrderReqDto){
		cssOrderReqDto.setIsDel(0);
		BkOrderListRespDto bkOrderListRespDto = bkOrderService.queryBkOrderList(page, rows, cssOrderReqDto);
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
	
	/**
	 * 未生成退款单页面
	 * @return
	 */
	@RequestMapping("/noreturn")
	public String noreturn(){
		return "orders/noReturnOrder";
	}
	
	/**
	 * 查找未生成退款单
	 * @param request
	 * @param page
	 * @param rows
	 * @param cssOrderReqDto
	 * @return
	 */
	@RequestMapping("/findNoreturn")
	@ResponseBody
	public DataGrid queryNoreturnOrder(HttpServletRequest request,int page, int rows,CssOrderReqDto cssOrderReqDto){
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		BkOrderListRespDto bkOrderListRespDto = bkOrderService.queryBkOrderNoReturnList(sort, order, page, rows, cssOrderReqDto);
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
	/**
	 * 待退款订单列表
	 * @return
	 */
	@RequestMapping("/toReturn")
	public String toReturn(){
		return "orders/toReturn";
	}
	
	/**
	 * 查找待退款单
	 * @param request
	 * @param page
	 * @param rows
	 * @param cssOrderReqDto
	 * @return
	 */
	@RequestMapping("/findToReturn")
	@ResponseBody
	public DataGrid queryToReturnOrder(HttpServletRequest request,int page, int rows,CssOrderReqDto cssOrderReqDto){
		DataGrid grid = new DataGrid();
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		BkOrderReturnListRespDto orderReturnRespDto = bkOrderService.queryBkOrderToReturn(sort, order, page, rows, cssOrderReqDto);
		grid.setTotal(orderReturnRespDto.getResult().getCount());
		grid.setRows(orderReturnRespDto.getResult().getOrderReturnList());
		return grid;
	}
	
	/**
	 * 取消反向订单
	 * @param orderIdR
	 * @return
	 */
	@RequestMapping(value="/cancelOrderReturn",  method = RequestMethod.GET)
	@ResponseBody
	public String cancelOrderReturn(Integer orderIdR){
		String result = bkOrderService.cancelOrderReturn(orderIdR);
		return result;
	}
	
	/**
	 * 将返回订单信息组装成前台显示的对象
	 * @param bkOrder
	 * @return
	 */
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
		cssOrderShow.setSellerName(base.getSellerName());
		cssOrderShow.setShippingFee(base.getShippingFee());
		cssOrderShow.setPostScript(base.getPostscript());
		return cssOrderShow;
	}
	
	//获取包裹商品列表
	@RequestMapping(value = "/getOrderGoodsList", method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsListDto> getOrderGoodsList(@RequestParam(value = "orderId", required = false, defaultValue = "") int orderId){
		List<GoodsListDto> purchaseAllList = bkOrderService.getOrderGoodsList(orderId);
		return purchaseAllList;
	}
	
	//导出待退款订单
	@RequestMapping(value="/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		String orderIdRs = request.getParameter("orderIdRs");
		if(StringUtils.isBlank(orderIdRs)){
			return;
		}
		String[] orderIdRArr = orderIdRs.split(",");
		List<Integer> orderIdRList = new ArrayList<Integer>();
		for(int i = 0 ; i<orderIdRArr.length ; i++ ){
			if(StringUtils.isNotBlank(orderIdRArr[i])){
				orderIdRList.add(Integer.parseInt(orderIdRArr[i],10));
			}
		}
		List<BkOrderReturnDto> list = bkOrderService.getOrderReturnsByIds(orderIdRList);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow header = sheet.createRow(0);
		String[] titleArr = {"原淘常州订单号","原订单号","客户ID","商家ID","商家名","退款金额","添加时间","商家类型","订单类型","订单状态","商品状态","退款状态","退账状态","备注"};
		for(int i=0; i<titleArr.length; i++){
			header.createCell(i).setCellValue(titleArr[i]);
		}
		
		for(int i=0; i<list.size(); i++){
			HSSFRow row = sheet.createRow(i+1);
			BkOrderReturnDto dto = list.get(i);
			row.createCell(0).setCellValue(dto.getOrderSnMain()==null?"":dto.getOrderSnMain());
			row.createCell(1).setCellValue(dto.getOrderId()==null?"":""+dto.getOrderId());
			row.createCell(2).setCellValue(dto.getBuyerId()==null?"":""+dto.getBuyerId());
			row.createCell(3).setCellValue(dto.getSellerId()==null?"":""+dto.getSellerId());
			row.createCell(4).setCellValue(dto.getSellerName());
			row.createCell(5).setCellValue(dto.getReturnAmount()==null?"":""+dto.getReturnAmount());
			row.createCell(6).setCellValue(dto.getAddTime()==null?"":dto.getAddTime());
			String goodsType = "普通商家";
			if(dto.getGoodsType()!=null && dto.getGoodsType()!=0){
				goodsType = "服务商家";
			}
			row.createCell(7).setCellValue(goodsType);
			row.createCell(8).setCellValue(dto.getOrderTypeStr());
			row.createCell(9).setCellValue(dto.getOrderStatusStr());
			row.createCell(10).setCellValue(dto.getGoodsStatusStr());
			String statementStatus = "已退款";
			if(dto.getStatementStatus() == 0){
				statementStatus = "未退款";
			}
			row.createCell(11).setCellValue(statementStatus);
			row.createCell(12).setCellValue(dto.getPostscript()==null?"":""+dto.getPostscript());
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
	@RequestMapping(value="/orderAction")
	public ModelAndView orderAction(HttpServletRequest request, HttpServletResponse response){
		String orderSnMain = request.getParameter("orderSnMain");
		ModelAndView mv = new ModelAndView("orders/orderAction");
		mv.addObject("orderSnMain", orderSnMain);
		return mv;
	}
	
	@RequestMapping(value="/findOrderAction")
	@ResponseBody
	public DataGrid findOrderAction(HttpServletRequest request, HttpServletResponse response){
		String orderSnMain = request.getParameter("orderSnMain");
		List<BkOrderActionRespDto> resultList = bkOrderService.getOrderActions(orderSnMain);
		DataGrid grid = new DataGrid();
		grid.setRows(resultList);
		grid.setTotal(resultList.size());
		return grid;
	}

	//获取退货信息
	@RequestMapping(value = "/returnGoods/{orderSnMain}", method = RequestMethod.GET)
	public String returnGoods(@PathVariable String orderSnMain,ModelMap modelMap){
		String returnHtml="";
		BkOrderListRespDto orderDetail = bkOrderService.orderReturnMsg(orderSnMain);
		if(orderDetail.getCode()!=200){
			return "";
		}
		
		List<BkOrderListDto> orderDetailMsgs = orderDetail.getResult().getOrderList();
		modelMap.put("orderDetailMsgs", orderDetailMsgs);

		List<BkOrderPayDto> orderPayList = bkOrderService.getOrderPayList(orderSnMain);
		modelMap.put("orderPayList", orderPayList);
		
		returnHtml="orders/ReturnOrderDetail";
		return returnHtml;
	}
	
	//生成退款单
	@RequestMapping(value = "/generateReturn", method = RequestMethod.POST)
	@ResponseBody
	public BaseRes<String> generateReturn(
			@RequestParam(value = "orderId", required = false, defaultValue = "") int orderId,
			@RequestParam(value = "postScript", required = false, defaultValue = "") String postScript,
			@RequestParam(value = "orderSnMain", required = false, defaultValue = "") String orderSnMain,
			@RequestParam(value = "returnType", required = false, defaultValue = "") int returnType,
			@RequestParam(value = "payId", required = false, defaultValue = "") int payId,
			@RequestParam(value = "shippingFee", required = false, defaultValue = "") double shippingFee,
			@RequestParam(value = "checkedProduct", required = false, defaultValue = "") int[] checkedProductList,
			@RequestParam(value = "productId", required = false, defaultValue = "") int[] productIdList,
			@RequestParam(value = "siteskuId", required = false, defaultValue = "") int[] siteskuIdList,
			@RequestParam(value = "proType", required = false, defaultValue = "") int[] proTypeList,
			@RequestParam(value = "recId", required = false, defaultValue = "") int[] recIdList,
			@RequestParam(value = "goodsReturnNum", required = false, defaultValue = "") int[] goodsReturnNumList,
			@RequestParam(value = "goodsReturnAmount", required = false, defaultValue = "") double[] goodsReturnAmountList,
			@RequestParam(value = "goodsReason", required = false, defaultValue = "") int[] goodsReasonList,
			@RequestParam(value = "goodsName", required = false, defaultValue = "") String[] goodsNameList,
			@RequestParam(value = "paymentId", required = false, defaultValue = "") int[] paymentIdList,
			@RequestParam(value = "returnAmount", required = false, defaultValue = "") double[] returnAmountList
			){
		String actor = this.getAuthInfo().getUserName();
		BaseRes<String> res=bkOrderService.generateReturn(actor,orderId, postScript, orderSnMain, returnType, payId, shippingFee, 
		checkedProductList,productIdList, siteskuIdList, proTypeList, recIdList, goodsReturnNumList, goodsReturnAmountList, goodsReasonList, goodsNameList,
		paymentIdList,returnAmountList);
		return res;
	}
	
	//作废订单
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
	@ResponseBody
	public BaseRes<String> cancelOrder(@RequestParam String orderSnMain){
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.cancelOrder(orderSnMain,actor);
		return res;
	}
	
	//取消作废订单
	@RequestMapping(value = "/resetCancelOrder", method = RequestMethod.GET)
	@ResponseBody
	public BaseRes<String> resetCancelOrder(@RequestParam String orderSnMain){
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.resetCancelOrder(orderSnMain,actor);
		return res;
	}
	
	//获取退款信息
	@RequestMapping(value = "/multiplePaymentRefund/{orderSnMain}", method = RequestMethod.GET)
	public String multiplePaymentRefund(@PathVariable String orderSnMain,ModelMap modelMap){
		String returnHtml="";
		
		BkOrderListRespDto orderDetail = bkOrderService.orderDetail(orderSnMain);
		if(orderDetail.getCode()!=200){
			return "";
		}
		
		List<BkOrderListDto> orderDetailMsgs = orderDetail.getResult().getOrderList();
		modelMap.put("orderDetailMsgs", orderDetailMsgs);
		
		double hasPaid = bkOrderService.getMultiplePaymentRefundMsg(orderSnMain);
		modelMap.put("hasPaid", hasPaid);

		List<BkOrderPayDto> AllOrderPayList = bkOrderService.getAllOrderPayList(orderSnMain);
		modelMap.put("AllOrderPayList", AllOrderPayList);
		
		returnHtml="orders/MultiplePaymentRefund";
		return returnHtml;
	}
	
	//生成退款单
	@RequestMapping(value = "/generatePaymentRefund", method = RequestMethod.POST)
	@ResponseBody
	public BaseRes<String> generatePaymentRefund(
			@RequestParam(value = "reason", required = false, defaultValue = "") int reason,
			@RequestParam(value = "orderSnMain", required = false, defaultValue = "") String orderSnMain,
			@RequestParam(value = "postScript", required = false, defaultValue = "") String postScript,
			@RequestParam(value = "paymentId", required = false, defaultValue = "") int[] paymentIdList,
			@RequestParam(value = "hasPaid", required = false, defaultValue = "") double hasPaid,
			@RequestParam(value = "returnAmount", required = false, defaultValue = "") double[] returnAmountList
			){
		
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.generatePaymentRefund(reason,actor,orderSnMain,postScript,paymentIdList,hasPaid,returnAmountList);
		return res;
	}
	
	//获取特殊退款信息
	@RequestMapping("/specialPaymentRefund")
	public String specialPaymentRefund(){
		String returnHtml="orders/SpecialPaymentRefund";
		return returnHtml;
	}
	
	//获取特殊退款信息
	@RequestMapping(value = "/specialPaymentRefundBox/{orderSnMain}", method = RequestMethod.GET)
	public String specialPaymentRefundBox(@PathVariable String orderSnMain,ModelMap modelMap){
		String returnHtml="";
		
		BkOrderListRespDto orderDetail = bkOrderService.orderDetail(orderSnMain);
		if(orderDetail.getCode()!=200){
			return "orders/SpecialPaymentRefundFail";
		}
		
		List<BkOrderListDto> orderDetailMsgs = orderDetail.getResult().getOrderList();
		
		if(orderDetailMsgs.get(0).getBase().getOrderPaid()<=0){
			return "orders/SpecialPaymentRefundFail";
		}
		
		modelMap.put("orderDetailMsgs", orderDetailMsgs);
		
		double hasPaid = bkOrderService.getMultiplePaymentRefundMsg(orderSnMain);
		modelMap.put("hasPaid", hasPaid);

		List<BkOrderPayDto> AllOrderPayList = bkOrderService.getAllOrderPayList(orderSnMain);
		modelMap.put("AllOrderPayList", AllOrderPayList);
		
		returnHtml="orders/SpecialPaymentRefundBox";
		return returnHtml;
	}
	
	//生成特殊退款单
	@RequestMapping(value = "/generateSpecialPaymentRefund", method = RequestMethod.POST)
	@ResponseBody
	public BaseRes<String> generateSpecialPaymentRefund(
			@RequestParam(value = "reason", required = false, defaultValue = "") int reason,
			@RequestParam(value = "orderSnMain", required = false, defaultValue = "") String orderSnMain,
			@RequestParam(value = "postScript", required = false, defaultValue = "") String postScript,
			@RequestParam(value = "paymentId", required = false, defaultValue = "") int[] paymentIdList,
			@RequestParam(value = "returnAmount", required = false, defaultValue = "") double[] returnAmountList
			){
		
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.generateSpecialPaymentRefund(reason,actor,orderSnMain,postScript,paymentIdList,returnAmountList);
		return res;
	}
	
	@RequestMapping("/handover")
	public String orderRemark(){
		return "orders/orderHandover";
	}
	
	@RequestMapping("/findHandover")
	@ResponseBody
	public DataGrid findHandover(HttpServletRequest request, int page, int rows, BkOrderRemarkReqDto reqDto){
		DataGrid grid = new DataGrid();
		BkOrderRemarkListRespDto respDto = bkOrderService.queryHandover(page, rows, reqDto);
		grid.setTotal(respDto.getTotal());
		grid.setRows(respDto.getBkOrderRemarkList());
		return grid;
	}
	
	@RequestMapping("/closeOrderRemark")
	@ResponseBody
	public String closeOrderRemark(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String ids = request.getParameter("ids");
		if(StringUtils.isBlank(ids)){
			return "none";
		}
		String userName = this.getAuthInfo().getUserName();
		
		if(StringUtils.isBlank(userName)){
			return "login";
		}
		String[] idArr = ids.split(",");
		for(String id: idArr){
			if(StringUtils.isNotBlank(id)){
				bkOrderService.closeOrderRemark(userName, Integer.parseInt(id));
			}
		}
		return "success";
	}
	
	@RequestMapping("/addOrderRemark")
	@ResponseBody
	public String addOrderRemark(HttpServletRequest request, HttpServletResponse response){
		String userName = this.getAuthInfo().getUserName();
		if(StringUtils.isBlank(userName)){
			return "login";
		}
		String orderSnMain = request.getParameter("orderSnMain");
		String content = request.getParameter("content");
		String typeStr = request.getParameter("type");
		if(StringUtils.isBlank(orderSnMain) || StringUtils.isBlank(content) || StringUtils.isBlank(typeStr)){
			return "error";
		}
		Integer type = Integer.parseInt(typeStr);
		bkOrderService.addOrderRemark(userName, orderSnMain, content, type);
		return "success";
	}
	
	@RequestMapping("/getOrderRemark")
	@ResponseBody
	public List<BkOrderRemarkDto> getOrderHanderover(HttpServletRequest request, HttpServletResponse response){
		String orderSnMain = request.getParameter("orderSnMain");
		String typeStr = request.getParameter("type");
		if(StringUtils.isBlank(orderSnMain) || StringUtils.isBlank(typeStr)){
			return new ArrayList<BkOrderRemarkDto>();
		}
		Integer type = Integer.parseInt(typeStr);
		List<BkOrderRemarkDto> resultList = bkOrderService.queryOrderRemark(orderSnMain,type);
		return resultList;
	}
	
	//作废订单
	@RequestMapping(value = "/cancelSubOrder", method = RequestMethod.GET)
	@ResponseBody
	public BaseRes<String> cancelSubOrder(@RequestParam int orderId){
		String actor = this.getAuthInfo().getUserName();
		BaseRes<String> res=bkOrderService.cancelSubOrder(orderId,actor);
		return res;
	}
	
	//取消作废订单
	@RequestMapping(value = "/resetCancelSubOrder", method = RequestMethod.GET)
	@ResponseBody
	public BaseRes<String> resetCancelSubOrder(@RequestParam int orderId){
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.resetCancelSubOrder(orderId,actor);
		return res;
	}
	
	//支付订单
	@RequestMapping(value = "/paySubOrder", method = RequestMethod.GET)
	@ResponseBody
	public BaseRes<String> paySubOrder(@RequestParam String orderSnMain,@RequestParam int payId){
		String actor = this.getAuthInfo().getUserName();
		
		BaseRes<String> res=bkOrderService.paySubOrder(orderSnMain,actor,payId);
		return res;
	}
	
	//支付列表
	@RequestMapping(value="/orderPayList")
	public ModelAndView orderPayList(HttpServletRequest request, HttpServletResponse response){
		String orderSnMain = request.getParameter("orderSnMain");
		ModelAndView mv = new ModelAndView("orders/orderPayList");
		List<BkOrderPayDto> AllOrderPayList = bkOrderService.getPaymentList();
		mv.addObject("AllOrderPayList", AllOrderPayList);
		mv.addObject("orderSnMain", orderSnMain);
		return mv;
	}
	
	@RequestMapping(value="/showOrderRemark")
	public ModelAndView showOrderRemark(HttpServletRequest request, HttpServletResponse response){
		String orderSnMain = request.getParameter("orderSnMain");
		ModelAndView mv = new ModelAndView("orders/showOrderRemarks");
//		List<BkOrderRemarkDto> resultList = bkOrderService.queryOrderRemark(orderSnMain,type);
//		mv.addObject("resultList", resultList);
		mv.addObject("orderSnMain", orderSnMain);
		return mv;
	}
	
	//发送开票提醒
	@RequestMapping(value = "/sendBillNotice", method = RequestMethod.GET)
	@ResponseBody
	public CssBaseRes<String> sendBillNotice(@RequestParam String orderSnMain){
		String actor = this.getAuthInfo().getUserName();
		CssBaseRes<String> res = new CssBaseRes<String>();
		if(StringUtils.isBlank(actor)){
			res.setCode("error");
			res.setMessage("请重新登陆");
		}else{
			res = cssService.sendBillNotice(orderSnMain,actor);
		}
		return res;
	}
	
	@RequestMapping("/showSeller")
	public ModelAndView showSeller(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/orders/showSeller");
		String sellerIdStr = request.getParameter("sellerId");
		if(StringUtils.isNotBlank(sellerIdStr)){
			Integer sellerId = Integer.parseInt(sellerIdStr);
			Store store = cssService.queryStore(sellerId);
			mv.addObject("store", store);
		}
		return mv;
	}
}
