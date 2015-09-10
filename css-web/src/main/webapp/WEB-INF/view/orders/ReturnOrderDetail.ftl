<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">订单 - ${orderDetailMsgs[0].base.orderSnMain}<font style="color:red"></font></p>
		</strong>
	</div>
	
	<form method="post" action="/order/generateReturn" id="returnForm">
	<input type="hidden" name="orderSnMain" value="${orderDetailMsgs[0].base.orderSnMain}">
	<input type="hidden" name="returnType" value="0">
	<input type="hidden" name="payId" value="${orderDetailMsgs[0].base.payId}">
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">
				<button type="button" class="btn btn-primary" id="examine" ng-click="generateReturn();">生成退货订单</button>
				退款备注：<input name="postScript" value="">
			</div>
			  <div class="panel-body">
			  <table class="table table-hover">
	  			  <thead>
	  				<tr>
	  					<th class="text-center" width="10%">子订单号</th>
	  					<th class="text-center" width="6%">运费</th>
	  					<th class="text-center" width="5%">商品ID</th>
	  					<th class="text-center" width="26%">商品名称</th>
	  					<th class="text-center" width="8%">购买数量</th>
	  					<th class="text-center" width="8%">可退数量</th>
	  					<th class="text-center" width="8%">购买价格</th>
	  					<th class="text-center" width="8%">折后价格</th>
	  					<th class="text-center" width="7%">退款金额</th>		
	  					<th class="text-center" width="16%">退货原因</th>		
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			  	<#list orderDetailMsgs as order>
					<tr class="type_msg">
					  <td valign="middle" align="center" style="border-width:0 1px;border-color:#cbe4f5;border-style:solid;">
						<input type="radio" name="orderId" value="${order.base.orderId}">${order.base.orderId}
					  </td>
					  <td class="text-center"><input style="width: 50px;" type="text" name="shippingFee" value="${order.base.shippingFee}"></td>
					  <td colspan="8">
						<table width="100%">
						<tbody>
							<#list order.goodsList as goods>
							<tr height="26" align="center">
							<td width="12%">
								<input style="width: 50px;" type="checkbox" alt="${order.base.orderId}" value="${goods.goodsId}" name="goodsId">${goods.goodsId}
								<input type="hidden" name="specId" value="${goods.specId}">
								<input type="hidden" name="proType" value="${goods.proType}">
								<input type="hidden" name="recId" value="${goods.recId}">
							</td>
							<td width="28%">${goods.goodsName}<input type="hidden" name="goodsName" value="${goods.goodsName}"></td>
							<td width="6%">${goods.quantity}</td>
							<td width="2%"><input style="width: 50px;" type="text" name="goodsReturnNum" value="${goods.returnQuantity}"></td>
							<td width="10%">${goods.pricePurchase}</td>
							<td width="8%">${goods.priceDiscount}</td>	
							<td width="12%"><input type="text" name="goodsReturnAmount" value="${goods.returnMoney}"></td>	
							<td width="15%">
								<select name="goodsReason">
								<option value="1">商品质量问题</option>
								<option value="2">实物与图片不符</option>
								<option value="3">商品临期</option>
								<option value="4">商品过期</option>
								<option value="5">顾客原因</option>
								<option value="6">其他</option>
								</select>
							</td>	
							</tr>
							</#list>
						</tbody>
						</table>
					  </td>
					</tr>
					</#list>
				  </tbody>
			  </table>
			  </div>
		</div>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">退款方案</div>
			  <div class="panel-body">
			  <table class="table table-hover">
	  			  <thead>
	  				<tr>
	  					<th class="text-center">支付方式</th>
	  					<th class="text-center">已支付</th>
	  					<th class="text-center">退款金额</th>
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			  	<#list orderPayList as op>
					<tr>
					  <td class="text-center">${op.paymentName}</td>
					  <td class="text-center">${op.money}</td>
					  <td class="text-center">
					  	<input type="text" name="returnAmount" alt="2">
					  	<input type="hidden" name="paymentId" value="${op.paymentId}">
					  </td>
					</tr>
					</#list>
				  </tbody>
			  </table>
			  </div>
		</div>
	</div>
	</form>
</div>
<script src="<@s.url '/assets/viewjs/orderdetail.js' />"></script>
<script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
<#include "/base/basefooter.ftl">