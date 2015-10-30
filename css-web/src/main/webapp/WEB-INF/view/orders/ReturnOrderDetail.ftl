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
	  					<th>子订单号</th>
	  					<th>运费</th>
	  					<th>商品ID</th>
	  					<th>商品名称</th>
	  					<th>购买数量</th>
	  					<th>可退数量</th>
	  					<th>购买价格</th>
	  					<th>折后价格</th>
	  					<th>退款金额</th>		
	  					<th>退货原因</th>		
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			  	<#list orderDetailMsgs as order>
					<tr class="type_msg">
					  <td valign="middle" align="center" style="border-width:0 1px;border-color:#cbe4f5;border-style:solid;" rowspan="${order.goodsList?size}" >
						<input type="radio" orderId="${order.base.orderId}" name="orderId" value="${order.base.orderId}">${order.base.orderId}
					  </td>
					  <td class="text-center" rowspan="${order.goodsList?size}"><input style="width: 50px;" type="text" name="shippingFee" value="${order.base.shippingFee}"></td>
					  <#list order.goodsList as goods>
						<#if (goods_index>0)>
							<tr>
						</#if>
						<td>
							<input style="width: 50px;" orderId="${order.base.orderId}" type="checkbox" value="${goods.productId}" name="checkedProduct">${goods.productId}
							<input type="hidden" name="productId" value="${goods.productId}">
							<input type="hidden" name="siteskuId" value="${goods.siteskuId}">
							<input type="hidden" name="specId" value="${goods.specId}">
							<input type="hidden" name="proType" value="${goods.proType}">
							<input type="hidden" name="recId" value="${goods.recId}">
						</td>
						<td>${goods.goodsName}<#if goods.packageName?exists && goods.packageName!="" >【组合购：${goods.packageName}】</#if>
							<input type="hidden" name="goodsName" value="${goods.goodsName}">
						</td>
						<td>${goods.quantity}</td>
						<td><input style="width: 50px;" type="text" name="goodsReturnNum" value="${goods.returnQuantity}"></td>
						<td>${goods.pricePurchase}</td>
						<td>${goods.priceDiscount}</td>	
						<td><input type="text" name="goodsReturnAmount" value="${goods.returnMoney}"></td>	
						<td>
							<select name="goodsReason">
							<option value="1">商品质量问题</option>
							<option value="2">实物与图片不符</option>
							<option value="3">商品临期</option>
							<option value="4">商品过期</option>
							<option value="5">顾客原因</option>
							<option value="6">其他</option>
							</select>
						</td>
						<#if (goods_index>0)>
							</tr>
						</#if>
					  </#list>
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
					  <td class="text-center">${op.paymentName?default("")}</td>
					  <td class="text-center">${op.money?default(0)}</td>
					  <td class="text-center">
					  	<input type="text" name="returnAmount" alt="2" value="0">
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
<script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
<script src="<@s.url '/assets/viewjs/orderdetail.js' />"></script>
<script>
	$(function(){
		$("input[name='orderId']").bind({
			'click':function(){
				$("input[name='checkedGoods']").removeAttr("checked");
				var orderId = $(this).attr("orderId");
				$("input[orderid='"+orderId+"'][name='checkedGoods']").prop("checked", true);
			}
		});
		$("input[name='checkedGoods']").bind({
			'click':function(){
				var orderId = $(this).attr("orderId");
				var selectOrderId = $("input[name='orderId']:checked").attr("orderId");
				if(!selectOrderId || selectOrderId!=orderId){
					$(this).prop("checked", false);				
				}
			}
		});
	});
</script>
<#include "/base/basefooter.ftl">