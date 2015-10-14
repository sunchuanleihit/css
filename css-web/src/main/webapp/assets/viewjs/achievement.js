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

$(function(){
	initDatePicker();
	initTable();
});
function initDatePicker(){
	$("#startDate").datepicker({
		language : "zh-CN",
	    autoclose : true,
	    todayHighlight : true,
		format:"yyyy-mm-dd",
		todayBtn:"linked"
	});
	$("#endDate").datepicker({
		language : "zh-CN",
	    autoclose : true,
	    todayHighlight : true,
		format:"yyyy-mm-dd",
		todayBtn:"linked"
	});
}
function initTable(){
	$('#table').bootstrapTable({
		url: '/achievement/caculate',
		queryParams:queryParams,
	    columns: [{
	        field: 'name',
	        title: '姓名'
	    },{
	        field: 'changeOrderNum',
	        title: '改单数'
	    },{
	    	field: 'cancelOrderNum',
	    	title: '作废单数'
	    },{
	        field: 'returnGoodsNum',
	        title: '退货单数'
	    },{
	        field: 'returnMoneyNum',
	        title: '退款单数'
	    },{
	        field: 'complaintNum',
	        title: '统计投诉数'
	    }]
	});
}

function caculateAchievement(){
	var startDate = $("#startDate").datepicker("getDate");
	var endDate = $("#endDate").datepicker("getDate");
	if(!startDate || !endDate){
		alert("请选择开始时间和结束时间");
		return false;
	}
	var dayReduction = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
	if(dayReduction < 0){
		alert("开始时间不能大于结束时间");
		return false;
	}
	if(dayReduction > 31){
		alert("时间间隔不允许超过一个月");
		return false;
	}
	$('#table').bootstrapTable("refresh");
}

function queryParams(){
	var params = {};
	var startDate = $("#startDate").datepicker("getDate");
	var endDate = $("#endDate").datepicker("getDate");
	if(startDate){
		startDate = startDate.format("yyyy-MM-dd");
		params['startDate'] = startDate;
	}
	if(endDate){
		endDate = endDate.format("yyyy-MM-dd");
		params['endDate'] = endDate;
	}
	return params;
}