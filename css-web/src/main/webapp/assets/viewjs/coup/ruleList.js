$(function(){
	initTable();
});
function initTable(){
	$('#table').bootstrapTable({
		url: '/coupon/findRule',
		pagination: true, 
		sidePagination:'server',//设置为服务器端分页
		queryParams:queryParams,
		cache: false,
		pageList: [10, 20, 50],
	    columns: [{
	        field: 'id',
	        title: '优惠券ID',
	    }, {
	    	field: 'categoryName',
	    	title: '优惠券类别'
	    },{
	        field: 'name',
	        title: '优惠券名称'
	    },{
	        field: 'type',
	        title: '优惠券类型'
	    },{
	        field: 'scope',
	        title: '适用范围'
	    },{
	        field: 'canuseday',
	        title: '有效期'
	    },{
	    	field: 'isuse',
	    	title:'启用状态'
	    },{
	        field: 'operate',
	        title: '操作',
	        align: 'center',
	        //events: operateEvents,
        	formatter: operateFormatter
	     }]
	});
}
function queryParams(params){
	var typeId = $("#typeId").val();
	var ruleId = $("#coupRuleId").val();
	var ruleName = $("#coupRuleName").val();
	var ruleType = $("#coupType").val();
	var scope = $("#useScope").val();
	return{
		limit: params.limit,
		offset:params.offset,
		typeId:typeId,
		ruleId:ruleId,
		ruleName:ruleName,
		ruleType:ruleType,
		scope:scope
	}
}
function viewRuleDetail(ruleId){
	window.parent.addTab("viewCoupRule", "/coupon/getRuleDetail?ruleId="+ruleId, "查看优惠券规则", true);
}
function operateFormatter(value, row, index){
	var id = row.id;
	var name = row.name;
	var isuse = row.isuse;
	var commoncode = row.commoncode;
	var title = row.categoryName;
	var getRuleDetail = '<a href="javascript:void(0)" onclick=viewRuleDetail("'+id+'") >查看详情</a>';
	var dataStatistic = '<a href="javascript:void(0)" onclick=statistic('+id+')>数据统计</a>';
	var exportCoup = '<a href="/coupon/exportCoup?ruleId='+id+'">导出券码</a>';
	var str = '';
	str += getRuleDetail +" | ";
	if(title == "客服专用"){
		var stopUseCoupRule = '<a href="javascript:void(0)" onclick="changeRuleIsuse('+id+',\''+name+'\', 2)">停用规则</a>';
		var startUseCoupRule = '<a href="javascript:void(0)" onclick="changeRuleIsuse('+id+',\''+name+'\', 1)">启用规则</a>';
		if(isuse == "停用"){
			str += startUseCoupRule;
		}else{
			str += stopUseCoupRule;
		}
		str += " | ";
	}
	str += dataStatistic+"<br/>";
	if(!commoncode && isuse == "启用" && title == "客服专用"){
		var addCoupon = '<a href="javascript:void(0)" onclick="addCoupon('+id+',\''+name+'\')">增加券码</a>';
		str += addCoupon +" | ";
	}
	if(!commoncode){
		str += '<a href="javascript:void(0)" onclick="manageCoupon('+id+', \''+name+'\')">查看券码</a> | ';
	}
	str += exportCoup;
	return str;
}

function statistic(ruleId){
	$.ajax({
		url:"/coupon/statisticRule",
		data:{id:ruleId},
		type:"POST",
		success:function(result){
			var getNum = result.getNum;
			var useNum = result.useNum;
			var percent = "0.00%";
			if(getNum>0){
				var f = (useNum/getNum)*100;
				percent = f.toFixed(2)+"%"
			}
			art.dialog({
				title:"数据统计",
				content:'领用量：'+getNum+'<br>使用量：'+useNum+'<br>使用率：'+percent
			});
		}
	});
}

function manageCoupon(ruleId, ruleName){
	window.parent.addTab("couponList", "/coupon/coupList?ruleId="+ruleId, ruleName+"—券码列表", true);
}

function addCoupon(id, name){
	art.dialog({
		id:"addCouponDoalog",
		title:name+"增加券码",
		content:'<input type="text" id="addCoupNumber" >'
	},function(){
		toAddCoupon(id);
		return false;
	});
}
function toAddCoupon(id){
	var number = $("#addCoupNumber").val();
	if(!number){
		alert("请输入正确数量");
		return;
	}else{
		number = number.trim();
	}
	if(!/^[0-9]*$/.test(number)){
		alert("请输入正确的数量");
		return;
	}
	$.ajax({
		type:"POST",
		url:"/coupon/addCouponNumber",
		data:{ruleId:id,number:number},
		success:function(data){
			if(data.result == "success"){
				alert("添加成功");
				art.dialog({id:"addCouponDoalog"}).close();
			}else{
				alert(data.result);
			}
		}
	});
}
function changeRuleIsuse(id, name, isuse){
	var str = "";
	if(isuse == 2){
		str = "确认停用规则："+name+"?";
	}else if(isuse==1){
		str = "确认启用规则："+name+"?";
	}
	if(confirm(str)){
		$.ajax({
			url:"/coupon/updateCoupUse",
			type:"POST",
			data:{ruleId:id,isuse:isuse},
			success:function(){
				searchRule();
			}
		});
	}
}
function searchRule(){
	var coupRuleId = $("#coupRuleId").val();
	var intPattern = /^\d*$/;
	if(!coupRuleId.match(intPattern)){
		alert("优惠券ID格式不正确");
		return false;
	}
	$('#table').bootstrapTable("refresh");
}