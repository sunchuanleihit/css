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
					<form id="complaintForm">
						<span class="inputspan">订单号<input type="text" id="orderSnMain" /></span>
						<span class="inputspan">订单时间<input type="text" id="startTime"/>--<input type="text" id="endTime"/></span>
						<span class="inputspan">处理状态
							<select id="handleStatus">
								<option value="">处理状态</option>
								<option value=0>待处理</option>
								<option value=1>处理中</option>
								<option value=2>已处理</option>
							</select>
						</span>
						<span class="inputspan">部门
							<select id="department" ng-model="department" ng-options="dept.name for dept in depts" ng-change="departmentChange()">
								<option value="">部门</option>
							</select>
							投诉类型
							<select id="complaintType" ng-model="complaintType" ng-options="ctype.name for ctype in department.types">
								<option value="">投诉类型</option>
							</select>
						</span>
						<span class="inputspan">城市
							<select ng-model="city" ng-options="c.name for c in cityOptions" ng-change="cityChange();">  
	                        	<option value="">城市</option>  
	                    	</select>
	                    	微仓
		                    <select ng-model="weic" ng-options="w.name for w in weicOptions">  
		                        <option value="">微仓</option>  
		                    </select>
		                </span>
						<span class="inputspan">&nbsp;&nbsp;
							<input type="button" ng-click="reloadTable();" value="查询" class="formbtn"/>&nbsp;&nbsp;
							<input type="button" value="重置" ng-click="resetForm()" class="formbtn" />
						</span>
					</form>
				</div>
				<div style="height:5px;"></div>
		        <div id="table"></div>
		    </div>  
		</div>
		<form id="exportExcelForm" action="/complaint/exportExcel" method="post">
			<input type="hidden" name="ids" id="ids">
		</form>
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
