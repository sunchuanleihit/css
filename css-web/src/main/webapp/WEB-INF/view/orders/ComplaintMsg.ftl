<#include "/base/basehead.ftl">
<div ng-controller="OrderDetailController" style="width:90%;margin:0 auto;">
	<div class="row">
		<strong><p style="font-size:20px">投诉订单 - ${orderDetailMsgs[0].base.orderSnMain}<font style="color:red"></font></p>
		</strong>
	</div>

	<form method="post" id="returnForm">
		<input type="hidden" value="${orderDetailMsgs[0].base.orderSnMain}" name="orderSnMain">
		<input type="hidden" value="${orderDetailMsgs[0].base.sellerName}" name="whName">
		<input type="hidden" value="${complaintId}" name="complaintId">
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
						<tr class="type_msg">
							<td valign="middle" align="center">
								<select name="whId" id="whId" class="form-control ng-pristine ng-valid" style="width:auto;float: left;" onchange="selectSeller()">
									<#list orderDetailMsgs as order>
									<option value="${order.base.sellerId}" >${order.base.sellerName}</option>
									</#list>
								</select>
							</td>
							<td class="text-center" colspan="3">
								<table>
								<tbody>
									<#list orderDetailMsgs as order>
									<#list order.goodsList as goods>
									<#if order_index==0>
									<tr height="26" align="center" name="s${order.base.sellerId}">
									<#else>
									<tr height="26" align="center" name="s${order.base.sellerId}" style="display:none;">
									</#if>
									<td width="12%">
									<input id="goods_name" style="width: 50px;" type="checkbox" value="${goods.goodsName}" name="goodsName">${goods.goodsId}
									</td>
									<td width="28%">${goods.goodsName}</td>
									<td width="6%">${goods.quantity}</td>
									</tr>
									</#list>
									</#list>
								</tbody>
								</table>
							</td>
						</tr>
					</tbody>
					</table>
				</div>
			</div>
		</div>

		<div style="padding:10px;" id="order_tosu_main">
			<table width="100%" style="padding:10px;">
			<tbody>
			<tr>
				<td width="10%">情况说明：</td>
				<td width="93%"><textarea name="content" style="width:520px;height:60px;"></textarea></td>
			</tr>
			<tr>
				<td height="30">投诉日期：</td>
				<td><input type="text" name="creatTime" class="form-control" placeholder="" ng-model="planArrivetime1" my97datepicker="{ dateFmt: 'yyyy-MM-dd', readOnly: true }" style="width:120px"></td>
			</tr>
			<tr>
				<td height="30">顾客姓名：</td>
				<td><input width="100" type="text" value="" name="userName"></td>
			</tr>
			<tr>
				<td>联系方式：</td>
				<td><input width="80" type="text" value="${orderDetailMsgs[0].extmMsg.phoneMob?default("")}" name="mobile"></td>
			</tr>
			<tr>
				<td>所属部门：</td>
				<td>
					<select id="department" name="department" class="form-control ng-pristine ng-valid" style="width:auto;float: left;" onchange="selectDepartment()">
						<option value="1" >微仓</option>
						<option value="2" >采购</option>
						<option value="3" >仓库</option>
						<option value="4" >系统</option>
					</select>
				</td>
			</tr>
			<tr>
				<td height="30">投诉类型：</td>
				<td>
					<select id="type1" class="form-control ng-pristine ng-valid" style="width:auto;float: left;" name="complaintType">
						<#list wscList as wsc>
						<option value="${wsc}" >${wsc}</option>
						</#list>
					</select>
					
					<select id="type2" class="form-control ng-pristine ng-valid" style="width:auto;float: left;display:none;" disabled="disabled" name="complaintType">
						<#list pcList as pc>
						<option value="${pc}" >${pc}</option>
						</#list>
					</select>
					
					<select id="type3" class="form-control ng-pristine ng-valid" style="width:auto;float: left;display:none;" disabled="disabled" name="complaintType">
						<#list whcList as whc>
						<option value="${whc}" >${whc}</option>
						</#list>
					</select>
					
					<select id="type4" class="form-control ng-pristine ng-valid" style="width:auto;float: left;display:none;" disabled="disabled" name="complaintType">
						<#list scList as sc>
						<option value="${sc}" >${sc}</option>
						</#list>
					</select>
				</td>
			</tr>
			<#if complaintId!=0>
			<tr style="display:none;">
			<#else>
			<tr>
			</#if>
				<td height="30">状态：</td>
				<td>
				<input type="radio" checked="" value="1" name="handleStatus">处理中
				<input type="radio" value="2" name="handleStatus">已处理
				</td>
			</tr>
			<tr>
				<td height="30" align="center" colspan="2"><input type="button" size="5" value="提交" ng-click="generateComplaint();"></td>
			</tr>
			</tbody>
			</table>
		</div>
	</form>
</div>
<script src="<@s.url '/assets/viewjs/orderdetail.js' />"></script>
<script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
<#include "/base/basefooter.ftl">