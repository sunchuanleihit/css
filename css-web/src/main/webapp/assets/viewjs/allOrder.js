//订单列表页，所有字段
var fields=[[
			{field:'orderId',hidden:true},
			{field:'orderSnMain',title:'订单编号',width:120,sortable:false},
			{field:'cityName',title:'城市',width:120,sortable:false},
			{field:'source',title:'订单来源',width:120,sortable:false},
			{field:'needShiptime',title:'期望送货时间',width:140,sortable:false},
			{field:'status',title:'订单状态',width:80,sortable:false},
			{field:'needInvoice',title:'是否开票',formatter:needInvoice,width:80,sortable:false},
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
	$("#startTime").datebox({
		formatter:formatDate
	});
	$("#endTime").datebox({
		formatter:formatDate
	});
	
	$("#search_submit").bind({
		click: function(){
			var orderSnMain = $("#orderSnMain").val();
			var consignee = $("#consignee").val();
			var startTime = $("#startTime").datebox("getValue");
			var endTime = $("#endTime").datebox("getValue");
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
function yesOrNoFormat(yesOrNo){
	if(yesOrNo){
		return "是";
	}else{
		return "否";
	}
}

//是否需要开票
function needInvoice(rs){
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

function OrderIndexController($scope, $http) {
	//跳转退货页面
	$scope.orderDetail = function(index) {
		GetDetailTab("orderDetail","/order/orderDetail/" + index, index+"订单详情");
	}
}

//查看订单详情
function order_detail(index,rowData){
	var appElement = document.querySelector('[ng-controller=OrderIndexController]');
	var $scope = angular.element(appElement).scope(); 
	$scope.orderDetail(rowData['orderSnMain']);

}

//刷新订单列表
function refresh_orders(){
	$("#table").datagrid("reload");
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