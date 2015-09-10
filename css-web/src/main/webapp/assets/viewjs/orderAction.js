function showOrderAction(orderSnMain){
	$("#table").datagrid({
		url:"findOrderAction",  
        queryParams:{
			"orderSnMain":orderSnMain
		},
        nowrap:false,
        columns:[[
			{field:'actionTime',title:'操作时间',width:120,sortable:false},
			{field:'note',title:'操作信息',width:200,sortable:false},
			{field:'actor',title:'操作人',width:140,sortable:false}			
        ]]
	});

}
