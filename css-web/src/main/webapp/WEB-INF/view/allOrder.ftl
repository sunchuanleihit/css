<#import "spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="../../assets/scripts/jquery.easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/scripts/jquery.easyui/themes/icon.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
		<div id="panel" style="padding:20px;border:1px solid #dadada;">
			<form id="search_form">
				<table>
                	<tr>
						<td>订单号</td><td><input type="text" name="order_sn" id="order_sn"/></td>
						<td>收货人</td><td><input type="text" name="consignee" id="consignee"/></td>
						<td>单据日期</td><td><input type="text" id="start_time" name="start_time"/>--</td><td><input type="text" id="end_time" name="end_time"/></td>
						<td>订单状态</td>
                        <td>
                        	<select id="status" name="status" style="100px;">  
								<option value="">订单状态</option>
								<option value="0">初始状态</option>
								<option value="1">取消</option>
								<option value="2">无效</option>
								<option value="3">已审核</option>
								<option value="5">已提货</option>
								<option value="6">已拣货</option>
								<option value="8">打包</option>
								<option value="13">已发货</option>
								<option value="14">已发货</option>
								<option value="15">回单</option>
								<option value="16">拒收</option>
								<option value="30">未支付</option>
								<option value="31">已支付</option>
                              </select> 
                          </td>
                      </tr>
                      <tr>
						  <td>查询类型</td>
						  <td>
						  	<select name="query_type" id="query_type" style="100px;">  
								<option value="address ">收货地址</option>
								<option value="phone_mob">收货人手机</option>
								<option value="phone_tel">收货人电话</option>
								<option value="ship_time">发货时间</option>
								<option value="postscript">订单备注</option>
								<option value="buyer_name">会员用户名</option>
								<option value="use_coupon_no">优惠券码</option>
								<option value="goods_name">商品名字</option>
								<option value="store_name">商家名称</option>
								<option value="city_name">城市</option>
							</select>
						</td>
					    <td colspan="7"><input type="text" name="query_content" id="query_content"/></td>
                  	</tr>
				</table>
				<p style="border-top:1px solid #dadada;text-align:center;padding-top:5px;">
					<input type="button" id="search_submit" value="查询" class="formbtn"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" class="formbtn"/>
				</p>
			</form>
		</div>
		<div style="height:5px;"></div>
        <div id="table"></div>
    </div>
  </body>
  <script type="text/javascript" src="../../assets/scripts/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../assets/scripts/jquery.easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../../assets/scripts/allOrder.js"></script>
</html>
