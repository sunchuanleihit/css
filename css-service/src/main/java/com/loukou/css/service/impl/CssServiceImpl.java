package com.loukou.css.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.dao.ComplaintDao;
import com.loukou.css.dao.ComplaintHandlerDao;
import com.loukou.css.dao.GoodsDao;
import com.loukou.css.dao.InvoiceActionDao;
import com.loukou.css.dao.InvoiceDao;
import com.loukou.css.dao.InvoiceGoodsDao;
import com.loukou.css.dao.MemberRateDao;
import com.loukou.css.dao.OrderDao;
import com.loukou.css.dao.OrderGoodsDao;
import com.loukou.css.dao.OrderPayDao;
import com.loukou.css.dao.SiteDao;
import com.loukou.css.dao.StoreDao;
import com.loukou.css.entity.Complaint;
import com.loukou.css.entity.ComplaintHandler;
import com.loukou.css.entity.Goods;
import com.loukou.css.entity.Invoice;
import com.loukou.css.entity.InvoiceAction;
import com.loukou.css.entity.InvoiceGoods;
import com.loukou.css.entity.MemberRate;
import com.loukou.css.entity.Order;
import com.loukou.css.entity.OrderGoods;
import com.loukou.css.entity.OrderPay;
import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.resp.ComplaintRespDto;
import com.loukou.css.resp.ComplaintRespListDto;
import com.loukou.css.service.CssService;
import com.loukou.css.utils.DateUtils;

@Service
public class CssServiceImpl implements CssService {
	@Autowired
    private OrderDao orderDao;
	
	@Autowired
    private MemberRateDao memberRateDao;
	
	@Autowired
    private InvoiceDao invoiceDao;
	
	@Autowired
    private StoreDao storeDao;
	
	@Autowired
    private OrderPayDao orderPayDao;
	
	@Autowired
    private OrderGoodsDao orderGoodsDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
    private InvoiceGoodsDao invoiceGoodsDao;
	
	@Autowired
    private InvoiceActionDao invoiceActionDao;
	
	@Autowired
	private SiteDao siteDao;
	
	@Autowired
	private ComplaintDao complaintDao;
	
	@Autowired
	private ComplaintHandlerDao complaintHandlerDao;
	
	//发送开票提醒
	public CssBaseRes<String> sendBillNotice(String orderSnMain,String actor){
		CssBaseRes<String> result=new CssBaseRes<String>();
		List<Order> orderList = orderDao.findByOrderSnMain(orderSnMain);//获取订单列表信息
		if (CollectionUtils.isEmpty(orderList)) {
			result.setCode("400");
			result.setMessage("订单不存在");
			return result;
		}
		
		String userName=orderList.get(0).getBuyerName();
		MemberRate memberRate=memberRateDao.getMsgByUserName(userName);
		if(memberRate==null){
			result.setCode("400");
			result.setMessage("建行员工不开票，如需开票联系江春梅");
			return result;
		}
		
		int needInvoice=orderList.get(0).getNeedInvoice();
		if(needInvoice==2){
			List<Invoice> invoiceList=invoiceDao.findByOrderSnMain(orderSnMain);
			if (CollectionUtils.isEmpty(invoiceList)) {
				needInvoice=1;
			}else{
				needInvoice=3;
			}
		}
		
		int invoiceType=orderList.get(0).getInvoiceType();
		if(invoiceType==2){
			needInvoice=3;
		}
		
		if(needInvoice==1 && orderList.get(0).getPayId()!=31 && orderList.get(0).getBuyerId()!=128702){
			List<Invoice> invoiceList=invoiceDao.findByOrderSnMain(orderSnMain);
			if (CollectionUtils.isEmpty(invoiceList)) {
				orderDao.updateNeedInvoiceByOrderSnMain(orderSnMain,2);
			}
			
			double invoiceAmount=0;
			Store storeMsg=storeDao.findOne(orderList.get(0).getSellerId());
			if(storeMsg.getTaxApply()!=1){
				invoiceAmount=0;
			}
			
			double goodsAmount=0;
			double orderAmount=0;
			double orderPayed=0;
			List<Integer> orderIds= new ArrayList<Integer>();
			for(Order o:orderList){
				goodsAmount+=o.getGoodsAmount();
				orderAmount+=o.getOrderAmount();
				orderPayed+=o.getOrderPayed();
				orderIds.add(o.getOrderId());
			}
			List<Integer> paymentIds = new ArrayList<Integer>();
			paymentIds.add(6);
			paymentIds.add(13);
			paymentIds.add(14);
			paymentIds.add(21);
			paymentIds.add(15);
			paymentIds.add(2);
			paymentIds.add(150);
			List<OrderPay> orderPay=orderPayDao.getByOrderIdsAndNotPaymentId(orderIds, paymentIds);
			double orderInvoiceAmount=0;
			for(OrderPay op:orderPay){
				orderInvoiceAmount+=op.getMoney();
			}
			
			if(orderInvoiceAmount>0){
				if(orderInvoiceAmount>=orderAmount){
					invoiceAmount=orderAmount;
				}else{
					invoiceAmount=orderInvoiceAmount+(goodsAmount-orderPayed);
				}
			}else{
				invoiceAmount=goodsAmount-orderPayed;
				if(invoiceAmount<0){
					invoiceAmount=0;
				}
			}
			
			if(invoiceAmount<=0){
				if(orderList.get(0).getSellerId()!=11){
					orderDao.updateNeedInvoiceByOrderSnMain(orderSnMain,2);
					result.setCode("400");
					result.setMessage("开票金额等0，无需开票");
					return result;
				}
			}else{
				boolean rs=creatInvoucePlans(orderSnMain,invoiceAmount);
				if(!rs){
					result.setCode("400");
					result.setMessage("生成开票方案失败");
					return result;
				}
			}
		}
		
		InvoiceAction invoiceActionData=new InvoiceAction();
		invoiceActionData.setOrderSnMain(orderSnMain);
		invoiceActionData.setActionName("发送开票提醒");
		invoiceActionData.setAdminName(actor);
		invoiceActionData.setCreateDate(new Date());
		InvoiceAction invoiceActionResult=invoiceActionDao.save(invoiceActionData);
		if(invoiceActionResult.getId()!=0){
			result.setCode("200");
			result.setMessage("发送开票提醒成功");
			return result;
		}else{
			result.setCode("400");
			result.setMessage("发送开票提醒失败");
			return result;
		}
	}
	
	//生成开票方案
	private boolean creatInvoucePlans(String orderSnMain,double invoiceAmount){
		List<Order> ordersMsg=orderDao.findByOrderSnMainAndShippingId(orderSnMain, 0);
		int freight=0;
		if (CollectionUtils.isEmpty(ordersMsg)) {
			freight=1;
		}
		List<Order> orderList = orderDao.findByOrderSnMain(orderSnMain);//获取订单列表信息
		if (CollectionUtils.isEmpty(orderList)) {
			return false;
		}
		
		Store storeMsg=storeDao.findOne(orderList.get(0).getSellerId());
		if(storeMsg.getTaxApply()!=1){
			return false;
		}
		
		List<Integer> orderIds= new ArrayList<Integer>();
		for(Order o:orderList){
			orderIds.add(o.getOrderId());
		}
		
		List<OrderGoods> orderGoodsList=orderGoodsDao.findByOrderIdsAndPriceDiscount(orderIds);
		List<Double> taxList = new ArrayList<Double>();
		for(OrderGoods og:orderGoodsList){
			Goods g=goodsDao.findByGoodsId(og.getGoodsId());
			if(!taxList.equals(g.getTax())){
				taxList.add(g.getTax());
			}
		}
		
		double haveInvoice=0;
		for(double t:taxList){
			Invoice invoiceData=new Invoice();
			invoiceData.setOrderSnMain(orderSnMain);
			invoiceData.setTax(t);
			invoiceData.setMtFlg(0);
			invoiceData.setValid(1);
			invoiceData.setFreight(freight);
			invoiceData.setAddDate(new Date());
			Invoice invoiceRes=invoiceDao.save(invoiceData);
			
			double subHaveInvoice=0;
			for(OrderGoods og:orderGoodsList){
				double gsum=og.getPriceDiscount()*og.getQuantity();
				double priceDiscount=og.getPriceDiscount();
				double subInvoice=0;
				Goods g=goodsDao.findByGoodsId(og.getGoodsId());
				if(g.getTax()==t){
					double subRemain=invoiceAmount-subHaveInvoice;
					if(subRemain<0.00000001){
						break;
					}

					if(gsum<=subRemain){
						subHaveInvoice+=gsum;
					}else{
						gsum=subRemain;
						priceDiscount=subRemain/og.getQuantity();
						subHaveInvoice+=subRemain;
					}
					
					InvoiceGoods invoiceGoodsData=new InvoiceGoods();
					invoiceGoodsData.setInvoiceId(invoiceRes.getInvoiceId());
					invoiceGoodsData.setGoodsId(g.getGoodsId());
					invoiceGoodsData.setGoodsName(g.getGoodsName());
					invoiceGoodsData.setSpecification(og.getSpecification());
					invoiceGoodsData.setNum(og.getQuantity());
					invoiceGoodsData.setGoods_amount(gsum);
					invoiceGoodsData.setPrice(priceDiscount);
					invoiceGoodsDao.save(invoiceGoodsData);
					subInvoice+=gsum;
				}
				subInvoice=Math.round(subInvoice*100);
				invoiceDao.updateOrderAmount(invoiceRes.getInvoiceId(), subInvoice);
				haveInvoice+=subInvoice;
				double remain=invoiceAmount-haveInvoice;
				if(remain<0.00000001){
					break;
				}
			}
		}
		
		return true;
	}

	@Override
	public List<Site> getAllSite() {
		List<Site> siteList = siteDao.getAllSite();
		return siteList;
	}

	@Override
	public List<Store> getStoresBySiteCode(String siteCode) {
		PageRequest pagenation = new PageRequest(0,10000 , new Sort(Sort.Direction.ASC, "storeId"));
		List<Store> storeList = storeDao.findBySellSiteAndStoreType(siteCode, "wei_wh", pagenation);
		return storeList;
	}

	@Override
	public ComplaintRespListDto queryComplaint(final String orderSnMain, final String weic, final String startTime, final String endTime, int page, int rows) {
		page = page-1;
		PageRequest pagenation = new PageRequest(page, rows , new Sort(Sort.Direction.DESC, "id"));
		Page<Complaint> complaintPage = complaintDao.findAll(new Specification<Complaint>(){
			@Override
			public Predicate toPredicate(Root<Complaint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(StringUtils.isNotBlank(orderSnMain)){
					predicates.add(cb.equal(root.get("orderSnMain"), orderSnMain));
				}
				if(StringUtils.isNotBlank(weic)){
					predicates.add(cb.equal(root.get("sellerName"), weic));
				}
				if(StringUtils.isNotBlank(startTime)){
					Integer startTimeInt = (int)(DateUtils.str2Date(startTime).getTime()/1000);
					predicates.add(cb.greaterThanOrEqualTo(root.get("dateline1").as(Integer.class), startTimeInt));
				}
				if(StringUtils.isNotBlank(endTime)){
					Integer endTimeInt = (int)(DateUtils.str2Date(endTime).getTime()/1000);
					predicates.add(cb.lessThanOrEqualTo(root.get("dateline1").as(Integer.class), endTimeInt));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pagenation);
		List<Complaint> complaintList = complaintPage.getContent();
		List<Integer> complaintIds = new ArrayList<Integer>();
		if(complaintList!=null && complaintList.size()>0){
			for(Complaint tmp: complaintList){
				complaintIds.add(tmp.getId());
			}
		}
		List<ComplaintHandler> handlerList = new ArrayList<ComplaintHandler>();
		if(complaintIds.size()>0){
			handlerList = complaintHandlerDao.findByTypeAndTidIn(0, complaintIds);
		}
		Map<Integer, ComplaintHandler> handlerMap = new HashMap<Integer, ComplaintHandler>();
		for(ComplaintHandler tmp: handlerList){
			handlerMap.put(tmp.getTid(), tmp);
		}
		List<ComplaintRespDto> respList = new ArrayList<ComplaintRespDto>();
		for(Complaint complaint: complaintList){
			ComplaintHandler handler = handlerMap.get(complaint.getId());
			ComplaintRespDto dto = this.createComplaintResp(complaint, handler);
			respList.add(dto);
		}
		ComplaintRespListDto respListDto = new ComplaintRespListDto();
		respListDto.setCount((int)complaintPage.getTotalElements());
		respListDto.setComplaintList(respList);
		return respListDto;
	}
	
	private ComplaintRespDto createComplaintResp(Complaint complaint, ComplaintHandler handler){
		ComplaintRespDto dto = new ComplaintRespDto();
		dto.setId(complaint.getId());
		if( complaint.getDateline1() != null ){
			dto.setComplaintTime(DateUtils.dateTimeToStr2(complaint.getDateline1()));
		}
		if(complaint.getDateline2() != null){
			dto.setDealTime(DateUtils.dateTimeToStr2(complaint.getDateline2()));
		}
		dto.setDepartment(complaint.getDepartment());
		dto.setGoodsName(complaint.getGoodsName());
		dto.setOrderSnMain(complaint.getOrderSnMain());
		dto.setSellerName(complaint.getSellerName());
		dto.setStatus(complaint.getStatus());
		dto.setType(complaint.getType());
		dto.setUserName(complaint.getUserName());
		dto.setMobile(complaint.getMobile());
		if(handler!=null){
			dto.setContent(handler.getContent());
			dto.setActor(handler.getActor());
		}
		return dto;
	}
}
