function opershow() {
	$("#oper").toggle();
}

$(function(){
	var complaintId=$("input[name='complaintId']").val();
	if(complaintId>0){
		selectDepartment();
	}
});

//选择微仓
function selectSeller(){
	var whId=$("#whId").val();
	var tr=$("tr[name='s"+whId+"']");
	$(tr.siblings()).css("display", "none");
	$(tr.siblings()).find(":checkbox").attr("checked",false);
	tr.removeAttr("style");
	
	var whName=$("#whId").find("option:selected").text();
	$("input[name='whName']").val(whName);
}

//选择部门
function selectDepartment(){
	var departmentId=$("#department").val();
	var tr=$("#type"+departmentId);
	$(tr.siblings()).css("display", "none");
	$(tr.siblings()).attr("disabled",true);
	tr.attr("disabled",false);
	tr.css('display', 'initial');
}

function OrderDetailController($scope, $http) {
	//获取包裹商品列表
	$scope.getordergoodslist = function(orderId) {
		$("#subOrderId").val(orderId);
		$http.get('/order/getOrderGoodsList', {
			params : {
				orderId : orderId
			}
		}).success(function(data) {
			$scope.goodsList = data;
		}).error(function(data) {
			
		});
	};
	
	//跳转退货页面
	$scope.returnGoods = function(index) {
		GetDetailTab("returngoods","/order/returnGoods/" + index, index+"退货");
	}
	
	//退货
	$scope.generateReturn = function() {
		var radioChks = $("input[type=radio][name='orderId']:checked");
		var selectChks = $("input[type=checkbox][name=checkedGoods]:checked");
		if(!radioChks){
			jAlert("请选择退货订单");
		}else if(!selectChks){
			jAlert("请选择退货商品");
		}else{
			jConfirm('确认要退货吗？', '退货确认', function(r) {
				if (r) {
					$.ajax( {   
						type : "POST",
						url : "/order/generateReturn", 
						data : $('#returnForm').serializeArray(),
						dataType: "json",
						success : function(data) {
							if(data.code==200){
								jAlert(data.message);
							}else{
								jAlert(data.message);
							}
						},   
						error :function(data){
							jAlert("录入信息错误");
						}
					});
				}
			});
		}
	}
	
	//作废订单
	$scope.cancel = function(orderSnMain) {
		jConfirm('确认要作废吗？', '作废确认', function(r) {
			if (r) {
				$http.get("/order/cancelOrder", {
					params : {
						orderSnMain : orderSnMain
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
	
	//取消作废
	$scope.resetCancel = function(orderSnMain) {
		jConfirm('确认要取消作废吗？', '取消作废确认', function(r) {
			if (r) {
				$http.get("/order/resetCancelOrder", {
					params : {
						orderSnMain : orderSnMain
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
	
	//跳转多付款退款页面
	$scope.multiplePaymentRefund = function(index) {
		GetDetailTab("multiplePaymentRefund","/order/multiplePaymentRefund/" + index, index+"多付款退款");
	}
	
	//多付款退款
	$scope.generatePaymentRefund = function() {
		jConfirm('确认要退款吗？', '退款确认', function(r) {
			if (r) {
			$.ajax( {
				type : "POST",
				url : "/order/generatePaymentRefund", 
				data : $('#returnForm').serializeArray(),
				dataType: "json",
				success : function(data) {
					if(data.code==200){
						jAlert(data.message);
					}else{
						jAlert(data.message);
					}
				},   
				error :function(data){
					jAlert("系统错误");
				}
			});
			}
		});
	}
	
	//获取特殊退款页面
	$scope.specialPaymentRefundBox = function(){
		var orderSnMain=$('#orderSnMain').val();
		if(!orderSnMain){
			jAlert("请输入订单号");
		}else{
			$("#pop_pass").colorbox({
	            href: "/order/specialPaymentRefundBox/" + orderSnMain,
	            iframe: true,
	            width: "800px",
	            height: "420px",
	            top: "100px",
	            opacity: 0,
	            overlayClose: false,
	            scrolling: true
	        });
		}
    }
	
	//订单操作详细
	$scope.showOrderAction = function(orderSnMain){
		$.colorbox({
            href: "/order/orderAction?orderSnMain=" + orderSnMain,
            iframe: true,
            width: "800px",
            height: "420px",
            top: "100px",
            opacity: 0,
            overlayClose: false,
            scrolling: true
        });
	}
	//多付款退款
	$scope.generateSpecialPaymentRefund = function() {
		jConfirm('确认要退款吗？', '退款确认', function(r) {
			if (r) {
			$.ajax( {
				type : "POST",
				url : "/order/generateSpecialPaymentRefund", 
				data : $('#returnForm').serializeArray(),
				dataType: "json",
				success : function(data) {
					if(data.code==200){
						jAlert(data.message);
					}else{
						jAlert(data.message);
					}
				},   
				error :function(data){
					jAlert("系统错误");
				}
			});
			}
		});
	}
	
	//保存
	$scope.changeOrder = function() {
		jConfirm('确认要保存吗？', '保存确认', function(r) {
			if (r) {
			$.ajax( {
				type : "POST",
				url : "/order/changeOrder", 
				data : $('#orderForm').serializeArray(),
				dataType: "json",
				success : function(data) {
					if(data.code==200){
						jAlert(data.message);
					}else{
						jAlert(data.message);
					}
				},   
				error :function(data){
					jAlert("系统错误");
				}
			});
			}
		});
	}
	
	//订单操作详细
	$scope.showOrderAction = function(orderSnMain){
		$.colorbox({
            href: "/order/orderAction?orderSnMain=" + orderSnMain,
            iframe: true,
            width: "800px",
            height: "420px",
            top: "100px",
            opacity: 0,
            overlayClose: false,
            scrolling: true
        });
	}
	
	//获取客户详细信息
	$scope.showCustomInfo = function(buyerId){
		GetDetailTab("customerInfo","/customer/customerInfo?buyerId=" + buyerId, "客户详细信息");
	}
	
	//跳转投诉
	$scope.complaintMsg = function(index) {
		GetDetailTab("complaintMsg","/complaint/complaintMsg?orderSnMain=" + index + "&complaintId=0", index+"投诉");
	}
	
	//提交投诉
	$scope.generateComplaint = function() {
		jConfirm('确认要提交吗？', '提交确认', function(r) {
			if (r) {
			$.ajax( {
				type : "POST",
				url : "/complaint/generateComplaint", 
				data : $('#returnForm').serializeArray(),
				dataType: "json",
				success : function(data) {
					if(data.code==200){
						jAlert(data.message);
					}else{
						jAlert(data.message);
					}
				},   
				error :function(data){
					jAlert("系统错误");
				}
			});
			}
		});
	}
	
	//子订单作废
	$scope.cancelSubOrder = function() {
		var orderId=$("#subOrderId").val();
		jConfirm('确认要作废子订单吗？', '作废子订单确认', function(r) {
			if (r) {
				$http.get("/order/cancelSubOrder", {
					params : {
						orderId : orderId
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
	
	//子订单取消作废
	$scope.resetCancelSubOrder = function() {
		var orderId=$("#subOrderId").val();
		jConfirm('确认要取消作废子订单吗？', '取消作废子订单确认', function(r) {
			if (r) {
				$http.get("/order/resetCancelSubOrder", {
					params : {
						orderId : orderId
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
	
	//子订单支付页面
	$scope.paySubOrderHtml = function(orderSnMain) {
		$http.get("/order/paySubOrder", {
			params : {
				orderSnMain : orderSnMain,
				payId : 0
			}
		}).success(function(data) {
			if(data.code==200){
				$.colorbox({
		            href: "/order/orderPayList?orderSnMain=" + orderSnMain,
		            iframe: true,
		            width: "800px",
		            height: "420px",
		            top: "100px",
		            opacity: 0,
		            overlayClose: false,
		            scrolling: true
		        });
			}else{
				jAlert(data.message);
			}
		}).error(function(data) {
			jAlert(data.ExceptionMessage);
		});
	}
	
	//子订单支付
	$scope.paySubOrder = function(orderSnMain) {
		var payId=$("input[name='paymentId']:checked").val();
		jConfirm('确认要支付子订单吗？', '支付子订单确认', function(r) {
			if (r) {
				$http.get("/order/paySubOrder", {
					params : {
						orderSnMain : orderSnMain,
						payId : payId
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
	
	$scope.showOrderRemark = function(orderSnMain){
		$.colorbox({
            href: "/order/showOrderRemark?orderSnMain=" + orderSnMain,
            iframe: true,
            width: "600px",
            height: "420px",
            top: "100px",
            opacity: 0,
            overlayClose: false,
            scrolling: true
        });
		
	}

	function getRemark(){
		var orderSnMain = $("#orderSnMain").val();
		var type = $("input[name='orderOrHandover']:checked").val();
		var param = {
				"orderSnMain":orderSnMain,
				"type":type
		};
		$.ajax({
			url:"/order/getOrderRemark",
			data:param,
			type: "POST",
			success: function(result){
				var divs = "";
				for(var i=0; i<result.length; i++){
					var handover = result[i];
					var div = "<div style='margin-top:5px;margin-left:10px;'>" + handover.user;
					if(handover.closed == 0){
						div += ":<span style='color:red;'>";
					}else{
						div += ":<span style='color:green;'>";
					}
					div += handover.content+"</span><span style='float:right;margin-right:5px;'>["+handover.time+"]</span></div>";
					divs += div;
				}
				$("#remarkInfo").html(divs);
			}
		});
	}

	//发送开票提醒
	$scope.sendBillNotice = function(orderSnMain) {
		jConfirm('确认要发送吗？', '发送确认', function(r) {
			if (r) {
				$http.get("/order/sendBillNotice", {
					params : {
						orderSnMain : orderSnMain
					}
				}).success(function(data) {
					jAlert(data.message, '', function() {
						window.location.reload();
					});
				}).error(function(data) {
					jAlert(data.ExceptionMessage);
				});
			}
		});
	}
}
function showSeller(sellerId){
	$.colorbox({
        href: "/order/showSeller?sellerId=" + sellerId,
        iframe: true,
        width: "500px",
        height: "170px",
        top: "100px",
        opacity: 0,
        overlayClose: false,
        scrolling: true
    });
}


