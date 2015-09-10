$(function(){
    var uid = $("#buyerId").val();//会员ID
    $("#custom_tabs").tabs({  
        cache : false,
        onSelect:function(){  
            $("#custom_tabs").tabs('getSelected').show();
        }  
    });
    $("#_custom_orders_").datagrid({
        url:"customerOrders",  
        title:"客户订单",
        pagination:true,
        rownumbers:true,
        queryParams:{
			"buyerId":uid
		},
        nowrap:true,
        pageSize:20,
        pageList:[20,40,60],
        columns:[[
			{field:'orderSnMain',title:'订单编号',width:120,sortable:false},
			{field:'cityName',title:'城市',width:120,sortable:false},
			{field:'source',title:'订单来源',width:120,sortable:false},
			{field:'needShiptime',title:'期望送货时间',width:140,sortable:false},
			{field:'status',title:'订单状态',width:80,sortable:false},
			{field:'isDel',title:'是否删除',formatter:yesOrNoFormat,width:80,sortable:false},
			{field:'needInvoice',title:'是否开票',formatter:yesOrNoFormat,width:80,sortable:false},
			{field:'invoiceNo',title:'开票号码',width:80,sortable:false},
			{field:'buyerName',title:'注册用户名',width:80,sortable:false},
			{field:'payNames',title:'付款方式',width:80,sortable:false},
			{field:'orderAmount',title:'订单总额',formatter:doubleFormat,width:80,sortable:false},
			{field:'goodsAmount',title:'商品总额',formatter:doubleFormat,width:80,sortable:false},
			{field:'orderPaid',title:'已付金额',formatter:doubleFormat,width:80,sortable:false},
			{field:'orderNotPaid',title:'未付金额',formatter:doubleFormat,width:80,sortable:false},
			{field:'consignee',title:'收件人姓名',width:80,sortable:false},
			{field:'regionName',title:'地区',width:80,sortable:false},
			{field:'address',title:'详细地址',width:180,sortable:false},
			{field:'phoneMob',title:'电话',width:80,sortable:false},
			{field:'isPrint',title:'是否打单',formatter:yesOrNoFormat,width:80,sortable:false},
			{field:'payStatus',title:'是否付款',formatter:f_pay_status,width:80,sortable:false},
			{field:'finishedTime',title:'完成时间',width:130,sortable:false},
			{field:'shipTime',title:'发货时间',width:130,sortable:false},
			{field:'addTime',title:'订单时间',width:130,sortable:false},
			{field:'postScript',title:'物流提示',width:80,sortable:false},
			{field:'payMessage',title:'订单备注',width:80,sortable:false}
        ]]
    });
    $("#_dummy_acct_").datagrid({
        url:"virtualAccount",
        title:"虚拟账户",
        pagination:true,
        nowrap:true,
        rownumbers:true,
        queryParams:{
			"buyerId":uid
		},
        pageNumber:1,
        pageSize:20,
        pageList:[20,40,60],
        columns:[[
            {field:'orderSnMain',title:'订单号',width:150},
            {field:'buyerName',title:'用户名',width:150},
            {field:'inAmount',title:'存入金额',width:150},
            {field:'outAmount',title:'支出金额',width:150},
            {field:'addTime',title:'变化时间',width:150},
            {field:'note',title:'备注',width:300}
        ]]
    });
    $("#_couponsn_").datagrid({
        url:"couponList",
        title:"优惠券",
        pagination:true,
        rownumbers:true,
        nowrap:true,
        queryParams:{
			"buyerId":uid
		},
        pageNumber:1,
        pageSize:20,
        pageList:[20,40,60],
        columns:[[
            {field:'commonCode',title:'优惠券码',width:130},
            {field:'money',title:'优惠券金额',width:120},
            {field:'couponName',title:'优惠券名称',width:150},
            {field:'couponTypeId',title:'优惠券类型',formatter: couponTypeFormatter,width:100},
            {field:'couponFormId',title:'优惠券形式',formatter:couponFormFormatter,width:100},
            {field:'isSue',title:'券码状态',formatter:isSueFormatter,width:100},
            {field:'isChecked',title:'是否使用',formatter:isCheckedFormatter,width:100},
            {field:'usedTime',title:'使用时间',width:120},
            {field:'orderSnMain',title:'使用订单',width:150},
            {field:'createTime',title:'领用时间',width:120},
            {field:'endTime',title:'结束时间',width:120}
        ]]
    });
    function couponTypeFormatter(num){
    	var map = {0:"满减券",1:"现金券",2:"折扣券",3:"线下券",4:"自提点专用"};
    	return map[num];
    }
    function couponFormFormatter(num){
    	var map = {0:"私有券",1:"公有券",3:"折扣券"};
    	return map[num];
    }
    function isSueFormatter(num){
    	var map = {0:"不启用",1:"启用",2:"停用"};
    	return map[num];
    }
    function isCheckedFormatter(num){
    	var map = {0:"未使用",1:"已使用"};
    	return map[num];
    }
    $("#_txk_").datagrid({
        url:"txkList",
        title:"客户淘心卡",
        queryParams:{
			"buyerId":uid
		},
        rownumbers:true,
        nowrap:true,
        columns:[[
            {field:'cardnum',title:'淘心卡卡号',width:130},
            {field:'amount',title:'面额',width:80},
            {field:'residueAmount',title:'余额',width:80},
            {field:'activeTime',title:'激活时间',width:150},
            {field:'endTime',title:'有效期',width:150}
        ]]
    });
    $("#_txk_details").datagrid({
        url:"txkRecord",
        title:"淘心卡支付明细",
        queryParams:{
			"buyerId":uid
		},
        pagination:true,
        rownumbers:true,
        singleSelect:true,
        pageNumber:1,
        pageSize:20,
        pageList:[20,40,60],
        columns:[[
            {field:'orderCode',title:'订单号',width:130},
            {field:'useTime',title:'消费时间',width:150},
            {field:'cardNum',title:'淘心卡号',width:150},
            {field:'useAmount',title:'金额状态',width:80},
            {field:'currentAmount',title:'淘心卡余额',width:80},
        ]]
    });
});
function yesOrNoFormat(yesOrNo){
	if(yesOrNo){
		return "是";
	}else{
		return "否";
	}
}
function doubleFormat(num){
	return num.toFixed(2);
}
function f_pay_status(vari){
	if(vari==1){
		return "已支付";
	}
	return "未支付";
}
function order_detail(){
	alert(1);
}