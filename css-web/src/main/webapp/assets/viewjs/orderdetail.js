function opershow() {
	$("#oper").toggle();
}

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
		
	$scope.examine = function(orderId) {
		jConfirm('确认要审核吗？', '审核确认', function(r) {
			if (r) {
				$http.get("/web/purchase/updateState", {
					params : {
						orderId : orderId,
						orderState : OrderState.examine
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

	$scope.close = function(orderId) {
		jConfirm('确认要关闭吗？', '关闭确认', function(r) {
			if (r) {
				$http.get("/web/purchase/updateState", {
					params : {
						orderId : orderId,
						orderState : OrderState.close
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

	$scope.cancel = function(orderId) {
		jConfirm('确认要作废吗？', '作废确认', function(r) {
			if (r) {
				$http.get("/web/purchase/updateState", {
					params : {
						orderId : orderId,
						orderState : OrderState.cancel
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

	$scope.info = function(index) {
		GetDetailTab("stockorderdetail",
				"/web/StockinOrder/StockinOrderDetail/" + index, "采购进货单详情");
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