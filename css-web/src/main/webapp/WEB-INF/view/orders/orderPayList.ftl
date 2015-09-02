<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">	
	<input type="hidden" name="orderSnMain" value="${orderSnMain}">
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			<div style="margin:10px auto;"><font color="#E10602">谨慎操作！！！</font></div>
			<ul id="pay_type_ul">
			<#list AllOrderPayList as op>
				<li style="float:left;width:150px;">
				<label><input type="radio" value="${op.paymentId}" name="paymentId">${op.paymentName}</label>
				</li>
			</#list>
			</ul>
		</div>
		<div class="panel-heading">
			<button type="button" class="btn btn-primary" ng-click="paySubOrder(${orderSnMain});">确定支付</button>
		</div>
	</div>
</div>
<script src="<@s.url '/assets/viewjs/orderdetail.js' />"></script>
<script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
<#include "/base/basefooter.ftl">