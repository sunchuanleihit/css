<#import "/spring.ftl" as s />
<!DOCTYPE html>
	<head>
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/bootstrap/bootstrap.min.css' />" />
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/bootstrap-table.css' />"  />
		<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/datepicker/css/bootstrap-datepicker.min.css' />" />
		<script src="<@s.url '/assets/scripts/jquery-1.10.2.js' />" ></script>
		<script src="<@s.url '/assets/scripts/bootstrap.min.js' />" ></script>
		<script src="<@s.url '/assets/scripts/bootstrap-table.js'/>" ></script>
		<script src="<@s.url '/assets/scripts/bootstrap-table-zh-cn.js'/>" ></script>
		<script src="<@s.url '/assets/scripts/datepicker/js/bootstrap-datepicker.js' />"></script>
		<script src="<@s.url '/assets/scripts/datepicker/locales/bootstrap-datepicker.zh-CN.min.js' />"></script>
	</head>
	<body>
		<div style="margin:5px;">
			开始时间：<input type="text" id="startDate">
			结束时间：<input type="text" id="endDate">
			<button class="btn btn-primary sm" onclick="caculateAchievement()">统计</button>
		</div>	
			<table id="table"></table>
		<script src="<@s.url '/assets/viewjs/achievement.js' />"></script>
	</body>
</html>