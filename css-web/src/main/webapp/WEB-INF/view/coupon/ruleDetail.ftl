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
</head>
<body>
<#if (rule?exists)>
	<table class="table">
		<tr><td style="width:150px;">规则ID：</td><td id="ruleId">${rule.id}</td></tr>
		<tr><td>规则类别：</td><td>${rule.type}</td></tr>
		<tr><td>优惠券名称：</td><td>${rule.name}</td>	</tr>
		<tr><td>启用：</td><td>${rule.isuse }</td></tr>
		<tr><td>有效期类型：</td><td>${rule.canuseday }</td></tr>
		<tr><td>最大发放量：</td><td>${rule.maxnum }</td></tr>
		<tr><td>券码前缀：</td><td>${rule.prefix }</td></tr>
		<tr><td>优惠券类型：</td><td>${rule.coupTypeName}</td>	</tr>
		<tr><td>优惠金额：</td><td>${rule.money }</td></tr>
		<#if (rule.coupTypeName?exists && rule.coupTypeName=="满减券")>
		<tr><td>最低消费金额：</td><td>${rule.lowemoney }</td></tr>
		</#if>
		<tr><td>使用范围：</td><td>${rule.scope }</td></tr>
		<#if (rule.scope?exists && rule.scope=="分类券" &&rule.categoryName?exists)><tr><td>分类：</td><td>${rule.categoryName}</td></tr></#if>
	</table>
</#if>
<script src="<@s.url '/assets/js/coup/ruleList.js' />"></script>
<script>
	var ruleId = "${rule.id}".replace(",","");
	$("#ruleId").text(""+ruleId);
</script>
</body>
<html>