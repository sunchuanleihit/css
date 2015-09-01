var handover_order_fields=[[
       			{field:'id',title:'ID',checkbox:true,width:120},
       			{field:'order_sn_main',title:'主订单编号',width:120},
       			{field:'user',title:'操作人',width:60},
       			{field:'time',title:'操作时间',width:120},
       			{field:'type',title:'操作类型',width:75},
       			{field:'closed',title:'交接状态',width:75},
       			{field:'bumen',title:'交接部门',width:60},
       			{field:'user_closed',title:'交接人',width:60},
       			{field:'closed_time',title:'交接时间',width:120},
       			{field:'content',title:'备注内容',width:400},
       			{field:'finished_time',title:'完成时间',width:120},
       ]];
$(function(){
	$('#dt_handover').datagrid({
		url: 'findHandover',
		showFooter:true,
		title: '交接与备注列表',
		pagination:true,
		pageSize:20,
		striped:false,
		rownumbers:true,
		columns:handover_order_fields,
		singleSelect:true,
		onDblClickRow:order_detail,//查看订单详情
		toolbar:[{
				id:'close',
				text:'交接',
				iconCls:'icon-edit',
				handler:close
			},{
				id:'add',
				text:'添加交接任务',
				iconCls:'icon-add',
				handler:add
			},{
				id:'show',
				text:'详情',
				iconCls:'icon-add',
				handler:show
			}
		]
	});
});
function OrderIndexController($scope, $http) {
	//跳转退货页面
	$scope.orderDetail = function(index) {
		GetDetailTab("orderDetail","/order/orderDetail/" + index, index+"订单详情");
	}
}
function order_detail(){
	alert("detail");
}
function add(){
	alert("add");
}
function show(){
	alert("show");
}