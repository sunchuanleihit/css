<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">订单 - ${orderDetailMsgs[0].base.orderSnMain}<font style="color:red"></font></p>
		</strong>
	</div>
	
	<form method="post" action="/order/generatePaymentRefund" id="returnForm">
	<input type="hidden" name="orderSnMain" value="${orderDetailMsgs[0].base.orderSnMain}">
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">
				<button type="button" class="btn btn-primary" ng-click="generatePaymentRefund();">生成退货订单</button>
			</div>
			<div>
				<table id="beyond_paid_return_table">
					<tbody>
					<tr>
						<th>还需退金额:</th><th>${hasPaid}</th>
					</tr>
					<tr>
						<th>退款原因:</th>
						<th id="beyond_paid_reason">
						<select name="reason">
						<option value="1">超单</option>
						<option value="2">客户取消订单</option>
						<option value="3">退运费</option>
						<option value="4">客户赔偿</option>
						<option value="5">其他</option>
						</select>
						</th>
					</tr>
					<tr>
						<th align="right">备注:</th>
						<th>
						<textarea width="300" height="30" name="postScript"></textarea>
						</th>
					</tr>
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
	  					<th class="text-center">支付方式ID</th>
	  					<th class="text-center">支付方式名称</th>
	  					<th class="text-center">支付金额</th>
	  					<th class="text-center">退款金额</th>
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			  	<#list AllOrderPayList as op>
					<tr>
						<td class="text-center">${op.paymentId}</td>
						<td class="text-center">${op.paymentName}</td>
						<td class="text-center">${op.money}</td>
						<td class="text-center">
							<input type="text" name="returnAmount" value="0">
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