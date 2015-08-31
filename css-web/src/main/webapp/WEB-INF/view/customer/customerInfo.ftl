<!DOCTYPE html>
<html lang="en">
<head>
	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
</head>
<body>
<style>
	#custom_tabs{margin:8px 15px 15px 15px;}
</style>
<input type="hidden" id="buyerId" value="${buyerId}">
<!--Tabs-->
<div id="custom_tabs" class="easyui-tabs" style="height:600px;">  
    <div title="客户订单" style="padding:20px;">  
        <div id="_custom_orders_"></div>
    </div>  
    <div title="虚拟帐户" data-options="iconCls:'icon-reload',closable:true" style="padding:20px;">  
        <div id="_dummy_acct_"></div>
    </div>  
    <div title="优惠券" data-options="iconCls:'icon-reload',closable:true" style="padding:20px;">  
        <div id="_couponsn_"></div>
    </div>  
    <div title="淘心卡" data-options="iconCls:'icon-reload',closable:true" style="padding:20px;">  
        <div>客户淘心卡</div>
        <div id="_txk_"></div>
        <div>淘心卡支付明细</div>
        <div id="_txk_details"></div>
    </div>  
</div>
</body>
  <script type="text/javascript" src="../../assets/scripts/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../../../assets/viewjs/customerInfo.js"></script>
</html>
