function opershow() {
	$("#oper").toggle();
}

//$(document).ready(function(){
//	$("#needShipTime").val("2015-09-21");
//});

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
						jAlert("系统错误");
					}
				});
			}
		});
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
		alert(1);
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
	
	//提交投诉
	$scope.generateComplaint = function() {
		jConfirm('确认要提交吗？', '提交确认', function(r) {
			if (r) {
			$.ajax( {
				type : "POST",
				url : "/order/generateComplaint", 
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
	
	//修改订单期望送货时间
	$scope.changeOrder = function() {
		jConfirm('确认要退款吗？', '退款确认', function(r) {
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
		GetDetailTab("complaintMsg","/order/complaintMsg/" + index, index+"退货");
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
}
