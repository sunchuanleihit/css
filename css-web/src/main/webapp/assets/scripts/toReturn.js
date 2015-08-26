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
		{field:'orderSnMain',title:'原淘常州订单号',width:120},
		{field:'orderId',title:'原订单号',width:80},
		{field:'buyerId',title:'客户ID',width:80},
		{field:'sellerId',title:'商家ID',width:80},
		{field:'returnAmount',title:'退款金额',width:80},
		{field:'addTime',title:'添加时间',width:140},
		{field:'goodsType',title:'商家类型',formatter:return_goods_type,width:80},
		{field:'orderType',title:'订单类型',formatter:return_order_type,width:80},
		{field:'orderStatus',title:'订单状态',formatter:order_status,width:80},
		{field:'goodsStatus',title:'商品状态',formatter:return_goods_status,width:80},
		{field:'refundStatus',title:'退款状态',formatter:return_refund_status,width:80},
		{field:'statementStatus',title:'对账状态',formatter:return_statement_status,width:80},
		{field:'postscript',title:'备注',width:120}
	]];
	$("#table").datagrid({
		columns:fields,
		title:"待退款订单列表",
		pagination:true,
		pageSize:20,
		rownumbers:true,
		nowrap:false,
		url:"/order/findToReturn",
		queryParams:{
			"refundStatus":0
		},
		onDblClickRow:return_order_detail,
		toolbar:[
				{
					id:'cancelReturn',
					text:'取消反向订单',
					iconCls:'icon-cancel',
					handler:cancelOrderReturn
				}
			]
	});
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

	function invoice_status(num){
		num=parseInt(num);
		if(num==0) return "未开票";
		return "已开票";
	}
	
	function take_invoice_status(num){
		num=parseInt(num);
		if(num==2) return "已取票";
		return "未取票"; 
	}
	$("#search_submit").bind({
		click:function(){
			var orderSnMain = $("#orderSnMain").val();
			var startTime = $("#startTime").datebox("getValue");
			var endTime = $("#endTime").datebox("getValue");
			var status = $("#status").val();
			var returnType = $("returnType").val();
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
	function confirm_take_invoice(){
		var temp=$("#wait_take_invoice").datagrid("getSelections");
		var size=temp.length;
		if(size==0){
			__alert("未先择任何订单");
			return false;
		}
		if(size>=1){
			$.messager.confirm('确认','确定要确认取票么?',function(r){  
			    if (r){  
			    	var sns=new Array();
			        for(var i=0;i<size;i++){
			        	sns[i]=temp[i].order_sn_main;
			        }
			        $.get("index.php?app=logistics.logistics&act=confirm_take_invoice",{sns:sns},function(rs){
			        	switch(rs){
			        		case "success":
			        			__alert("确认开票成功");
			        			$("#wait_take_invoice").datagrid("reload");
			        			break;
			        		case "failed":
			        			__alert("确认开票失败");
			        			break;
			        	}
			        });
			    }  
			}); 
		}
	
	}
	
	function confirm_get_goods(){
		var temp=$("#wait_get_goods").datagrid("getSelections");
		var size=temp.length;
		if(size==0){
			__alert("未先择任何订单");
			return false;
		}
		if(size>=1){
			$.messager.confirm('确认','确定要确认取货么?',function(r){  
			    if (r){  
			    	var sns=new Array();
			        for(var i=0;i<size;i++){
			        	sns[i]=temp[i].order_id_r;
			        }
			        $.get("index.php?app=logistics.logistics&act=confirm_get_goods",{sns:sns},function(rs){
			        	switch(rs){
			        		case "success":
			        			__alert("确认取货成功");
			        			$("#wait_get_goods").datagrid("reload");
			        			break;
			        		case "failed":
			        			__alert("确认取货失败");
			        			break;
			        	}
			        });
			    }  
			}); 
		}
	
	}
	
	/*———————————————————————下面为一系列反向订单，格式化函数——————————————————————————————————*/
	
	//商家类型格式化
	function return_goods_type(value,row,index){
		if(value==0){
			return "普通商家";
		
		}
		return "服务商家";
	}
	//订单类型格式化
	function return_order_type(value,row,index){
		if(value==0){
			return "退货单";
		
		}
		return "拒收单";
	}
	//订单状态格式化
	function return_order_status(value,row,index){
		if(value==0){
			return "正常";
		}
		return "取消";
	}
	
	//物品状态格式化
	function return_goods_status(value,row,index){
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
	function return_statement_status(value,row,index){
		if(value==0){
			return "未对账";
		
		}
		return "已对账";
	
	
	}
	
	//退款状态格式化
	function return_refund_status(value,row,index){
		if(value==0){
			return "未退款";
		
		}
		return "已退款";
	
	}
	//右下角提示信息，封装$.messager.show
	function __show(content,title){
		if(!title){
			$.messager.show({
				title:'提示',
				msg:content
			});
			return;
		}
		$.messager.show({
			title:title,
			msg:content
		
		});
		return ;
	}
	//获取当前框架的宽度
	function __get_frame_width(){
		return $(window).width();
	}
	//提示信息,封装$.messager
	function __alert(content,title){
		
		if(!title){
			$.messager.alert("提示",content);
			return;
		}
		$.messager.alert(title,content);
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
	function return_order_detail(){
		alert(1);
	}
});