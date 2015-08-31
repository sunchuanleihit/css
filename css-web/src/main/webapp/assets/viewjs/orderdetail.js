function opershow() {
	$("#oper").toggle();
}

$("#seller_name").live("click",function(){
	alert("aaaa");
	var rs=$(this).parents("tr").find("#goods_name");
	var checked=$(this).attr("checked");
	$.each(rs,function(i,e){
		$(e).attr("checked",!!checked);
	});
});

function OrderDetailController($scope, $http) {
	//获取包裹商品列表
	$scope.getordergoodslist = function(orderId) {
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
		GetDetailTab("returngoods","/order/multiplePaymentRefund/" + index, index+"多付款退款");
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
	$scope.showOrderAction = function(orderSnMain){
		alert(1);
		jQuery("#orderAction").dialog();
	}
}
function showOrderAction(orderSnMain){
	
//	$("#orderAction").dialog({
//		title: "操作详情",
//		href: "orderAction?orderSnMain="+orderSnMain
//	});
}
