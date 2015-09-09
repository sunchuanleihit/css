<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
	<head>
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />">
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />">
		<link href="<@s.url '/assets/css/searchform.css' />" rel="stylesheet">
	</head>
  <body  >
		<div id="tabs" style="border:1px solid #dadada;">
			<div title="投诉列表" closable="true" style="padding:20px;">
				<div id="panel" style="padding:20px;border:1px solid #dadada;" ng-controller="IndexCtrl">
					<form id="no_return_search_form">
						<span class="inputspan">订单号<input type="text" id="orderSnMain" /></span>
						<span class="inputspan">
							<select ng-model="city" ng-options="c.name for c in cityOptions" ng-change="cityChange();">  
	                        	<option value="">城市</option>  
	                    	</select>  
		                    <select ng-model="weic" ng-options="w.name for w in weicOptions">  
		                        <option value="">微仓</option>  
		                    </select>
		                </span>
						<span class="inputspan">订单时间<input type="text" id="startTime"/>--<input type="text" id="endTime"/></span>
						<span class="inputspan">&nbsp;&nbsp;
							<input type="button" ng-click="reloadTable();" value="查询" class="formbtn"/>&nbsp;&nbsp;
							<input type="reset" value="重置" class="formbtn" />
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
  <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
  <script src="<@s.url '/assets/scripts/CustomizeAngularjs.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js' />"></script>
  <script src="<@s.url '/assets/viewjs/complaint.js' />"></script>
  <script>
	    //详情页作为tab
		function GetDetailTab(id,url,tabTxt){
	        window.parent.addTab(id, url, tabTxt, true);
		}
	</script>
</html>
