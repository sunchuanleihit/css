//订单列表页，所有字段
var fields=[[
			{field:'orderId',hidden:true},
			{field:'orderSnMain',title:'订单编号',width:120,sortable:false},
			{field:'cityName',title:'城市',width:120,sortable:false},
			{field:'source',title:'订单来源',width:120,sortable:false},
			{field:'needShiptime',title:'期望送货时间',width:140,sortable:false},
			{field:'status',title:'订单状态',width:80,sortable:false},
			{field:'needInvoice',title:'是否开票',formatter:yesOrNoFormat,width:80,sortable:false},
			{field:'invoiceNo',title:'开票号码',width:80,sortable:false},
			{field:'buyerName',title:'注册用户名',width:80,sortable:false},
			{field:'payNames',title:'付款方式',width:80,sortable:false},
			{field:'orderAmount',title:'订单总额',formatter:doubleFormat,width:80,sortable:false},
			{field:'goodsAmount',title:'商品总额',formatter:doubleFormat,width:80,sortable:false},
			{field:'orderPaid',title:'已付金额',formatter:doubleFormat,width:80,sortable:false},
			{field:'orderNotPaid',title:'未付金额',formatter:doubleFormat,width:80,sortable:false},
			{field:'consignee',title:'收件人姓名',width:80,sortable:false},
			{field:'regionName',title:'地区',width:80,sortable:false},
			{field:'address',title:'详细地址',width:80,sortable:false},
			{field:'phoneMob',title:'电话',width:80,sortable:false},
			{field:'isPrint',title:'是否打单',formatter:yesOrNoFormat,width:80,sortable:false},
			{field:'payStatus',title:'是否付款',formatter:f_pay_status,width:80,sortable:false},
			{field:'finishedTime',title:'完成时间',width:130,sortable:false},
			{field:'shipTime',title:'发货时间',width:130,sortable:false},
			{field:'addTime',title:'订单时间',width:130,sortable:false},
			{field:'postScript',title:'物流提示',width:80,sortable:false},
			{field:'payMessage',title:'订单备注',width:80,sortable:false}
		]];
$(document).ready(function(){
	$('#table').datagrid({
		url: "findOrder",
		showFooter:true,
		title: '订单列表',
		width: 'auto',
		pagination:true,
		pageSize:20,
		height: 500,
		striped:false,
		rownumbers:true,
		columns:fields,
		onDblClickRow:order_detail//查看订单详情
	});
	//$("#status").combobox();
	//$("#type").combobox();
	$("#start_time").datebox({
		formatter:formatDate
	});
	$("#end_time").datebox({
		formatter:formatDate
	});
	
	$("#search_submit").bind({
		click: function(){
			var orderSnMain = $("#order_sn_main").val();
			var consignee = $("#consignee").val();
			var startTime = $("#start_time").datebox("getValue");
			var endTime = $("#end_time").datebox("getValue");
			var status = $("#status").val();
			var queryType = $("#queryType").val();
			var queryContent = $("#queryContent").val();
			var payStatus = $("#payStatus").val();
			var buyerName = $("#buyerName").val();
			var params = {
					"orderSnMain":orderSnMain,
					"consignee":consignee,
					"startTime":startTime,
					"endTime":endTime,
					"status":status,
					"queryType":queryType,
					"queryContent":queryContent,
					"payStatus":payStatus,
					"buyerName":buyerName
			};
			$("#table").datagrid('load',params);
		}
	});
});
//设置不同状态下订单编号的样式
function ordersn_style(value,data,index){
	if(data.status==0){
		return "background-color:yellow;";
	}
}
function yesOrNoFormat(yesOrNo){
	if(yesOrNo){
		return "是";
	}else{
		return "否";
	}
}

//是否需要开票
function need_invoice(rs){
	rs=parseInt(rs);
	switch(rs){
		case 0:
			return "否";
			break;
		case 1:
			return "是";
			break;
		case 2:
			return "已发送提醒";
			break;
		case 3:
			return "是";
			break;	
	}
}

function f_pay_status(vari){
	if(vari==1){
		return "已支付";
	}
	return "未支付"
}
//将PHP的unix时间戳转换成开如yyyy/mm/dd的时间
function fromtimestamp(timestamp){
	if(!!!timestamp || timestamp==0){
		return " ";
	}
	var date= new Date(parseInt(timestamp) * 1000);
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var h = date.getHours();
	var i = date.getMinutes();
	var s = date.getSeconds();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+" "+(h<10?('0'+h):h)+':'+(i<10?('0'+i):i)+":"+(s<10?('0'+s):s);
}
//查看订单详情
function order_detail(index,rowData){
	alert(1);
}
//刷新订单列表
function refresh_orders(){
	$("#table").datagrid("reload");
}
//修改订单
function edit_order(){
	var rs=$("#table").datagrid("getSelections");
	var size=(rs.length);
	if(!!size==false){
		__alert("未选中任何订单");
		return false;
	}
	else if(size>1){
		__alert("一次只能修改一个订单！");
		return false;
	}
	var i=0;
	var ids=new Array();
	$.each(rs,function(index,value){
		ids[i]=value.order_sn_main;
		i++;	
	
	});
	var order_id=ids[0]+"";
	var flag=$("#tabs").tabs("exists","订单详情");
	if(!flag){
		$.get("index.php?app=callcenter.check_order&act=change_order_form",{"order_id":order_id},function(rs){
			$("#tabs").tabs("add",{
				title:"订单详情",
				content:rs,
				closable:true
			});
			
		});
		
	}
	else{
		$("#tabs").tabs("select","订单详情");
		var stab=$("#tabs").tabs("getSelected");
		$.get("index.php?app=callcenter.check_order&act=change_order_form",{"order_id":order_id},function(rs){
			$("#tabs").tabs("update",{
				tab:stab,
				options:{
					title:"订单详情",
					content:rs,
					closable:true
				}
			});
		});
	}
}
//确认订单
function confirm_order(){
    var rs=$("#table").datagrid("getSelections");
    var size=(rs.length);
	if(!!size==false){
		__alert("未选中任何订单");
		return false;
	}
	else if(size>1){
		__alert("一次只能修改一个订单！");
		return false;
	}
        
	var i=0;
	var ids=new Array();
	$.each(rs,function(index,value){
		ids[i]=value.order_id;
		i++;	
	
	});
	var order_id=ids[0]+"";
        $.get("index.php?app=callcenter.check_order&act=confirm_order",{"order_id":order_id},function(rs){
               if(rs == 'success'){
                   __alert("发货成功");
                   refresh_orders();
               }else{
                   __alert("未能成功发货");
               }
		});
}
//将日期格式化成YY-MM-dd
function formatDate(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function doubleFormat(num){
	return num.toFixed(2);
}
function showOrderAction(){
	$("#dialog").dialog({
		title:"订单详情",
		width:600,
		autoOpen:false,
		height:500,
		href:"orderAction?orderSnMain=150827183843417"
	});
}