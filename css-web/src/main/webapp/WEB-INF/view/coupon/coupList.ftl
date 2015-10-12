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
	<script src="<@s.url '/assets/scripts/bootstrap-table-zh-cn.js'/>" ></script>
	<link href="<@s.url '/assets/css/searchform.css' />" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/artDialog.css' />">
</head>
<body>
<div style="margin-top:5px;margin-left:5px;">
	<#if (ruleId?exists)>
		<input type="hidden" id="ruleId" value="${ruleId}">
	</#if>
	<span class="inputspan">券码<input type="text" id="commoncode"></span>
	<span class="inputspan">会员名<input type="text" id="username"></span>
	<button class="btn btn-primary sm" onclick="searchCoup()">查询</button>
</div>
<div style="height:5px;"></div>
<table id="table"></table>
<script src="<@s.url '/assets/scripts/jquery.artDialog.js' />"></script>
<script src="<@s.url '/assets/viewjs/coup/coupList.js' />"></script>
</body>
<html>