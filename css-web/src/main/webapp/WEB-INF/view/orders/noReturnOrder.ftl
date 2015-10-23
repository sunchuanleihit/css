<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
	<head>
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />">
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />">
		<link href="<@s.url '/assets/css/searchform.css' />" rel="stylesheet">
	</head>
  <body ng-app="myapp" ng-controller="OrderReturnController">
		<div id="tabs" style="border:1px solid #dadada;">
			<div title="订单列表" closable="true" style="padding:20px;">  
				<div id="panel" style="padding:20px;border:1px solid #dadada;">
					<form id="no_return_search_form">
						<span class="inputspan">订单号<input type="text" id="orderSnMain" /></span>
						<span class="inputspan">用户名<input type="text" id="buyerName"/></span>
						<span class="inputspan">单据日期<input type="text" id="orderDate"/></span>
						<span class="inputspan">商家类型<select id="storeType"><option value="">所有</option><option value="wei_wh">微仓</option></select></span>
						<span class="inputspan">&nbsp;&nbsp;
							<input type="button" id="no_return_search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;
							<input type="button" onclick="resetForm()" value="重置" class="formbtn"/>
						</span>
					</form>
				</div>
				<div style="height:5px;"></div>
		        <div id="table"></div>
		    </div>  
		</div>
  </body>
  <script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/jquery.easyui.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js' />"></script>
  <script src="<@s.url '/assets/viewjs/noreturn.js' />"></script>
  <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
  <script src="<@s.url '/assets/scripts/CustomizeAngularjs.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js' />"></script>
  <script>
	    //详情页作为tab
		function GetDetailTab(id,url,tabTxt){
	        window.parent.addTab(id, url, tabTxt, true);
		}
	</script>
</html>
