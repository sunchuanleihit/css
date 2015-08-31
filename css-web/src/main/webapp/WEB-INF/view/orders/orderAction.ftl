<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
  </head>
  <div id="table"></div>
  </body>
  <script type="text/javascript" src="../../assets/scripts/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../../assets/viewjs/orderAction.js"></script>
  <script>
  	$(function(){
  		showOrderAction("${orderSnMain}");
  	});
  </script>
</html>