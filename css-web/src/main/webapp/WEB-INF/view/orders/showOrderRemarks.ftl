<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
  <head>
  </head>
  <body>
  	<input type="hidden" id="orderSnMain" value="${orderSnMain}">
  	<div style="padding-top:10px;">
		<input type="radio" name="orderOrHandover" value=0 checked onclick="radioChange()" >备注
		<input type="radio" name="orderOrHandover" value=1 onclick="radioChange()">交接
	</div>
	<div style="height:200px;background-color: #ebebe4;margin-top:5px;margin-bottom:5px;padding-top:10px;overflow:auto;" id="remarkInfo">
		
	</div>
	<div style="text-align:center;">
		<textarea style="width:99%;height:70px;" id="remarkContent"></textarea>
	</div>
	<div><button style="float:right;margin-top:20px;" onclick="addRemark()">提交</button></div>
  </body>
  <script src="<@s.url '/assets/scripts/jquery-1.7.1.min.js' />"></script>
  <script src="<@s.url '/assets/viewjs/showOrderRemarks.js' />"></script>
</html>