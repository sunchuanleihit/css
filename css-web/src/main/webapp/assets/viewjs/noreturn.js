$(document).ready(function(){
	//未处理已支付取消单
	var noreturnfields = [[
		{field:'order_id',title:'订单号',width:80,hidden:true},
		{field:'orderSnMain',title:'订单编号',width:120,sortable:true},
		{field:'status',title:'订单状态',width:60,sortable:true},
		{field:'needInvoice',title:'是否开票',formatter:yesOrNoFormat,width:60,sortable:true},
		{field:'sellerName',title:'商家名称',width:120,sortable:true},
		{field:'buyerName',title:'会员名称',width:80,sortable:true},
		{field:'orderAmount',title:'订单总额',formatter:doubleFormat,width:80,sortable:true},
		{field:'shippingFee',title:'运费',formatter:doubleFormat,width:80,sortable:true},
		{field:'goodsAmount',title:'商品总额',formatter:doubleFormat,width:80,sortable:true},
		{field:'orderPaid',title:'已付金额',formatter:doubleFormat,width:80,sortable:true},
		{field:'orderNotPaid',title:'未付金额',formatter:doubleFormat,width:80,sortable:true},
		{field:'addTime',title:'订单时间',width:130,sortable:true},
		{field:'postScript',title:'物流提示',width:80,sortable:true},
		{field:'payMessage',title:'订单备注',width:80,sortable:true}                
	]];
	
	$('#table').datagrid({
		url: "findNoreturn",
		showFooter:true,
		title: '订单列表',
		width: 'auto',
		pagination:true,
		pageSize:20,
		striped:false,
		rownumbers:true,
		columns:noreturnfields,
		onDblClickRow:order_detail//查看订单详情
		
	});
	
	$("#no_return_search_submit").click(function(){
		var orderSnMain = $("#orderSnMain").val();
		var buyerName = $("#buyerName").val();
		var sellerName = $("#sellerName").val();
		var orderDate = $("#orderDate").datebox("getValue");
		var storeType = $("#storeType").val();
		var params = {
				"orderSnMain":orderSnMain,
				"buyerName":buyerName,
				"sellerName":sellerName,
				"orderDate":orderDate,
				"storeType":storeType
		};
		$("#table").datagrid("load", params);
	});

	//刷新订单列表
	function refresh_orders(){
		$("#table").datagrid("reload");
	}
	$("#orderDate").datebox({
		formatter:formatDate
	});
	//将日期格式化成YY-MM-dd
	function formatDate(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	var today = formatDate(new Date());
	$("#orderDate").datebox("setValue", today);
   $(".detail_link").click(function(){
		$(this).parent().parent().next().toggle();
   });
   function doubleFormat(num){
	   if(num){
		   return num.toFixed(2);
	   }else{
		   return 0;
	   }
	}
   function yesOrNoFormat(yesOrNo){
		if(yesOrNo){
			return "是";
		}else{
			return "否";
		}
	}
});

function OrderReturnController($scope, $http) {
	//跳转退货页面
	$scope.orderDetail = function(index) {
		GetDetailTab("orderDetail","/order/orderDetail/" + index, index+"订单详情");
	}
}

//查看订单详情
function order_detail(index,rowData){
	var appElement = document.querySelector('[ng-controller=OrderReturnController]');

	var $scope = angular.element(appElement).scope(); 
	
	$scope.orderDetail(rowData['orderSnMain']);
}