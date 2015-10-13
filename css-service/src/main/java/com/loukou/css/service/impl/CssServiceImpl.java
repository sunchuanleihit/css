package com.loukou.css.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.loukou.css.dao.InvoiceActionDao;
import com.loukou.css.dao.InvoiceDao;
import com.loukou.css.dao.InvoiceGoodsDao;
import com.loukou.css.dao.LkComplaintDao;
import com.loukou.css.dao.MemberRateDao;
import com.loukou.css.dao.OrderActionDao;
import com.loukou.css.dao.OrderDao;
import com.loukou.css.dao.OrderGoodsDao;
import com.loukou.css.dao.OrderPayDao;
import com.loukou.css.dao.OrderReturnDao;
import com.loukou.css.dao.SiteDao;
import com.loukou.css.dao.StoreDao;
import com.loukou.css.dao.TczAdminDao;
import com.loukou.css.entity.Invoice;
import com.loukou.css.entity.InvoiceAction;
import com.loukou.css.entity.InvoiceGoods;
import com.loukou.css.entity.LkComplaint;
import com.loukou.css.entity.MemberRate;
import com.loukou.css.entity.Order;
import com.loukou.css.entity.OrderAction;
import com.loukou.css.entity.OrderGoods;
import com.loukou.css.entity.OrderPay;
import com.loukou.css.entity.OrderReturn;
import com.loukou.css.entity.Site;
import com.loukou.css.entity.Store;
import com.loukou.css.entity.TczAdmin;
import com.loukou.css.enums.ComplaintTypeEnum;
import com.loukou.css.enums.DeptEnum;
import com.loukou.css.req.ComplaintReqDto;
import com.loukou.css.resp.AchievementRespDto;
import com.loukou.css.resp.ComplaintRespDto;
import com.loukou.css.resp.ComplaintRespListDto;
import com.loukou.css.service.CssService;
import com.loukou.css.utils.DateUtils;
import com.serverstarted.base.service.resp.dto.RespDto;
import com.serverstarted.product.service.api.ProductService;
import com.serverstarted.product.service.api.SpuService;
import com.serverstarted.product.service.resp.dto.ProductRespDto;
import com.serverstarted.product.service.resp.dto.SpuRespDto;

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
    private InvoiceGoodsDao invoiceGoodsDao;
	
	@Autowired
    private InvoiceActionDao invoiceActionDao;
	
	@Autowired
	private SiteDao siteDao;
	
	@Autowired
	private LkComplaintDao lkComplaintDao;
	
	@Autowired
	private OrderReturnDao orderReturnDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SpuService spuService;
	
	@Autowired
	private TczAdminDao tczAdminDao;
	
	@Autowired
	private OrderActionDao orderActionDao;
	
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
		if(memberRate!=null){
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
			if (!CollectionUtils.isEmpty(invoiceList)) {
				invoiceDao.updateOrderValid(orderSnMain,0);//作废之前的发票单
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
		Map<Integer, SpuRespDto> spuMap = new HashMap<Integer, SpuRespDto>();
		
		for(OrderGoods og:orderGoodsList){
			ProductRespDto productDto = productService.getProductById(og.getProductId());
			int spuId = productDto.getSpuId();
			if(spuId != 0){
				RespDto<SpuRespDto> spuDto = spuService.findSpuById(spuId);
				if(spuDto.getCode() == 200){
					if(!taxList.contains(spuDto.getResult().getTax())){
						taxList.add(spuDto.getResult().getTax());
					}
					spuMap.put(og.getRecId(), spuDto.getResult());
				}else{
					return false;
				}
			}else{
				return false;
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
				SpuRespDto spu = spuMap.get(og.getRecId());
				if(spu !=null && spu.getTax()==t){
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
					invoiceGoodsData.setProductId(og.getProductId());
					invoiceGoodsData.setGoodsName(spu.getName());
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
	public ComplaintRespListDto queryComplaint(final ComplaintReqDto complaintReqDto,  int page, int rows) {
		Map<String, Site> siteMap = getSiteMap();
		page = page-1;
		PageRequest pagenation = new PageRequest(page, rows , new Sort(Sort.Direction.DESC, "id"));
		Page<LkComplaint> complaintPage = lkComplaintDao.findAll(new Specification<LkComplaint>(){
			@Override
			public Predicate toPredicate(Root<LkComplaint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(StringUtils.isNotBlank(complaintReqDto.getOrderSnMain())){
					predicates.add(cb.equal(root.get("orderSnMain"), complaintReqDto.getOrderSnMain()));
				}
				if(StringUtils.isNotBlank(complaintReqDto.getStartTime())){
					//Integer startTimeInt = (int)(DateUtils.str2Date(complaintReqDto.getStartTime()).getTime()/1000);
					predicates.add(cb.greaterThanOrEqualTo(root.get("creatTime").as(String.class), complaintReqDto.getStartTime()));
				}
				if(StringUtils.isNotBlank(complaintReqDto.getEndTime())){
					//Integer endTimeInt = (int)(DateUtils.str2Date(complaintReqDto.getEndTime()).getTime()/1000);
					predicates.add(cb.lessThanOrEqualTo(root.get("creatTime").as(String.class), complaintReqDto.getEndTime()+" 00:00:00"));
				}
				if(complaintReqDto.getHandleStatus()!=null){
					predicates.add(cb.equal(root.get("handleStatus"), complaintReqDto.getHandleStatus()));
				}
				if(complaintReqDto.getDepartment()!=null){
					predicates.add(cb.equal(root.get("department"), complaintReqDto.getDepartment()));
				}
				if(complaintReqDto.getComplaintType()!=null){
					predicates.add(cb.equal(root.get("complaintType"), complaintReqDto.getComplaintType()));
				}
				if(StringUtils.isNotBlank(complaintReqDto.getCityCode())){
					predicates.add(cb.equal(root.get("cityCode"), complaintReqDto.getCityCode()));
				}
				if(complaintReqDto.getWeic()!=null){
					predicates.add(cb.equal(root.get("whId"), complaintReqDto.getWeic()));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pagenation);
		List<LkComplaint> complaintList = complaintPage.getContent();
		
		List<ComplaintRespDto> complaintRespList = new ArrayList<ComplaintRespDto>();
		if(complaintList!=null && complaintList.size()>0){
			for(LkComplaint complaint: complaintList){
				ComplaintRespDto dto = createComplaintRespDto(siteMap, complaint);
				complaintRespList.add(dto);
			}
		}
		ComplaintRespListDto respListDto = new ComplaintRespListDto();
		respListDto.setCount((int)complaintPage.getTotalElements());
		respListDto.setComplaintList(complaintRespList);
		return respListDto;
	}
	
	/**
	 * 获取站点的Map
	 * @return
	 */
	private Map<String, Site> getSiteMap(){
		List<Site> siteList = siteDao.getAllSite();
		Map<String, Site> siteMap = new HashMap<String, Site>();
		if(siteList!=null && siteList.size()>0){
			for(Site site: siteList){
				siteMap.put(site.getShortCode(), site);
			}
		}
		return siteMap;
	}
	
	/**
	 * 根据原始投诉生成处理后的显示数据
	 * @param siteMap
	 * @param complaint
	 * @return
	 */
	private ComplaintRespDto createComplaintRespDto(Map<String, Site> siteMap, LkComplaint complaint){
		ComplaintRespDto dto = new ComplaintRespDto();
		dto.setId(complaint.getId());
		if(complaint.getCreatTime()!=null){
			dto.setCreateTime(DateUtils.date2DateStr(complaint.getCreatTime()));
		}
		dto.setOrderSnMain(complaint.getOrderSnMain());
		if(complaint.getDepartment()!=null){
			dto.setDepartment(DeptEnum.parseName(complaint.getDepartment()).getName());
		}
		if(complaint.getComplaintType()!=0){
			dto.setComplaintType(ComplaintTypeEnum.parseName(complaint.getComplaintType()).getName());
		}
		dto.setHandleStatus(complaint.getHandleStatus());
		dto.setContent(complaint.getContent());
		dto.setUserName(complaint.getUserName());
		dto.setMobile(complaint.getMobile());
		Site site = siteMap.get(complaint.getCityCode());
		if(site!=null){
			dto.setCity(site.getSiteName());
		}
		dto.setWhName(complaint.getWhName());
		dto.setGoodsName(complaint.getGoodsName());
		dto.setActor(complaint.getActor());
		dto.setDepartmentId(complaint.getDepartment());
		dto.setComplaintTypeId(complaint.getComplaintType());
		dto.setWhId(complaint.getWhId());
		dto.setProductId(complaint.getProductId());
		return dto;
	}
	
	
	//提交/修改投诉
	public CssBaseRes<String> generateComplaint(String actor,int complaintId,String orderSnMain,int whId,String whName,
		int[] productIdList,String content,String creatTime,String userName,String mobile,int department,int complaintType,int handleStatus){
		CssBaseRes<String> result=new CssBaseRes<String>();
		
		List<Order> orderList = orderDao.findByOrderSnMain(orderSnMain);//获取订单列表信息
		if (CollectionUtils.isEmpty(orderList)) {
			result.setCode("400");
			result.setMessage("订单为空");
			return result;
		}
		
		if(productIdList.length<=0){
			result.setCode("400");
			result.setMessage("所选商品为空");
			return result;
		}
		
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i=0; i < productIdList.length; i++){
			pidList.add(productIdList[i]);
		}
		List<ProductRespDto> produDtoList = productService.getProductsByIds(pidList);
		List<Integer> spuIdList = new ArrayList<Integer>();
		String productIds = "";
		for(ProductRespDto dto: produDtoList){
			spuIdList.add(dto.getSpuId());
			productIds += ","+dto.getId();
		}
		if(StringUtils.isNotBlank(productIds)){
			productIds = productIds.substring(1);
		}
		List<SpuRespDto> spuList = spuService.findByIdIn(spuIdList);
		String goodsName = "";
		for(SpuRespDto spu: spuList){
			goodsName += "," + spu.getName();
		}
		//新增投诉
		if(complaintId==0){
			LkComplaint complaintData=new LkComplaint();
			complaintData.setUserName(userName);
			complaintData.setMobile(mobile);
			complaintData.setOrderSnMain(orderSnMain);
			complaintData.setWhId(whId);
			complaintData.setWhName(whName);
			complaintData.setProductId(productIds);
			complaintData.setGoodsName(goodsName);
			complaintData.setContent(content);
			complaintData.setDepartment(department);
			complaintData.setComplaintType(complaintType);
			complaintData.setHandleStatus(handleStatus);
			complaintData.setStatus(0);
			complaintData.setCreatTime(DateUtils.str2Date(creatTime));
			complaintData.setCityCode(orderList.get(0).getSellSite());
			complaintData.setActor(actor);
			LkComplaint complaintResult=lkComplaintDao.save(complaintData);
			if(complaintResult==null){
				result.setCode("400");
				result.setMessage("投诉失败");
				return result;
			}
		}else{//修改投诉
			Date finishTime=new Date();
			int lcResult=lkComplaintDao.updateComplaintById(complaintId, userName, mobile, whId, whName, goodsName, content, department, 
					complaintType, handleStatus, actor,finishTime,productIds);
			if(lcResult<=0){
				result.setCode("400");
				result.setMessage("修改投诉失败");
				return result;
			}
		}
		
		result.setCode("200");
		result.setMessage("提交成功");
		return result;
	}
	

	@Override
	public List<ComplaintRespDto> queryComplaintByIds(List<Integer> idList) {
		Map<String, Site> siteMap = this.getSiteMap();
		List<LkComplaint> complaintList = lkComplaintDao.getByIds(idList);
		List<ComplaintRespDto> respList = new ArrayList<ComplaintRespDto>();
		if(complaintList!=null && complaintList.size()>0){
			for(LkComplaint tmp: complaintList){
				ComplaintRespDto dto = this.createComplaintRespDto(siteMap, tmp);
				respList.add(dto);
			}
		}
		return respList;
	}

	@Override
	public Store queryStore(Integer sellerId) {
		Store store = storeDao.findOne(sellerId);
		return store;
	}

	@Override
	public List<AchievementRespDto> getAchievement(String startDate, String endDate) {
		Map<String, AchievementRespDto> resultMap = new HashMap<String, AchievementRespDto>();
		List<AchievementRespDto> achievementList = new ArrayList<AchievementRespDto>();
		List<TczAdmin> adminList = this.getCallCenterMember();
		for(TczAdmin admin: adminList){
			AchievementRespDto respDto = new AchievementRespDto();
			achievementList.add(respDto);
			respDto.setName(admin.getRealname());
			resultMap.put(admin.getRealname(), respDto);
		}
		if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)){
			return achievementList;
		}
		List<OrderReturn> orderReturnList = orderReturnDao.findByAddTimeBetweenAndOrderStatus(startDate, endDate, 0);
		for(OrderReturn or : orderReturnList){
			AchievementRespDto respDto = resultMap.get(or.getActor());
			if(respDto!=null){
				if(or.getOrderType() == 0){//统计退货数
					respDto.setReturnGoodsNum(respDto.getReturnGoodsNum()+1);
				}
				if((or.getOrderType() == 2 || or.getOrderType()==7) && or.getReason()>0){//统计退款单数
					respDto.setReturnMoneyNum(respDto.getReturnMoneyNum()+1);
				}
			}
		}
		orderReturnList = null;
		//统计作废单数
		Date start = DateUtils.getStartofDate(DateUtils.str2Date(startDate));
		Date end = DateUtils.getEndofDate(DateUtils.str2Date(endDate));
		List<Integer> actions = new ArrayList<Integer>();
		actions.add(2);
		actions.add(31);
		actions.add(32);
		actions.add(33);
		List<OrderAction> orderActionList = orderActionDao.findByActionTimeBetweenAndActionIn(start, end, actions);//作废订单或改单
		Set<String> orderSnMainSet = new HashSet<String>();
		for(OrderAction oa : orderActionList){
			if(orderSnMainSet.add(oa.getOrderSnMain())){
				AchievementRespDto respDto = resultMap.get(oa.getActor());
				if(respDto != null){
					if(oa.getAction()==2){//作废订单
						respDto.setCancelOrderNum(respDto.getCancelOrderNum()+1);
					}else if(oa.getAction() == 31 || oa.getAction() == 32 || oa.getAction() == 33){//改单
						respDto.setChangeOrderNum(respDto.getChangeOrderNum()+1);
					}
				}
			}
		}
		//统计投诉数
		List<LkComplaint> complaintList = lkComplaintDao.findByCreatTimeBetween(start, end);
		for(LkComplaint complaint: complaintList){
			AchievementRespDto respDto = resultMap.get(complaint.getActor());
			if(respDto != null){
				respDto.setComplaintNum(respDto.getComplaintNum()+1);
			}
		}
		return achievementList;
	}
	
	private List<TczAdmin> getCallCenterMember(){
		Integer callCenterManager = 220;// 客服老大 雍燕
		List<TczAdmin> tczAdminList = tczAdminDao.getAllCallCenterAdmin(callCenterManager);
		return tczAdminList;
	} 
	
}
