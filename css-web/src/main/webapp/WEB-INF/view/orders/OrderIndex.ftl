<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
  </head>
  <body ng-app="myapp">
		<div id="panel" style="padding:20px;border:1px solid #dadada;" ng-controller="OrderIndexController">
			<form id="search_form">
				<table>
                	<tr>
						<td>订单号</td><td><input type="text" name="order_sn_main" id="order_sn_main"/></td>
						<td>会员用户名</td><td><input type="text" id="buyerName"/></td>
						<td>单据日期</td><td><input type="text" id="start_time" name="start_time"/>--</td><td><input type="text" id="end_time" name="end_time"/></td>
						<td>订单状态</td>
                        <td>
                        	<select id="status" name="status" style="100px;">  
								<option value="">订单状态</option>
								<option value="0">初始状态</option>
								<option value="1">用户取消</option>
								<option value="2">无效</option>
								<option value="3">已审核</option>
								<option value="5">已提货</option>
								<option value="6">已拣货</option>
								<option value="8">打包</option>
								<option value="14">已发货</option>
								<option value="15">回单</option>
								<option value="16">拒收</option>
                              </select> 
                          </td>
                      </tr>
                      <tr>
                      	  <td>是否付款</td>
                      	  <td>
                      	  	<select id="payStatus">
                      	  		<option value="">所有</option>
                      	  		<option value="0">未支付</option>
                      	  		<option value="1">已支付</option>
                      	  	</select>
                      	  </td>
                      	  <td>查询类型</td>
                      	  <td>
                      	  	<select id="queryType">
                      	  		<option value="consignee">收货人姓名</option>
                      	  		<option value="phoneMob">收货人手机</option>
                      	  		<option value="phoneTel">收货人电话</option>
                      	  		<option value="city">城市</option>
                      	  	</select>
                      	  </td>
                      	  <td><input type="text" id="queryContent"></td>
                      	  <td></td>
                      	  <td>
                      	  	<input type="button" id="search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" class="formbtn"/>
                      	  	<input type="button" value="操作详情" onclick="showOrderAction()">
                      	  </td> 
                  	</tr>
				</table>
			</form>
		</div>
		<div style="height:5px;"></div>
        <div id="table"></div>
    </div>
  </body>
  <script type="text/javascript" src="../../assets/scripts/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../../assets/scripts/allOrder.js"></script>
  <script src="/assets/scripts/angularjs/angular.js"></script>
  <script src="/assets/scripts/CustomizeAngularjs.js"></script>
  <script type="text/javascript" src="/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
	<script>
	    //详情页作为tab
		function GetDetailTab(id,url,tabTxt){
	        window.parent.addTab(id, url, tabTxt, true);
		}
	</script>
</html>
