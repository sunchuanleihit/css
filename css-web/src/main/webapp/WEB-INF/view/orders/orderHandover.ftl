<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />" rel="stylesheet">
  	<link href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />" rel="stylesheet">
  </head>
  <body ng-app="myapp">
		<div id="panel" style="padding:20px;border:1px solid #dadada;" ng-controller="OrderIndexController">
			<form>
				<table>
					<tr>
					<td>订单号</td><td><input type="text" name="order_sn_main"/></td>
					<td>操作人</td><td><input type="text" name="user_name"/></td>
					<td>&nbsp;&nbsp;操作类型</td>
                    <td>
                        <select id="type" name="type" >  
							<option value="">操作类型</option>
								{html_options options=$type selected=$query.type}
                         </select> 
                    </td>
                    <td>交接部门</td>
                    <td><select name="bumen" id="bumen">
		               <option value="0">客服</option>
		               <option value="1">仓储</option>
		               <option value="2">物流</option>
		             </select></td>
					<td>&nbsp;&nbsp;是否交接</td>
                    <td>
                        <select id="closed" name="closed" >  
							<option value="">操作类型</option>
								{html_options options=$closed selected=$query.closed}
                         </select> 
                     </td>
                  </tr>
				</table>
				<p style="border-top:1px solid #dadada;text-align:center;padding-top:5px;">
					<input type="button" id="search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" class="formbtn"/>
				</p>
			</form>
		</div>
		<div style="height:5px;"></div>
        <div id="dt_handover"></div>
  </body>
  <script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/jquery.easyui.min.js' />"></script>
  <script src="<@s.url '/assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js' />"></script>
  <script src="<@s.url '/assets/viewjs/orderHandover.js' />"></script>
  <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
  <script src="<@s.url '/assets/scripts/CustomizeAngularjs.js' />"></script>
</html> 
        
        
        