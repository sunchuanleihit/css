function searchCoup(){
	$('#table').bootstrapTable("refresh");
}

$(function(){
	initTable();
});
function queryParam(params){
	var commoncode = $("#commoncode").val();
	if(commoncode){
		commoncode = commoncode.trim();
	}
	var username = $("#username").val();
	if(username){
		username = username.trim();
	}
	var ruleId = $("#ruleId").val();
	if(!ruleId){
		ruleId = "";
	}
	var map = {
		limit: params.limit,
		offset:params.offset,
		commoncode:commoncode,
		username:username,
		ruleId:ruleId
	};
	return map;
}
function initTable(){
	$('#table').bootstrapTable({
		title:"优惠券",
		url:'/coupon/findCoup',
		pagination:true,
		sidePagination:'server',//设置为服务器端分页
		queryParams:queryParam,
		cache: false,
		pageList: [15,20, 30, 50],
		pageSize:15,
		columns:[{
			field:'commoncode',
			title:'券码'
		},{
			field:'couponName',
			title:'优惠券名称',
			formatter:couponNameFormatter
		},{
			field:'title',
			title:'优惠券类别'
		},{
			field:'usetime',
			title:'有效期',
			formatter:usetimeFormatter
		},{
			field:'userName',
			title:'领用人'
		},{
			field:'createtime',
			title:'领用时间'
		},{
			field:'ischecked',
			title:'使用状态'
		},{
			field:'usedtime',
			title:'使用时间'
		},{
			field:'canuse',
			title:'优惠券状态'
		},{
			title:'操作',
			formatter:actFormatter
		}]
	});
}

function couponNameFormatter(value, row){
	var ruleId = row.couponId;
	var str = '<a href="javascript:void(0)" onclick=viewRuleDetail("'+ruleId+'") >'+value+'</a>';
	return str;
}

function viewRuleDetail(ruleId){
	window.parent.addTab("viewCoupRule", "/coupon/getRuleDetail?ruleId="+ruleId, "查看优惠券规则", true);
}
function usetimeFormatter(index, row){
	var str = row.starttime + " ~ "+ row.endtime;
	return str;
}
function actFormatter(index, row){
	var id = row.id;
	var str = '';
	var title = row.title;
	var commoncode = row.commoncode;
	if(title == "客服专用"){
		if(row.userId == 0){
			str += '<a href="javascript:void(0)" onclick="sendCoup('+id+')">发放</a>';
		}
		if(row.ischecked == "未使用" && row.issue !=2 && row.canuse != "停用"){
			if(str){
				str += ' | ';
			}
			str += '<a href="javascript:void(0)" onclick="stopCoup('+id+',\''+commoncode+'\')">停用</a>';
		}
	}
	return str;
}
function sendCoup(id){
	art.dialog({
		id:"sendCouponDialog",
		title:"发放优惠券",
		content:'输入用户名：<input type="text" id="username" >'
	},function(){
		toSendCoupon(id);
		return false;
	});
}
function toSendCoupon(id){
	var username = $("#username").val();
	if(!username || !username.trim()){
		alert("请输入用户名");
		return;
	}
	var param = {
		id:id,
		username:username
	}
	$.ajax({
		url:"/coupon/sendCoupon",
		data:param,
		type:"POST",
		success:function(result){
			if(result.code == "success"){
				searchCoup();
				alert("发放成功");
				art.dialog({id:"sendCouponDialog"}).close();
			}else{
				alert(result.code);
			}
		}
	});
}
function stopCoup(id,commoncode){
	if(confirm("确认停用"+commoncode+"?")){
		$.ajax({
			url:"/coupon/stopCoup",
			data:{id:id},
			type:"POST",
			success: function(result){
				if(result.code == "success"){
					$('#table').bootstrapTable("refresh");
				}else{
					alert(result.code);
				}
			}
		});
	}
}
