$(function(){
	initTable();
});
function initTable(){
	function operateFormatter(value, row, index) {
		var str = '';
		str += '<a href="javascript:void(0);" title="getDescription" class="getDescription">查看描述</a>';
		var title = row.title;
		if(title == "客服专用"){
			str += ' | <a href="javascript:void(0);" title="creatRule" class="creatRule">增加规则</a>';
		}
	    return str;
	}

	window.operateEvents = {
	    'click .creatRule': function (e, value, row, index) {
	   		 var id = row.id;
	   		 window.parent.addTab("addCoupRule", "/coupon/addCoupRulePage?typeId="+id, "新增优惠券规则", true);
	    },
	    'click .getDescription': function (e, value, row, index) {
	    	art.dialog({
	    		title:row.title+'的规则',
	    		content:row.description
	    	});
	    }
	};
	
	$('#table').bootstrapTable({
		url: '/coupon/findType',
		pagination: true, 
		sidePagination:'server',//设置为服务器端分页
		cache: false,
		pageList: [10,20],
	    columns: [{
	        field: 'title',
	        title: '名称'
	    },{
	        field: 'typeid',
	        title: '类型',
	        formatter:typeIdFormatter
	    },{
	        field: 'usenum',
	        title: '互斥次数'
	    },{
	        field: 'newuser',
	        title: '会员限制',
	        formatter:userFormatter
	    },{
	        field: 'operate',
	        title: '操作',
	        align: 'center',
	        events: operateEvents,
        	formatter: operateFormatter
	     }]
	});
}
function typeIdFormatter(id){
	if(id == 1){
		return "私有券";
	}else if(id == 2){
		return "公有券";
	}else if(id == 3){
		return "折扣券";
	}
}
function userFormatter(user){
	if(user==0){
		return "不限";
	}else if(user ==1){
		return "新会员"
	}else if(user ==2){
		return "老会员";
	}
}
function show(title,msg,sell_site){
	art.dialog({
		title:title+'的规则('+sell_site+')',
		content:msg
	})
}
function addCoupType(){
	window.parent.addTab("addCoupType", "/coupon/addType", "新增优惠券类型", true);
}