var handover_order_fields=[[
       			{field:'id',title:'ID',checkbox:true,width:120},
       			{field:'orderSnMain',title:'主订单编号',width:120},
       			{field:'user',title:'操作人',width:60},
       			{field:'time',title:'操作时间',width:120},
       			{field:'type',title:'操作类型',formatter:typeFormatter, width:75},
       			{field:'closedTime',title:'交接时间',width:120},
       			{field:'content',title:'备注内容',width:400},
       ]];
function typeFormatter(num){
	if(num == 0){
		return "备注";
	}
	if(num == 1){
		return "交接";
	}
}

function closedFormatter(num){
	if(num == 0){
		return "未交接";
	}
	if(num ==1){
		return "已交接";
	}
	if(num ==2){
		return "已处理";
	}
}
function getParam(){
	var orderSnMain = $("#orderSnMain").val();
	var userName = $("#userName").val();
	var type = $("#type").val();
	var closed = $("#closed").val();
	var param = {};
	param['orderSnMain'] = orderSnMain;
	param['userName'] = userName;
	param['type'] = type;
	param['closed'] = closed;
	return param;
}
$(function(){
	initAddRemarkDialog();
	initShowHandoverDialog();
	var param = getParam();
	$('#table').datagrid({
		url: 'findHandover',
		showFooter:true,
		title: '交接与备注列表',
		pagination:true,
		queryParams:param,
		pageSize:20,
		striped:false,
		rownumbers:true,
		columns:handover_order_fields,
		singleSelect:true,
		onDblClickRow:order_detail,//查看订单详情
		toolbar:[{
				id:'close',
				text:'完成交接',
				iconCls:'icon-ok',
				handler:closeHandover
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
	
	$("#search_submit").bind({
		click:function(){
			reloadGrid()
		}
	});
});
function reloadGrid(){
	var param = getParam();
	$('#table').datagrid("load",param);
}
function OrderHandoverController($scope, $http) {
	//跳转退货页面
	$scope.orderDetail = function(index) {
		GetDetailTab("orderDetail","/order/orderDetail/" + index, index+"订单详情");
	}
}
function closeHandover(){
	var selections = $("#table").datagrid("getSelections");
	var size = selections.length;
	if(size == 0){
		alert("请至少选择一个订单");
		return false;
	}
	var ids = "";
	for(var i=0; i<size; i++){
		ids += selections[i].id ;
	}
	var data = {"ids":ids};
	$.ajax({
		url: "closeOrderRemark",
		type:"post",
		data:data,
		success: function(result){
			if(result == "none"){
				alert("请至少选择一个订单");
			}else if(result == "login"){
				alert("请重新登陆");
			}else if(result == "success"){
				alert("完成交接");
				reloadGrid();
			}
		}
	});
}
function order_detail(index,rowData){
	var appElement = document.querySelector('[ng-controller=OrderHandoverController]');
	var $scope = angular.element(appElement).scope(); 
	$scope.orderDetail(rowData['orderSnMain']);
}
function initAddRemarkDialog(){
	$("#addOrderRemarkDialog").dialog({
		title:"交接内容",
		height:400,
		closed: true,
		width:500,
		buttons:[{
			text:"提交",
			handler:function(){
				addOrderRemark();
			}
		},{
			text:"取消",
			handler:function(){
				$("#addOrderRemarkDialog").dialog("close");
			}
		}]
	});
}
function initShowHandoverDialog(){
	$("#showHandoverDialog").dialog({
		title:"交接内容",
		height:400,
		closed:true,
		width:600,
		buttons:[{
			text:"提交",
			handler:function(){
				addHandover();
			}
		},{
			text:"取消",
			handler:function(){
				$("#showHandoverDialog").dialog("close");
			}
		}]
	});
}
function add(){
	$("#addOrderRemarkDialog").dialog("open");
}
function addOrderRemark(){
	var addOrderSnMain = $("#addOrderSnMain").val();
	var orderRemarkContent = $("#orderRemarkContent").val();
	var param = {
			"orderSnMain":addOrderSnMain,
			"content":orderRemarkContent,
			"type":1
	}
	$.ajax({
		url:"addOrderRemark",
		type:"post",
		data:param,
		success: function(result){
			if(result == "login"){
				alert("请重新登陆");
			}else if(result == "error"){
				alert("参数错误");
			}else if(result == "success"){
				$("#addOrderRemarkDialog").dialog("close");
				alert("添加成功");
				reloadGrid();
			}
		}
	});
}
function addHandover(){
	var orderSnMain = $("#handoverOrderSnMain").val();
	var content = $("#handoverContent").val();
	var param = {
			"orderSnMain":orderSnMain,
			"content":content,
			"type":1
	}
	$.ajax({
		url:"addOrderRemark",
		type:"post",
		data:param,
		success: function(result){
			if(result == "login"){
				alert("请重新登陆");
			}else if(result == "error"){
				alert("参数错误");
			}else if(result == "success"){
				$("#showHandoverDialog").dialog("close");
				alert("添加成功");
				reloadGrid();
			}
		}
	});
}
function show(){
	var selections = $("#table").datagrid("getSelections");
	var size = selections.length;
	if(size == 0 || size > 1 ){
		alert("请选择一个订单");
		return false;
	}
	var orderSnMain = selections[0].orderSnMain;
	$("#handoverOrderSnMain").val(orderSnMain);
	var param = {"orderSnMain":orderSnMain};
	$.ajax({
		url:"getOrderHandover",
		data:param,
		success: function(result){
			var divs = "";
			for(var i=0; i<result.length; i++){
				var handover = result[i];
				var div = "<div style='margin-top:5px;margin-left:10px;'>"+handover.user;
				if(handover.closed==0){
					div += ":<span style='color:red;'>";
				}else{
					div += ":<span style='color:green;'>";
				}
				div += handover.content+"</span><span style='float:right;margin-right:5px;'>["+handover.time+"]</span></div>";
				divs += div;
			}
			$("#handoverInfo").html(divs);
		}
	});
	$("#showHandoverDialog").dialog("open");
}
function GetDetailTab(id,url,tabTxt){
    window.parent.addTab(id, url, tabTxt, true);
}