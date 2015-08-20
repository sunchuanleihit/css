//订单列表页，所有字段
var fields=[[
			{field:'orderId',title:'订单号',width:80,styler:ordersn_style,sortable:true,hidden:true},
			{field:'orderSnMain',title:'订单编号',width:120,sortable:true},
			{field:'cityName',title:'城市',width:120,sortable:true},
			{field:'source',title:'订单来源',width:120,sortable:true},
			{field:'needShiptime',title:'期望送货时间',width:140,sortable:true},
			{field:'status',title:'订单状态',width:80,formatter:order_status,sortable:true},
			{field:'needInvoice',title:'是否开票',width:80,formatter:need_invoice,sortable:true},
			{field:'invoice_no',title:'开票号码',width:80,sortable:true},
			{field:'buyerName',title:'注册用户名',width:80,sortable:true},
			{field:'payNames',title:'付款方式',width:80,sortable:false},
			{field:'orderAmount',title:'订单总额',width:80,sortable:true},
			{field:'goodsAmount',title:'商品总额',width:80,sortable:true},
			{field:'hasPaid',title:'已付金额',width:80,sortable:true},
			{field:'balance',title:'未付金额',width:80,sortable:true},
			{field:'consignee',title:'收件人姓名',width:80,sortable:true},
			{field:'regionName',title:'地区',width:80,sortable:true},
			{field:'address',title:'详细地址',width:80,sortable:true},
			{field:'phoneMob',title:'电话',width:80,sortable:true},
			{field:'isPrint',title:'是否打单',width:80,sortable:true},
			{field:'payStatus',title:'是否付款',width:80,sortable:true,formatter:f_pay_status},
			{field:'finishedTime',title:'完成时间',width:130,formatter:fromtimestamp,sortable:true},
			{field:'isShipped',title:'是否发货',width:80,sortable:true},
			{field:'shipper',title:'发货人',width:80,sortable:true},
			{field:'shipTime',title:'发货时间',width:130,formatter:fromtimestamp,sortable:true},
			{field:'addTime',title:'订单时间',width:130,formatter:fromtimestamp,sortable:true},
			{field:'postScript',title:'物流提示',width:80,sortable:true},
			{field:'payMessage',title:'订单备注',width:80,sortable:true}
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
		onDblClickRow:order_detail,//查看订单详情
		toolbar:[
			{
				id:'refresh',
				text:'刷新列表',
				iconCls:'icon-reload',
				handler:refresh_orders
		
			},
			{
				id:'edit',
				text:'修改订单',
				iconCls:'icon-edit',
				handler:edit_order
			},
            {
                id:'confirm',
                text:'发货',
                iconCls:'icon-edit',
                handler:confirm_order
            }
		]
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
			var queryType = $("#query_type").val();
			var queryContent = $("#query_content").val();
			var params = {
					"orderSnMain":orderSnMain,
					"consignee":consignee,
					"startTime":startTime,
					"endTime":endTime,
					"status":status,
					"queryType":queryType,
					"queryContent":queryContent
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
//订单状态，根据数字，返回相应的中文
function order_status(num){
	num=parseInt(num);
	switch(num){
		case 0:
			return "初始状态";
			break;
		case 1:
			return "取消";
			break;
		case 2:
			return "无效";
			break;
		case 3:
			return "已审核";
			break;
		case 5:
			return "已提货";
			break;
		case 6:
			return "已拣货";
			break;
		case 8:
			return "已打包";
			break;  
		case 13:
			return "已发货";
			break;	
		case 14:
			return "已发货";
			break;
		case 15:
			return "回单";
			break;
		case 16:
			return "拒收";
			break;
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
	if(vari==1) return "已支付";
	return "未支付";
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