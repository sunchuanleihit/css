<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">	
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div class="panel-heading">需要特殊退款的订单</div>
			<div id="pop_pass">
				订单号：<input type="text" id="orderSnMain">
				<button type="button" class="btn btn-primary" ng-click="specialPaymentRefundBox();">搜索</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="../../../assets/viewjs/orderdetail.js"></script>
<script type="text/javascript" src="../../../assets/scripts/jquery-1.7.1.min.js"></script>
<#include "/base/basefooter.ftl">