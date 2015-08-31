<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">投诉订单 - ${orderDetailMsgs[0].base.orderSnMain}<font style="color:red"></font></p>
		</strong>
	</div>
	
	<form method="post" id="returnForm">
	<div class="row">
		<div class="panel panel-default"  class="text-center">
			  <div class="panel-body">
			  <table class="table table-hover">
	  			  <thead>
	  				<tr>
	  					<th class="text-center">商家名称</th>
	  					<th class="text-center">商品ID</th>
	  					<th class="text-center">商品名称</th>
	  					<th class="text-center">数量</th>
	  				</tr>
	  			  </thead>
	  			  <tbody>
	  			  	<#list orderDetailMsgs as order>
					<tr class="type_msg">
					  <td valign="middle" align="center" style="border-width:0 1px;border-color:#cbe4f5;border-style:solid;">
						<input id="seller_name" style="width: 50px;" type="checkbox" alt="${order.base.sellerName}" value="${order.base.sellerName}" name="sellerName">${order.base.sellerName}
					  </td>
					  <td class="text-center" colspan="3">
					  	<table>
						<tbody>
							<#list order.goodsList as goods>
							<tr height="26" align="center">
							<td width="12%">
								<input id="goods_name" style="width: 50px;" type="checkbox" value="${goods.goodsName}" name="goodsName">${goods.goodsId}
							</td>
							<td width="28%">${goods.goodsName}<input type="hidden" name="goodsName" value="${goods.goodsName}"></td>
							<td width="6%">${goods.quantity}</td>
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
	
	<div style="padding:10px;" id="order_tosu_main">
		<table width="100%" style="padding:10px;">
		  <tbody><tr><td width="10%">投诉内容：</td>
		      <td width="93%"><textarea name="content1" style="width:520px;height:60px;"></textarea></td>
		  </tr>
		  <tr><td height="30">投诉日期：</td>
		      <td><input type="text" name="addTime" class="form-control" placeholder="" ng-model="planArrivetime1" my97datepicker="{ dateFmt: 'yyyy-MM-dd', readOnly: true }" style="width:120px"></td>
		  </tr>
		  <tr><td height="30">顾客姓名：</td>
		      <td><input width="100" type="text" value="" name="userName"></td>
		  </tr>
		  <tr><td>联系方式：</td>
		      <td><input width="80" type="text" value="${orderDetailMsgs[0].extmMsg.phoneMob}" name="mobile"></td>
		  </tr>
		  <tr><td height="30">投诉等级：</td>
	          <td>
				 <input type="radio" checked="" value="2" name="type">一般
				 <input type="radio" value="1" name="type">严重
				 <input type="radio" value="0" name="type">非常严重
			  </td>
		   </tr>
	      <tr><td height="30">状态：</td>
	          <td>
				 <input type="radio" checked="" value="2" name="status">已处理
				 <input type="radio" value="1" name="status">处理中
				 <input type="radio" value="0" name="status">待处理
			  </td>
		   </tr>
	     <tr><td height="30">涉及部门：</td>
	          <td>
			            </td>
	     </tr>
		 <tr><td height="30">处理意见：</td>
		      <td><textarea name="content2" style="width:520px;height:60px;"></textarea></td>
		  </tr>
		 <tr><td height="30" align="center" colspan="2"><input type="button" size="5" value="提交" ng-click="generateComplaint();"></td></tr>
		 <input type="hidden" value="${orderDetailMsgs[0].base.orderSnMain}" name="orderSnMain">
	  </tbody></table>
	</div>
	</form>
</div>
<script type="text/javascript" src="../../../assets/viewjs/orderdetail.js"></script>
<script type="text/javascript" src="../../../assets/scripts/jquery-1.7.1.min.js"></script>
<#include "/base/basefooter.ftl">