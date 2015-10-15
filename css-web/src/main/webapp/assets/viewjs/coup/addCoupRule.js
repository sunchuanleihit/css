Date.prototype.format = function (format) {
   var args = {
       "M+": this.getMonth() + 1,
       "d+": this.getDate(),
       "h+": this.getHours(),
       "m+": this.getMinutes(),
       "s+": this.getSeconds(),
       "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
       "S": this.getMilliseconds()
   };
   if (/(y+)/.test(format)){
	   format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   }
   for (var i in args) {
       var n = args[i];
       if (new RegExp("(" + i + ")").test(format))
           format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
   }
   return format;
};
function useTimeTypeChange(){
	var canuseType = $("#canuseType").val();
	if(canuseType == "1"){
		validDay();
	}else if(canuseType == "0"){
		beginEnd();
	}
}
function sharedCoup(){
	$("#commoncodeTr").show();
	$("#prefixTr").hide();
}
function privateCoup(){
	$("#commoncodeTr").hide();
	$("#prefixTr").show();
}
function beginEnd(){
	$("#begintimeTr").show();
	$("#endtimeTr").show();
	$("#canusedayTr").hide();
}
function validDay(){
	$("#begintimeTr").hide();
	$("#endtimeTr").hide();
	$("#canusedayTr").show();
}
function searchItems(){
	var scope = $("#couponType").val();
	var searchContent = $("#searchParam").val();
	if(scope == 1){
		$.ajax({
			url:"/coupon/searchStore",
			type:"POST",
			data:{"param":searchContent},
			success:function(resultList){
				$("#searchSelects").html("");
				if(!resultList || resultList.length==0){
					return;
				}
				var options = "<option value=''>请选择店铺</option>";
				for(var i=0; i<resultList.length; i++){
					options += "<option value='"+resultList[i].storeId+"'>"+resultList[i].storeName+"</option>";
				}
				$("#searchSelects").html(options);
			}
		});
	}
}
function addCoupRuleCtrl($scope, $http){
	$scope.coupTypes = coupTypeArr;
	if($scope.coupTypes[0]){
		var typeId = $scope.coupTypes[0].typeId;//公有券、私有券、折扣券
		if(typeId == 2){//是公有券
			sharedCoup();
		}else{
			privateCoup();
		}
	}
	$scope.coupType = $scope.coupTypes[0];
	$scope.coupTypeChange = function(){
		if($scope.coupType){
			var typeId = $scope.coupType.typeId;//公有券、私有券、折扣券
			if(typeId == 2){//是公有券
				sharedCoup();
			}else{
				privateCoup();
			}
		}
	}
	$scope.addCoupRule = function(){
		var intPattern = /^\d+$/;
		var floatPattern = /^[0-9]+\.{0,1}[0-9]*$/;
		var typeId = "";
		if($scope.coupType){
			typeId = $scope.coupType.id;
		}else{
			alert("请选择优惠券类别");
			return;
		}
		var couponName = $("#couponName").val();
		var commoncode = $("#commoncode").val();
		var prefix = $("#prefix").val();
		var maxnum = $("#maxnum").val();
		var canuseType = $("#canuseType").val();
		var canuseday = $("#canuseday").val();
		var begintime = $("#begintime").datepicker("getDate");
		var endtime = $("#endtime").datepicker("getDate");
		var coupontypeid = $("#coupontypeid").val();
		var lowemoney = $("#lowemoney").val();
		var money = $("#money").val();
		var couponType = $("#couponType").val();
		if(!typeId){
			alert("请选择优惠券类别");
			return;
		}
		if(!couponName || !couponName.trim()){
			alert("请选择优惠券名称");
			$("#couponName").focus();
			return;
		}
		couponName = couponName.trim();
		if(!maxnum || !maxnum.trim()){
			alert("请输入最大发放量");
			$("#maxnum").focus();
			return;
		}
		if(!maxnum.match(intPattern)){
			alert("最大发放量请输入整数");
			$("#maxnum").focus();
			return;
		}
		maxnum = maxnum.trim();
		if(!money || !money.trim()){
			alert("请输入优惠金额");
			$("#money").focus();
			return;
		}
		money = money.trim();
		if(!money.match(floatPattern)){
			alert("优惠金额请输入正确数值");
			$("#money").focus();
			return;
		}
		money = parseFloat(money,10);
		var params = {
			"typeId":typeId,
			"couponName":couponName,
			"maxnum":maxnum,
			"coupontypeid":coupontypeid,
			"canuseType":canuseType,
			"money":money,
			"couponType":couponType
		}
		if(coupontypeid == 0){//如果是满减券
			if(!lowemoney || !lowemoney.trim()){
				alert("请输入最低消费金额");
				$("#lowemoney").focus();
				return;
			}
			lowemoney = lowemoney.trim();
			if(!lowemoney.match(floatPattern)){
				alert("最低消费金额请输入正确数值");
				$("#lowemoney").focus();
				return;
			}
			lowemoney = parseFloat(lowemoney,10);
			if(money>lowemoney){
				alert("优惠金额不能大于最低消费金额");
				$("#money").focus();
				return;
			}
			params['lowemoney'] = lowemoney;
		}
		//如果是分类券则获取分类ID
		if(couponType == "4"){
			var checkboxs = $("input[name='category']");
			if(!checkboxs || checkboxs.length==0){
				alert("缺少分类");
				return;
			}
			var category = "";
			for(var i=0; i<checkboxs.length; i++){
				var id = $(checkboxs[i]).val();
				category += ","+id;
			}
			category = category.substring(1);
			params['category'] = category;
		}
		if($scope.coupType.typeId == 2){
			if(!commoncode || !commoncode.trim()) {
				alert("请输入公用券券码");
				$("#prefix").focus();
				return;
			}
			commoncode = commoncode.trim();
			if(!commoncode.match(/^[A-Z]{4,}[a-zA-Z0-9]{0,14}$/) || commoncode.length>18){
				alert("公用券券码长度必须限制在4至18位,必须以4位以上大写字母开头");
				return;
			}
			params['commoncode'] = commoncode;
		}else{
			if(!prefix || !prefix.trim()) {
				alert("请输入优惠码前缀");
				$("#prefix").focus();
				return;
			}
			prefix = prefix.trim();
			if(prefix.indexOf("LK")==0 || !prefix.match(/^[A-Z]{2,6}$/)){
				alert("优惠券前缀为2-6位大写字母，不能以LK开头");
				$("#prefix").focus();
				return;
			}
			params['prefix'] = prefix;
		}
		if(canuseType == "1"){
			if(!canuseday || !canuseday.trim()){
				alert("请输入有效天数");
				$("#canuseday").focus();
				return;
			}
			canuseday = canuseday.trim();
			if(!canuseday.match(intPattern)){
				alert("有效天数请输入正整数");
				$("#canuseday").focus();
				return;
			}
			params['canuseday'] = canuseday;
		}else if(canuseType == "0"){
			if(!begintime){
				alert("请输入开始时间");
				return;
			}
			begintime = begintime.format("yyyy-MM-dd") + " 00:00:00";
			params['begintime'] = begintime;
			if(!endtime){
				alert("请输入结束时间");
				return;
			}
			endtime = endtime.format("yyyy-MM-dd") + " 23:59:59";
			params['endtime'] = endtime;
			if(begintime > endtime){
				alert("开始时间不能大于结束时间");
				return;
			}
		}
		
		$.ajax({
			url:"/coupon/addCoupRule",
			data:params,
			type:"POST",
			success:function(result){
				if(result['code']=="success"){
					alert("录入成功");
					window.parent.closeTab("addCoupRule");
				}else{
					alert(result['code']);
				}
			}
		});
	}
}

$(function(){
	$("#begintime").datepicker({
		language : "zh-CN",
	    autoclose : true,
	    todayHighlight : true,
		format:"yyyy-mm-dd",
		todayBtn:"linked"
	});
	$("#endtime").datepicker({
		language : "zh-CN",
	    autoclose : true,
	    todayHighlight : true,
		format:"yyyy-mm-dd",
		todayBtn:"linked"
	});
	validDay();
	showCategory1();
});

function showCategory1(){
	$("#category1").html('');
	$.ajax({
		url:"/coupon/getChildCategories",
		data:{
			"id":0
		},
		success:function(list){
			var options = '<option value="">--请选择--</option>';
			if(list && list.length>0){
				for(var i=0; i<list.length; i++){
					options += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
				}
				$("#category1").html(options);
			}
		}
	});
}
function category1Change(){
	$("#category2").html('');
	var category1 = $("#category1").val();
	if(category1){
		var params = {
			"id":category1
		};
		$.ajax({
			url:"/coupon/getChildCategories",
			data:params,
			type:"POST",
			success:function(list){
				if(list && list.length>0){
					var options = '<option value="">--请选择--</option>';
					for(var i=0; i<list.length; i++){
						options += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
					}
					$("#category2").html(options);
				}
			}
		});
	}
}

function category2Change(){
	$("#category3").html('');
	var category2 = $("#category2").val();
	if(category2){
		var params = {
			"id":category2
		};
		$.ajax({
			url:"/coupon/getChildCategories",
			data:params,
			type:"POST",
			success:function(list){
				if(list && list.length>0){
					var options = '<option value="">--请选择--</option>';
					for(var i=0; i<list.length; i++){
						options += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
					}
					$("#category3").html(options);
				}
			}
		});
	}
}

function useScopeChange(){
	var scope = $("#couponType").val();
	if(scope == 0){
		$(".category").hide();
	}if(scope == 4){
		$(".category").show();
	}
}

function addCategory(){
	var category = "";
	var categoryName = "";
	var category3 = $("#category3").val();
	var category2 = $("#category2").val();
	var category1 = $("#category1").val();
	if(category3){
		category = category3;
		var select = document.getElementById("category3");
		categoryName = select.options[select.selectedIndex].text;
	}else if(category2){
		category = category2;
		var select = document.getElementById("category2");
		categoryName = select.options[select.selectedIndex].text;
	}else if(category1){
		category = category1;
		var select = document.getElementById("category1");
		categoryName = select.options[select.selectedIndex].text;
	}else{
		alert("未选择分类");
		return;
	}
	var checkbox = '<span style="margin-right:5px;"><input type="checkbox" name="category" checked onclick="removeCheck(this)" value="'+category+'">'+categoryName+'</span>';
	$("#categories").append(checkbox);
}
function removeCheck(dom){
	$(dom).parent().remove();
}
function coupontypeidChange(){
	var coupontypeid = $("#coupontypeid").val();
	if(coupontypeid == 1){
		$("#lowemoneyTr").hide();
	}else{
		$("#lowemoneyTr").show();
	}
}
