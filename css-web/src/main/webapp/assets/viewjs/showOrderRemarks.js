$(function(){
		reloadRemarkInfo();
});
var type = 0;
function addRemark(){
	var orderSnMain = $("#orderSnMain").val();
	var content = $("#remarkContent").val();
	var param = {
		"orderSnMain":orderSnMain,
		"content":content,
		"type":type
	}
	$.ajax({
		url:"/order/addOrderRemark",
		type:"post",
		data:param,
		success: function(result){
			if(result == "login"){
				alert("请重新登陆");
			}else if(result == "error"){
				alert("参数错误");
			}else if(result == "success"){
				$("#remarkContent").val("");
				reloadRemarkInfo();
			}
		}
	});
}

function radioChange(){
	var ntype = $("input[name='orderOrHandover']:checked").val();
	if(type != ntype){
		type = ntype;
		reloadRemarkInfo();
	}
}

function reloadRemarkInfo(){
	$("#remarkInfo").html("");
	var orderSnMain = $("#orderSnMain").val();
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