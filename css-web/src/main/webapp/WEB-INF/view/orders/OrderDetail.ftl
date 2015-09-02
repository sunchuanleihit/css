<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">订单 - ${orderDetailMsgs[0].base.orderSnMain?default("")}<font style="color:red"></font></p></strong>
	</div>
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">基本信息</div>
			<div class="panel-heading">
			<form method="post" id="orderForm">
				<input type="hidden" name="orderSnMain" value="${orderDetailMsgs[0].base.orderSnMain}">
				<input type="hidden" id="needShipTime" value="${orderDetailMsgs[0].base.needShipTime}">
				<input type="text" name="needShiptime" class="form-control" placeholder="" ng-model="planArrivetime1" my97datepicker="{ dateFmt: 'yyyy-MM-dd', readOnly: true }" style="width:120px;float: left;">
				<select name="needShiptimeSlot" class="form-control ng-pristine ng-valid" style="width:130px;float: left;margin-left: 10px;">
				<#list timeList as time>
				<option value="${time}">${time}</option>
				</#list>
				</select>
				<button style="margin-top: 1px;" type="button" class="btn btn-primary" ng-click="changeOrder(${orderDetailMsgs[0].base.orderSnMain});">保存</button>
			</form>
			</div>
			<div class="panel-heading">
				<#if orderDetailMsgs[0].base.status==15>
				<button type="button" class="btn btn-primary" ng-click="returnGoods(${orderDetailMsgs[0].base.orderSnMain});">退货</button>
				</#if>
				<#if orderDetailMsgs[0].base.status==2>
				<button type="button" class="btn btn-primary" ng-click="multiplePaymentRefund(${orderDetailMsgs[0].base.orderSnMain});">多付款退款</button>
				</#if>
				<button type="button" class="btn btn-primary" ng-click="showOrderAction(${orderDetailMsgs[0].base.orderSnMain});">详细</button>
				<#if orderDetailMsgs[0].base.status!=1 && orderDetailMsgs[0].base.status!=2>
				<button type="button" class="btn btn-primary" ng-click="cancel(${orderDetailMsgs[0].base.orderSnMain});">作废[操作优惠券]</button>
				</#if>
				<#if orderDetailMsgs[0].base.status==1 || orderDetailMsgs[0].base.status==2>
				<button type="button" class="btn btn-primary" ng-click="resetCancel(${orderDetailMsgs[0].base.orderSnMain});">取消作废[操作优惠券]</button>
				</#if>
				<button type="button" class="btn btn-primary" ng-click="showCustomInfo(${orderDetailMsgs[0].base.buyerId});">客户信息</button>
				<button type="button" class="btn btn-primary" ng-click="complaintMsg(${orderDetailMsgs[0].base.orderSnMain});">投诉</button>
			</div>
			  <div class="panel-body">
			  	 <table style="width:100%">
			  			  <tbody>
							<tr>
							  <td class="text-right">用户名:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.buyerName?default("")}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">下单时间:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.addTimeStr?default("")}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">收款:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.payStatusToString?default("")}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">订单来源:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.sourceName}</td>
							</tr>
							<tr>
							  <td class="text-right">收件人:</td>
							  <td class="text-left">${orderDetailMsgs[0].extmMsg.consignee?default("")}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">手机:</td>
							  <td class="text-left">${orderDetailMsgs[0].extmMsg.phoneMob?default("")}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">地址:</td>
							  <td class="text-left">${orderDetailMsgs[0].extmMsg.address?default("")}</td>
							</tr>
							<tr>
							  <td class="text-right">商品总额:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.goodsAmount?string.number}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">邮费:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.shippingFee?string.number}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">优惠金额:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.discount?string.number}</td>
							</tr>
							<tr>
							  <td class="text-right">应付:</td>
							  <td class="text-left">${(orderDetailMsgs[0].base.goodsAmount+orderDetailMsgs[0].base.shippingFee)?string.number}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">已付:</td>
							  <td class="text-left">${orderDetailMsgs[0].base.orderPaid?string.number}</td>
							  <td class="text-center">|</td>
							  <td class="text-right">未付:</td>
							  <td class="text-left">${(orderDetailMsgs[0].base.goodsAmount+orderDetailMsgs[0].base.shippingFee-orderDetailMsgs[0].base.orderPaid)?string.number}</td>
							</tr>
						  </tbody>
				  </table>
			  </div>
		</div>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">订单信息</div>
			<div class="panel-heading">
				<button type="button" class="btn btn-primary" ng-click="cancelSubOrder();">子订单作废[不操作优惠券]</button>
				<button type="button" class="btn btn-primary" ng-click="resetCancelSubOrder();">子订单取消作废[不操作优惠券]</button>
				<button type="button" class="btn btn-primary" ng-click="paySubOrderHtml(${orderDetailMsgs[0].base.orderSnMain});">支付订单</button>
				<input type="hidden" name="subOrderId" id="subOrderId">
			</div>
			  <div class="panel-body">
			  <table class="table table-hover">
				  <thead>
					<tr>
						<th class="text-center">订单编号</th>
						<th class="text-center">商家名称</th>
						<th class="text-center">订单状态</th>
						<th class="text-center">付款方式</th>
						<th class="text-center">支付状态</th>
						<th class="text-center">商品金额</th>
						<th class="text-center">折扣金额</th>
						<th class="text-center">物流费</th>
						<th class="text-center">订单总额</th>		
						<th class="text-center">开票方</th> 
						<th class="text-center">配送方式</th>
						<th class="text-center">快递公司</th>
						<th class="text-center">快递单号</th>
						<th class="text-center">送货日期</th>
						<th class="text-center">送货时间段</th>			
					</tr>
				  </thead>
				  <tbody>
				  	<#list orderDetailMsgs as order>
					<tr style="cursor:pointer" ng-click="getordergoodslist(${order.base.orderId});">
					  <td class="text-center">${order.base.taoOrderSn?default("")}</td>
					  <td class="text-center">${order.base.sellerName}</td>
					  <td class="text-center">${order.base.statusName?default("")}</td>
					  <td class="text-center">${order.base.payTypeToString?default("")}</td>
					  <td class="text-center">${order.base.payStatusToString?default("")}</td>
					  <td class="text-center">${order.base.goodsAmount?string.number}</td>
					  <td class="text-center">${order.base.discount?string.number}</td>
					  <td class="text-center">${order.base.shippingFee?string.number}</td>
					  <td class="text-center">${(order.base.goodsAmount+order.base.shippingFee-order.base.discount)?string.number}</td>
					  <td class="text-center">${order.base.taxApply?default("")}</td>
					  <td class="text-center">${order.base.shippingType?default("")}</td>
					  <td class="text-center">${order.base.shippingCompany?default("")}</td>
					  <td class="text-center">${order.base.shippingNo?default("")}</td>
					  <td class="text-center">${order.base.needShipTime?default("")}</td>
					  <td class="text-center">${order.base.needShipTimeSlot?default("")}</td>
					</tr>
					</#list>
				  </tbody>
			  </table>
			  </div>
		</div>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading" style="cursor:pointer" onclick="opershow();">商品列表</div>
			  <div class="panel-body" id="oper">
			   <table class="table">
	  			  <thead>
	  				<tr>
	  					<th class="text-left">条形码</th>
	  					<th class="text-left">货号</th>
	  					<th class="text-left">数量</th>
	  					<th class="text-left">商品名称</th>
	  					<th class="text-left">购买价格</th>
	  					<th class="text-left">折后价格</th>
	  					<th class="text-left">折后总价</th>
	  					<th class="text-left">规格</th>
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			    <tr ng-repeat="purchase in goodsList">
					  <td>{{purchase.bn}}</td>
					  <td>{{purchase.taosku}}</td>
					  <td>{{purchase.quantity}}</td>
					  <td>{{purchase.goodsName}}</td>
					  <td>{{purchase.pricePurchase}}</td>
					  <td>{{purchase.pricePurchase}}</td>
					  <td>{{purchase.pricePurchase*purchase.quantity}}</td>
					  <td>{{purchase.specification}}</td>
					</tr>
				  </tbody>
				</table>
			  </div>
		</div>
	</div>
	<#if orderDetailMsgs[0].base.orderCode??>
	<div class="row">
		<table style="width:100%;">
			<tr>
				<td align="center">
					<#if order.base.orderState==1 || order.base.orderState==0>
					<button type="button" class="btn btn-primary" id="cancel" ng-click="cancel(${orderDetailMsgs[0].base.orderId});">作废</button>
					</#if>
					<#if order.base.orderState==2>
					<button type="button" class="btn btn-primary" id="close" ng-click="close(${orderDetailMsgs[0].base.orderId});">关闭</button>
					</#if>
					<#if order.base.orderState==9>
					<button type="button" class="btn btn-primary" id="btnDelete">删除</button>
					</#if>
				</td>
			</tr>
		</table>
	</div>
	</#if>
</div>
<script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
<script src="<@s.url '/assets/viewjs/orderdetail.js' />"></script>
<#include "/base/basefooter.ftl">
