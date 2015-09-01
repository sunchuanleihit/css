<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
	<head>
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />">
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />">
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
							<td>&nbsp;&nbsp;
								<input type="button" id="no_return_search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;
								<input type="reset" value="重置" class="formbtn"/>
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
