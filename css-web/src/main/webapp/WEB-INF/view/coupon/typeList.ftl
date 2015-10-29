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
	<div style="margin-left:10px;margin-top:10px;margin-right:10px;">
		<div style="height:5px;"></div>
		<table id="table" ></table>
		<script src="<@s.url '/assets/viewjs/coup/typeList.js' />"></script>
		<script src="<@s.url '/assets/scripts/jquery.artDialog.js' />"></script>
	</div>
</body>
<html>