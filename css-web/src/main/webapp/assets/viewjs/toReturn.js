$(document).ready(function(){
	$("#startTime").datebox({
		formatter:formatDate
	});
	$("#endTime").datebox({
		formatter:formatDate
	});
	//将日期格式化成YY-MM-dd
	function formatDate(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	//反向订单的所有字段
	var fields=[[
	    {field:'orderIdR',title:'反向订单ID',width:80,checkbox:true},
		{field:'orderSnMain',title:'订单编号',width:120},
		{field:'orderId',title:'原订单号',width:80},
		{field:'buyerId',title:'客户ID',width:80},
		{field:'sellerId',title:'商家ID',width:80},
		{field:'sellerName',title:'商家名',width:140},
		{field:'returnAmount',title:'退款金额',width:80},
		{field:'addTime',title:'添加时间',width:140},
		{field:'goodsType',title:'商家类型',formatter:returnGoodsTypeFormatter,width:80},
		{field:'orderType',title:'订单类型',formatter:orderTypeFormatter,width:80},
		{field:'orderStatus',title:'订单状态',formatter:orderStatusFormatter,width:80},
		{field:'goodsStatus',title:'商品状态',formatter:returnGoodsStatusFormatter,width:80},
		{field:'refundStatus',title:'退款状态',formatter:returnRefundStatusFormatter,width:80},
		{field:'statementStatus',title:'对账状态',formatter:returnStatementStatusFormatter,width:80},
		{field:'postscript',title:'备注',width:120}
	]];
	$("#table").datagrid({
		columns:fields,
		title:"待退款订单列表",
		pagination:true,
		pageSize:20,
		pageList:[20,30,40,50,100],
		rownumbers:true,
		nowrap:false,
		url:"/order/findToReturn",
		queryParams:{
			"refundStatus":0
		},
		onDblClickRow:return_order_detail,
		toolbar:[{
					id:'cancelReturn',
					text:'取消反向订单',
					iconCls:'icon-cancel',
					handler:cancelOrderReturn
				},{
					id:'printView',
					text:'打印预览',
					iconCls:'icon-print',
					handler:printView
				},{
					id:'exportReturn',
					text:'导出',
					iconCls:'icon-save',
					handler:toExportExcel
				}
			]
	});
	function printView(){
		var selections=$("#table").datagrid("getSelections");
		var size=selections.length;
		var ids = "";
		if(size==0 || size > 1){
			alert("请选择一项进行预览");
			return false;
		}
		var orderIdR=selections[0].orderIdR;
		var orderType = selections[0].orderType;
		var url = "http://lkbackend.loukou.com/taoczbackend/index.php?app=callcenter.check_order&act=print_repay_view&order_id_r="+orderIdR;
		if(orderType ==2){
			url += "&order_type="+orderType;
		}
		window.parent.addTab("printview", url, "退款申请单", true);
	}
	//取消退款订单
	function cancelOrderReturn(){
		var selections=$("#table").datagrid("getSelections");
		var size=selections.length;
		if(!size){
			alert("未选择任何反向订单");
			return false;
		}
		if(size>1){
			alert("一次只能操作一个反向订单");
			return false;
		}
		var orderIdR=selections[0].orderIdR;
		$.get("cancelOrderReturn?orderIdR="+orderIdR, function(rs){
			switch(rs){
				case "cancel_success":
					alert("取消反向订单成功");
					$("#table").datagrid("reload");
					break;
				case "has_refund":
					alert("财务已退款，不能作废退款单");
					break;
				case "cancel_fail":
					alert("取消失败");
					break;
			}
		});
	}

	$("#search_submit").bind({
		click:function(){
			var orderSnMain = $("#orderSnMain").val();
			var startTime = $("#startTime").datebox("getValue");
			var endTime = $("#endTime").datebox("getValue");
			var status = $("#status").val();
			var returnType = $("#returnType").val();
			var refundStatus = $("#refundStatus").val();
			var params = {
					"orderSnMain":orderSnMain,
					"startTime":startTime,
					"endTime":endTime,
					"status":status,
					"queryType":returnType,
					"refundStatus":refundStatus
			};
			$("#table").datagrid("load",params);
		}
	});
	
	/*———————————————————————下面为一系列反向订单，格式化函数——————————————————————————————————*/
	
	//商家类型格式化
	function returnGoodsTypeFormatter(value,row,index){
		if(value==0){
			return "普通商家";
		
		}
		return "服务商家";
	}
	//订单类型格式化
	function orderTypeFormatter(num){
		var map = {
				0:"退货订单",
				1:"拒收订单",
				2:"多付款退款",
				3:"退运费",
				4:"客户赔偿",
				5:"其他退款",
				6:"客户自己取消订单退款",
				7:"特殊退款"
		}
		return map[num];
	}
	//订单状态格式化
	function returnOrderStatusFormatter(value,row,index){
		if(value==0){
			return "正常";
		}
		return "取消";
	}
	
	//物品状态格式化
	function returnGoodsStatusFormatter(value,row,index){
		var res="";
		value=parseInt(value);
		switch(value){
			case 0:
				res="未取货";
				break;
			case 1:
				res= "已取货";
				break;
			case 2:
				res= "损耗";
				break;
			case 3:
				res= "待退商家";
				break;
			case 4:
				res= "已退商家";
				break;
		}
		return res;
	
	}
	
	//对账状态格式化
	function returnStatementStatusFormatter(value,row,index){
		if(value==0){
			return "未对账";
		
		}
		return "已对账";
	}
	
	//退款状态格式化
	function returnRefundStatusFormatter(value,row,index){
		if(value==0){
			return "未退款";
		}
		return "已退款";
	
	}
	//订单状态，根据数字，返回相应的中文
	function orderStatusFormatter(num){
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
	
	function toExportExcel(){
		var selections=$("#table").datagrid("getSelections");
		var size=selections.length;
		var ids = "";
		if(size==0){
			alert("至少选择一项进行导出");
			return false;
		}
		for(var i=0; i<size; i++){
			var orderIdR=selections[i].orderIdR;
			ids += "," + orderIdR;
		}
		var params = {"orderIdRs":ids}
		$("#orderIds").val(ids);
		$("#exportExcelForm").submit();
	}
});

function OrderToReturnController($scope, $http) {
	//跳转退货页面
	$scope.orderDetail = function(index) {
		GetDetailTab("orderDetail","/order/orderDetail/" + index, index+"订单详情");
	}
}

//查看订单详情
function return_order_detail(index,rowData){
	var appElement = document.querySelector('[ng-controller=OrderToReturnController]');

	var $scope = angular.element(appElement).scope(); 
	
	$scope.orderDetail(rowData['orderSnMain']);
}
