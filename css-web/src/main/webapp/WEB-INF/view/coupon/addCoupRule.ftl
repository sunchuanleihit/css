<#import "/spring.ftl" as s />
<html>
<head>
<script type="text/javascript">
	var coupTypeArr = [];
	<#if (coupTypeList?exists)>
		<#list coupTypeList as coupType>
			var type = {
					"id":"${coupType.id}",
					"title":"${coupType.title}",
					"typeId":"${coupType.typeid}"
			};
			coupTypeArr.push(type);
		</#list>
	</#if>
</script>
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/artDialog.css' />" >
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/bootstrap/bootstrap.min.css' />" />
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/css/bootstrap-table.css' />"  />
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/datepicker/css/bootstrap-datepicker.min.css' />" />
	<script src="<@s.url '/assets/scripts/jquery-1.10.2.js' />" ></script>
	<script src="<@s.url '/assets/scripts/bootstrap.min.js' />" ></script>
	<script src="<@s.url '/assets/scripts/bootstrap-table.js'/>"   ></script>
	<script src="<@s.url '/assets/scripts/bootstrap-table-zh-cn.js'/>" ></script>
	<script src="<@s.url '/assets/scripts/datepicker/js/bootstrap-datepicker.js' />"></script>
	<script src="<@s.url '/assets/scripts/datepicker/locales/bootstrap-datepicker.zh-CN.min.js' />"></script>
	<script src="<@s.url '/assets/viewjs/coup/addCoupRule.js' />"></script>
	<script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
</head>
<body>
<div ng-app ng-controller="addCoupRuleCtrl" style="margin-left:10px;">
<table class="table">
	<tr><td style="width:150px;">优惠券类别</td>
		<td style="width:200px;"><select ng-model="coupType" id="typeId" ng-options="type.title for type in coupTypes" ng-change="coupTypeChange()"></select></td>
		<td></td>
	</tr>
	<tr><td>优惠券名称</td><td><input type="text" name="couponName" id="couponName"></td><td></td></tr>
	<tr id="commoncodeTr"><td>公有券券码：</td><td>LK<input type="text" name="commoncode" id="commoncode"></td><td style="color:blue;">4-18位；必须有4位以上大写字母开头，可以有数字。券码不能重复</td></tr>
	<tr id="prefixTr"><td>优惠券前缀：</td><td><input type="text" name="prefix" id="prefix"></td><td style="color:blue;">2-6位大写字母，不能以LK开头</td></tr>
	<tr><td>最大发放量：</td><td><input type="text" name="maxnum" id="maxnum"></td><td style="color:blue;">0表示不限</td><td></td></tr>
	<tr><td>使用时间：</td>
		<td><select id="canuseType" onchange="useTimeTypeChange()">
				<option value="1">N天有效</option>
				<option value="0">开始~结束</option>
			</select>
		</td>
		<td></td>
	</tr>
	<tr id="canusedayTr"><td>领用有效天数：</td><td><input type="text" name="canuseday" id="canuseday">天</td><td style="color:blue;">自领取日算起</td><td></td></tr>
	<tr id="begintimeTr"><td>开始时间：</td><td><input type="text" name="begintime" id="begintime"></td><td></td></tr>
	<tr id="endtimeTr"><td>结束时间：</td><td><input type="text" name="endtime" id="endtime"></td><td></td></tr>
	<tr><td>优惠券类型：</td>
		<td><select id="coupontypeid" onchange="coupontypeidChange()">
				<option value=0>满减券</option>
				<option value=1>现金券</option>
			</select>
		</td><td></td>
	</tr>
	<tr><td>优惠金额：</td><td><input type="text" name="money" id="money"></td><td></td></tr>
	<tr id="lowemoneyTr"><td>最低消费金额：</td><td><input type="text" name="lowemoney" id="lowemoney"></td><td></td></tr>
	<tr><td>使用范围：</td>
		<td><select name="couponType" id="couponType" onchange="useScopeChange()" >
				<option value=0>全场券</option>
				<option value=4>分类券</option>
			</select>
		</td>
		<td class="category" style="display:none">
			<select id="category1" onchange="category1Change()"></select>
			<select id="category2" onchange="category2Change()"></select>
			<select id="category3"></select>
			<button onclick="addCategory()">添加</button>
		</td>
	</tr>
	<tr class="category" style="display:none;"><td>已选分类：</td><td colspan=2 id="categories"></td></tr>
	<tr>
		<td colspan=2 style="text-align:right;"><button class="btn btn-primary" ng-click="addCoupRule()" >提交</button></td>
	</tr>
</table>
</div>
</body>
<script>
	<#if (defaultType?exists)>
		$("#returnBtn").bind({
			click:function(){
				loadUrl('/coupon/typeList')
			}
		});
	</#if>
	<#if !(defaultType?exists)>
	$("#returnBtn").bind({
		click:function(){
			loadUrl('/coupon/ruleList')
		}
	});
	</#if>
</script>
</html>
