var app = angular.module('app', []);
app.controller('IndexCtrl', ['$scope','$http', function ($scope, $http) {
	var cityOptions = new Array();
	$http.get("/complaint/getAllCity").success(function(data){
		if(data){
			for(var i=0; i<data.length; i++){
				var site = data[i];
				var siteOption = {
						"id":site.shortCode,
						"name":site.siteName
				};
				cityOptions.push(siteOption);
			}
			$scope.cityOptions = cityOptions;
		}
	});
    $scope.cityChange = function () {
		var siteCode = $scope.city.id;
		$scope.weic = "";
		$http.get("/complaint/getStores?siteCode="+siteCode).success(function(data){
			if(data){
				var storeOptions = new Array();
				for(var i=0; i<data.length; i++){
					var store = data[i];
					storeOptions.push({
						"id":store.storeId,
						"name":store.storeName
					});
				}
				$scope.weicOptions = storeOptions;
			}
		});
    };
    
    $scope.reloadTable = function(){
    	var orderSnMain = $("#orderSnMain").val();
    	var weic = "";
    	if($scope.weic){
    		weic = $scope.weic.name;
    	}
    	var startTime = $("#startTime").datebox("getValue");
    	var endTime = $("#endTime").datebox("getValue");
    	var params = {
    		"orderSnMain": orderSnMain,
    		"startTime": startTime,
    		"weic": weic,
    		"endTime": endTime
    	};
    	$('#table').datagrid("load",params);
    }
}]);
angular.bootstrap(document, ['app']);
$(function(){
	$("#startTime").datebox({
		formatter:formatDate
	});
	$("#endTime").datebox({
		formatter:formatDate
	});
	initTable();
});
function initTable(){
	var fields = [[
        {field:'tid',title:'记录ID',width:80, checkbox:true},
   		{field:'complaintTime',title:'日期',width:70,sortable:false},
   		{field:'orderSnMain',title:'订单号',width:110,sortable:false},
   		{field:'type',title:'投诉等级',formatter:typeFormatter,width:60, sortable:false},
   		{field:'status',title:'处理状态',formatter:statusFormatter,width:60,sortable:false},
   		{field:'content',title:'投诉内容',width:180,sortable:false},
   		{field:'department',title:'涉及部门',width:80,sortable:false},
   		{field:'userName',title:'用户姓名',width:80,sortable:false},
   		{field:'mobile',title:'联系方式',width:100,sortable:false},
   		{field:'sellerName',title:'涉及商家',width:180,sortable:false},
   		{field:'goodsName',title:'商品名称',width:180,sortable:false},
   		{field:'actor',title:'经手人',width:80,sortable:false}
    ]];
   	$('#table').datagrid({
   		url: "/complaint/findComplaint",
   		showFooter:true,
   		title: '投诉列表',
   		pagination:true,
   		pageSize:20,
   		striped:false,
   		rownumbers:true,
   		columns:fields,
   		onDblClickRow:order_detail,//查看订单详情
   		toolbar:[
   			{
   				id:'tosu_msg',
   				text:'详情',
   				iconCls:'icon-more',
   				handler:complaintDetail
   			},
   			{
   				id:'export_tosu',
   				text:'导出',
   				iconCls:'icon-save',
   				handler:exportComplaint
   			}
   		]
   	});
}
//将日期格式化成YY-MM-dd
function formatDate(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function typeFormatter(num){
	var map = {0:"一般",1:"严重",2:"非常严重"};
	return map[num];
}
function statusFormatter(num){
	var map = {0:"待处理",1:"处理中",2:"已处理"};
	return map[num];
}
function complaintDetail(){
	alert(1);
}
function exportComplaint(){
	alert(2);
}
function order_detail(){
	alert(3);
}