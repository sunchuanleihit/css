<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/artDialog.css' />">
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/bootstrap/bootstrap.min.css' />" />
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/bootstrap-table.css' />"  />
	<script src="<@s.url '/assets/scripts/jquery-1.10.2.js' />" ></script>
	<script src="<@s.url '/assets/scripts/bootstrap.min.js' />" ></script>
	<script src="<@s.url '/assets/scripts/bootstrap-table.js'/>"   ></script>
	<script src="<@s.url '/assets/scripts/bootstrap-table-zh-cn.js'/>"   ></script>
	<link href="<@s.url '/assets/css/searchform.css' />" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/artDialog.css' />">
	<script>
		function isCoupmanager(){
			<#if privileges?seq_contains("css.sendCoupon") >
				return true;
			<#else>
				return false;
			</#if>
		}
	</script>
</head>
<body>
<div style="margin-bottom:5px;margin-top:5px;">
	<span class="inputspan">优惠券类别
		<select id="typeId" onchange="searchRule()">
			<#if (coupTypeList?exists)>
				<#list coupTypeList as coupType>
					<option value="${coupType.id}">${coupType.title}</option>						
				</#list>
				<option value="">所有</option>
			</#if>
		</select>
	</span>
	<span class="inputspan">优惠券ID<input type="text" id="coupRuleId"></span>
	<span class="inputspan">优惠券名称<input type="text" id="coupRuleName"></span>
	<span class="inputspan">优惠券类型
		<select id="coupType">
			<option value="">请选择</option>
			<option value=0>满减券</option>
			<option value=1>现金券</option>
		</select>
	</span>
	<span class="inputspan">适用范围
		<select id="useScope">
			<option value="">请选择</option>
			<option value=0>全场券</option>
			<option value=4>分类券</option>
		</select>
	</span>
	<button class="btn btn-primary btn-xs" onclick="searchRule()">查询</button>
</div>
<table id="table"></table>
<script src="<@s.url '/assets/scripts/jquery.artDialog.js' />"></script>
<script src="<@s.url '/assets/viewjs/coup/ruleList.js' />"></script>
</body>
<html>
