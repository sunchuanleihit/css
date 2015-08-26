<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  	<div id="order_return_manage">
	<div title="待退款订单" closable="true" style="padding:20px;">  
		<div id="order_return_search" style="padding:20px;border:1px solid #dadada;">
				<form id="order_return_form">
					<table>
					  <tr>
						<td>订单号:</td><td><input type="text" id="orderSnMain" /></td>
						<td>单据日期:</td><td><input type="text" id="startTime" />--</td>
						<td><input type="text" id="endTime" /></td>
						<td>退款类型:</td>
						<td><select id="returnType">  
								<option value="">订单类型</option>
								<option value="0">退货</opition>
								<option value="1">拒收</option>
								<option value="2">多付款退款</option>
								<option value="3">退运费</option>
								<option value="4">客户赔偿</option>
								<option value="5">其他退款</option>
								<option value="10">比价退差</option>
							</select>
						</td>
					  </tr>
					  <tr>
				        <td>退款状态:</td>
				        <td><select id="refundStatus">
								<option value="0">未退款</option>
								<option value="1">已退款</option>
							</select>
						</td>
						<td>
							<input type="button" id="search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;&nbsp;
							<input type="reset" value="重置" class="formbtn" onclick="order_return_reset()"/>&nbsp;&nbsp;
							<input type="button" value="导出" class="formbtn" onclick="alert('待定')"/>
						</td>
					</tr>
				 </table>
				</form>
		</div>
		<div style="height:5px;"></div>
        <div id="table"></div>
    </div>
</div>

  </body>
  <script type="text/javascript" src="../../assets/scripts/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../../assets/scripts/toReturn.js"></script>
</html>