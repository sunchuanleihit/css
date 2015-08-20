<#include "/base/basehead.ftl">
<div ng-controller="PurchaseDetailsController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">采购订单 - ${purchase.orderCode?default("")}<font style="color:red">,${purchase.orderStateName?default("")}</font></p>
		</strong>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">基本信息</div>
			  <div class="panel-body">
			  	 <table style="width:100%">
			  			  <tbody>
							<tr>
							  <td class="text-right" style="width:15%">供应商&nbsp;&nbsp;&nbsp;&nbsp;</td>
							  <td class="text-left" style="width:28%">${purchase.supplierName?default("")}</td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-center" style="width:15%">进货单明细 </td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-left">共<strong>${purchase.goodsNum}</strong>个商品</td>
							</tr>
							<tr>
							  <td class="text-right" style="width:15%">收货仓库&nbsp;&nbsp;&nbsp;&nbsp;</td>
							  <td class="text-left" style="width:28%">${purchase.warehouseName?default("")}</td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-center" style="width:15%"><a href="#" ng-click="info(${purchase.stockInOrderId?default("")});">${purchase.stockInOrderCode?default("")}</a> </td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-left">订<strong>${purchase.orderNumberAmount}</strong>件，付<strong>${purchase.receiveNumberAmount}</strong>件，缺<strong>${purchase.noReceiveNumberAmount}</strong>件</td>
							</tr>
							<tr>
							  <td class="text-right" style="width:15%">采购员&nbsp;&nbsp;&nbsp;&nbsp;</td>
							  <td class="text-left" style="width:28%">${purchase.userName?default("")}</td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-center" style="width:15%"> </td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-left">订<strong>${purchase.taxAmountPrice?string("0.00")}</strong>元，付<strong>${purchase.receivePrice?string("0.00")}</strong>元，缺<strong>${purchase.noReceivePrice?string("0.00")}</strong>元</td>
							</tr>
							<tr>
							  <td class="text-right" style="width:15%">计划送货日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
							  <td class="text-left" style="width:28%">${purchase.planArrivetime?default("")}</td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-center" style="width:15%"> </td>
							  <td class="text-center" style="width:5%">|</td>
							  <td class="text-left"> </td>
							</tr>
						  </tbody>
				  </table>
			  </div>
		</div>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">商品信息</div>
			  <div class="panel-body">
			  <table class="table table-hover">
			  			  <thead>
			  				<tr>
			  					<th class="text-center">商品代码</th>
			  					<th class="text-center">商品名称</th>
			  					<th class="text-center">包装</th>
			  					<th class="text-center">规格</th>
			  					<th class="text-center">税率</th>
			  					<th class="text-center">是否赠品</th>
			  					<th class="text-center">含税单价(元)</th>
			  					<th class="text-center">件数</th>
			  					<th class="text-center">含税金额(元)</th>		
			  					<th class="text-center">已收件数</th> 
			  					<th class="text-center">代收件数</th>					
			  				</tr>
			  			  </thead>
			  			  <tbody>
			  			  	<#list purchaseList as purchase>
							<tr>
							  <td class="text-center">${purchase.taoSku?default("")}</td>
							  <td class="text-center">${purchase.goodsName}</td>
							  <td class="text-center">1*${purchase.packing?default("")}</td>
							  <td class="text-center">${purchase.spec?default("-")}</td>
							  <td class="text-center">${purchase.inputTax}%</td>
							  <td class="text-center">${purchase.isGift}</td>
							  <td class="text-center">${purchase.taxPrice?string("0.0000")}</td>
							  <td class="text-center">${purchase.orderNumber}</td>
							  <td class="text-center">${purchase.taxAmount?string("0.00")}</td>
							  <td class="text-center">${purchase.receiveNumber}</td>
							  <td class="text-center"><strong><font style="color:red">${purchase.noReceiveNumber}</font></strong></td>
							</tr>
							</#list>
						  </tbody>
					</table>
			  </div>
		</div>
	</div>
	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading" style="cursor:pointer" onclick="opershow();">操作详情</div>
			  <div class="panel-body" id="oper" style="display:none">
			   <table class="table" style="width:60%;">
			  			  <thead>
			  				<tr>
			  					<th class="text-left" style="width:30%;">时间</th>
			  					<th class="text-left">操作信息</th>				
			  				</tr>
			  			  </thead>
			  			  <tbody>
							<#list operateInfo as operate>
								<tr>
									<td class="text-left" style="width:30%;">${operate.operateTime?default("")?string("yyyy-MM-dd HH:mm:ss")}</td>
									<td class="text-left">${operate.operateInfo?default("")}</td>
								</tr>
						    </#list>
						  </tbody>
					</table>
			  </div>
		</div>
	</div>
	<#if purchase.orderCode??>
	<div class="row">
			<table style="width:100%;">
				<tr>
					<td align="center">
						<#if purchase.orderState==0>
						<button type="button" class="btn btn-primary" id="examine" ng-click="examine(${purchase.orderId});">审核</button>
						</#if>
						<#if purchase.orderState==1 || purchase.orderState==0>
						<button type="button" class="btn btn-primary" id="cancel" ng-click="cancel(${purchase.orderId});">作废</button>
						</#if>
						<#if purchase.orderState==2>
						<button type="button" class="btn btn-primary" id="close" ng-click="close(${purchase.orderId});">关闭</button>
						</#if>
						<#if purchase.orderState==9>
						<button type="button" class="btn btn-primary" id="btnDelete">删除</button>
						</#if>
					</td>
				</tr>
			</table>
	</div>
	</#if>
</div>
<script type="text/javascript" src="${request.getContextPath()}/assets/viewjs/purchase/purchaselist.js"></script>
<#include "/base/basefooter.ftl">