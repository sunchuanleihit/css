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
    	var siteCode = "";
    	if($scope.city && $scope.city!=null){
    		siteCode = $scope.city.id;
    	}
    	$scope.weicOptions = [];
		$scope.weic = "";
		if(siteCode){
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
		}
    };
    $scope.depts=[{
        "id":1,
        "name":"微仓",
        "types":[
             {
             	"id":1,
             	"name":"配送延迟"
             },
             {
             	"id":2,
             	"name":"虚假回单"
             },
             {
             	"id":3,
             	"name":"商品皮损"
             },
             {
             	"id": 4,
             	"name":"配送缺发"
             },
             {
             	"id": 5,
             	"name":"态度问题"
             }
         ]},{
            "id":2,
            "name":"采购",
            "types":[
                {
	            	"id": 6,
	            	"name":"实物不符"
	            },
	            {
	            	"id": 7,
	            	"name":"产品质量"
	            },
	            {
	            	"id": 8,
	            	"name":"产品缺货"
	            }
	        ]
	    },{
	    	"id":3,
	    	"name":"仓库",
	    	"types":[
				{
					"id": 9,
					"name":"包装问题"
				},
				{
					"id": 10,
					"name":"产品销期"
				}
	    	]
	    },{
	    	"id":4,
	    	"name":"系统",
	    	"types":[
    	        {
	            	"id": 11,
	            	"name":"订单错误"
	            },
	            {
	            	"id": 12,
	            	"name":"支付错误"
	            },
	            {
	            	"id": 13,
	            	"name":"账户问题"
	            }
	    	]
	    }
    ];
    
    $scope.departmentChange = function(){
    	$scope.complaintType = "";
    }
    
    $scope.resetForm = function(){
    	$("#complaintForm")[0].reset();
    	$("#startTime").datebox("setValue","");
    	$("#endTime").datebox("setValue","");
    	$scope.department = "";
    	$scope.city = "";
    }
    
    $scope.reloadTable = function(){
    	var orderSnMain = $("#orderSnMain").val();
    	var startTime = $("#startTime").datebox("getValue");
    	var endTime = $("#endTime").datebox("getValue");
    	var handleStatus = $("#handleStatus").val();
    	var department = "";
    	if($scope.department){
    		department = $scope.department.id;
    	}
    	var complaintType = "";
    	if($scope.complaintType){
    		complaintType = $scope.complaintType.id;
    	}
    	var cityCode = "";
    	if($scope.city){
    		cityCode = $scope.city.id;
    	}
    	var weic = "";
    	if($scope.weic){
    		weic = $scope.weic.id;
    	}
    	var params = {
    		"orderSnMain": orderSnMain,
    		"startTime": startTime,
    		"endTime": endTime,
    		"handleStatus":handleStatus,
    		"department":department,
    		"complaintType":complaintType,
    		"cityCode":cityCode,
    		"weic": weic
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
        {field:'id',title:'记录ID',width:80, checkbox:true},
   		{field:'createTime',title:'时间',width:120,sortable:false},
   		{field:'orderSnMain',title:'订单号',width:110,sortable:false},
   		{field:'department',title:'部门',width:60, sortable:false},
   		{field:'complaintType',title:'投诉类型',width:60, sortable:false},
   		{field:'handleStatus',title:'处理状态',formatter:statusFormatter,width:60,sortable:false},
   		{field:'content',title:'投诉内容',width:180,sortable:false},
   		{field:'userName',title:'用户姓名',width:80,sortable:false},
   		{field:'mobile',title:'联系方式',width:100,sortable:false},
   		{field:'city',title:'城市',width:100,sortable:false},
   		{field:'',title:'商家ID',width:80,sortable:false},
   		{field:'whName',title:'商家',width:180,sortable:false},
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
	var map = {1:"处理中",2:"已处理"};
	return map[num];
}
function exportComplaint(){
	var selections=$("#table").datagrid("getSelections");
	var size=selections.length;
	var ids = "";
	if(size==0){
		alert("至少选择一项进行导出");
		return false;
	}
	for(var i=0; i<size; i++){
		var id=selections[i].id;
		ids += "," + id;
	}
	var params = {"id":ids}
	$("#ids").val(ids);
	$("#exportExcelForm").submit();
}
function order_detail(index,rowData){
	var orderSnMain=rowData.orderSnMain;
	var id=rowData.id;
	GetDetailTab("complaintMsg","/complaint/complaintMsg?orderSnMain=" + orderSnMain + "&complaintId="+id, orderSnMain+"投诉");
}
