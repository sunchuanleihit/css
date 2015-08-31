<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
  </head>
  <body ng-app="myapp" ng-controller="OrderReturnController">
		<div id="tabs" style="border:1px solid #dadada;">
			<div title="订单列表" closable="true" style="padding:20px;">  
				<div id="panel" style="padding:20px;border:1px solid #dadada;">
					<form id="no_return_search_form">
						<table>
		                   <tr>
							<td>订单号</td><td><input type="text" id="orderSnMain" /></td>
							<td>用户名</td><td><input type="text" id="buyerName"/></td>
							<td>单据日期</td><td><input type="text" id="startTime"/>--</td><td><input type="text" id="endTime"/></td>
						  </tr>
		                  <tr>
						</table>
						<p style="border-top:1px solid #dadada;text-align:center;padding-top:5px;">
							<input type="button" id="no_return_search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="reset" value="重置" class="formbtn"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</p>
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
  <script type="text/javascript" src="../../assets/scripts/noreturn.js"></script>
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