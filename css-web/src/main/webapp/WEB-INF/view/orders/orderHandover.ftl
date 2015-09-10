<#import "/spring.ftl" as s />
<!DOCTYPE html >
<html lang="en">
  <head>
  	<link href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />" rel="stylesheet">
  	<link href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />" rel="stylesheet">
  	<link href="<@s.url '/assets/css/searchform.css' />" rel="stylesheet">
  </head>
  <body ng-app="myapp">
		<div id="panel" style="padding:20px;border:1px solid #dadada;" ng-controller="OrderHandoverController">
			<form>
				<span class="inputspan">订单号<input type="text" id="orderSnMain"/></span>
				<span class="inputspan">操作人</td><td><input type="text" id="userName"/></span>
				<span class="inputspan">操作类型
                    <select id="type">  
						<option value="">操作类型</option>
						<option value="0">备注</option>
						<option value="1" selected>交接</option>
                     </select>
                </span>
				<span class="inputspan">是否交接
                    <select id="closed">
						<option value="">所有</option>
						<option value="0" selected>未交接</option>
						<option value="1">已交接</option>
                    </select>
                 </span>
                 <span class="inputspan">
                 	&nbsp;&nbsp;<input type="button" id="search_submit" value="查询"/>
                 </td>
			</form>
		</div>
		<div style="height:5px;"></div>
        <div id="table"></div>
        <div id="addOrderRemarkDialog">
    		<div style="text-align:center;margin-top:20px;">
    			订单号:&nbsp;&nbsp;<input type="text" id="addOrderSnMain">
				<textarea id="orderRemarkContent" style="width:450px;height:200px;margin-top:20px;"></textarea>
    		</div>
        </div>
        <div id="showHandoverDialog" >
        	<input type="hidden" id="handoverOrderSnMain">
        	<div style="height:230px;" id="handoverInfo"></div>
			<div style="text-align:center;">
				<textarea style="width:90%;height:70px;" id="handoverContent"></textarea>
			</div>
        </div>
  </body>
  <script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/jquery.easyui.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js' />"></script>
  <script src="<@s.url '/assets/viewjs/orderHandover.js' />"></script>
  <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
  <script src="<@s.url '/assets/scripts/CustomizeAngularjs.js' />"></script>
</html> 
        
        
        