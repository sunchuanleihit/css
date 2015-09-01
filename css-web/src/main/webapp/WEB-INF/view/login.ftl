<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>楼口进出存系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- STYLESHEETS -->
	<!--[if lt IE 9]>
		<script src="js/flot/excanvas.min.js"></script>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/css/cloud-admin.css' />">
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/font-awesome/css/font-awesome.min.css' />">
	<!-- DATE RANGE PICKER -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/bootstrap-daterangepicker/daterangepicker-bs3.css' />">
	<!-- UNIFORM -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/uniform/css/uniform.default.min.css' />">
	<!-- ANIMATE -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/css/animatecss/animate.min.css' />">
	<!-- FONTS -->
	
    <script type="text/javascript" src="<@s.url '/assets/scripts/myValidate.js' />"></script>
    <script type="text/javascript" src="<@s.url '/assets/scripts/jquery-1.10.2.js' />"></script>
    <script type="text/javascript" src="<@s.url '/assets/scripts/jalert/jquery.alerts.js' />"></script>
    <script type="text/javascript" src="<@s.url '/assets/scripts/jalert/jquery.ui.draggable.js' />"></script>
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jalert/jquery.alerts.css' />">
    <script type="text/javascript">
        //ie浏览器，并且版本低于ie9
        function IsLessThanIE9() {
            var ua = navigator.userAgent.toLowerCase();
            if (window.ActiveXObject) {
                if (ua.match(/msie ([\d.]+)/)[1] < 9) {
                    return true;
                }
            }
            return false;
        }
        
        function Login() {
            if (IsLessThanIE9()) {
                jAlert('您的浏览器版本过低,请使用IE9以上的版本!');
                return;
            }
            doCheck();
        }
        if (IsLessThanIE9()) {
            //document.getElementById("browserVersionMsg").style.display = "block";
        }
        function doCheck() {
            //if (IsFieldEmpty("username", "系统提示:\r\n用户名不能为空，请输入用户名.") == false) {
            //    return;
            //}
            //if (IsFieldEmpty("userpassword", "系统提示:\r\n密码不能为空，请输入密码.") == false) {
            //    return;
            //}
            var userCode = $("#username").val();
            var userPwd = $("#userpassword").val();
            if(userCode == "")
            {userCode = "1001";}
            if(userPwd == ""){
             userPwd = "123";
            }
           
            $.post("/login/checklogin", { userCode:userCode,userPwd:userPwd }).success(function (data) {
                if (data.code == "200") {
                    top.window.location.href = "/main";
                } else {
                    jAlert(data.message, "提示");
                }

            }).error(function (data, msg, detail) {
                jAlert(data.responseJSON.ExceptionMessage, "错误", function () { $("#txtPwd").focus() });
            });
            //return false;
        }
   </script>
</head>
<body class="login">	
	<!-- PAGE -->
	<section id="page">
			<!-- HEADER -->
			<header>
				<!-- NAV-BAR -->
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="logo">
								<a href="#">
									<img src="<@s.url '/assets/main/img/2_login_logo.png' />" alt="logo name" />
								</a>
							</div>
						</div>
					</div>
				</div>
				<!--/NAV-BAR -->
			</header>
			<!--/HEADER -->
			<!-- LOGIN -->
			<section id="login" class="visible">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">登录</h2>
								<div class="divide-40"></div>
									<div class="form-group">
								  </div>
								  <div class="form-group">
									<label for="exampleInputEmail1">用户名</label>
									<i class="fa fa-user"></i>
									<input type="text" class="form-control"  id="username" name="username" >
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword1">密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" class="form-control" id="userpassword" name="userpassword" >
								  </div>
								  <div class="form-actions">
									<label class="checkbox"> <div class="checker" id="uniform-Rem"><span><input type="checkbox" name="Rem" id="Rem" class="uniform"></span></div> 记住密码</label>
									<button onclick="Login()" class="btn btn-primary">登录</button>
								  </div>
								
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/LOGIN -->
	</section>
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY -->
	<!-- BOOTSTRAP -->
	<script type="text/javascript" src="<@s.url '/assets/main/bootstrap-dist/js/bootstrap.min.js' />"></script>
	<!-- UNIFORM -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/uniform/jquery.uniform.min.js' />"></script>
	<!-- CUSTOM SCRIPT -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/script.js' />"></script>
	<script>
	    jQuery(document).ready(function () {
	        App.setPage("login");  //Set current page
	        App.init(); //Initialise plugins and elements
	    });
	</script>
	<script type="text/javascript">
	    function swapScreen(id) {
	        jQuery('.visible').removeClass('visible animated fadeInUp');
	        jQuery('#' + id).addClass('visible animated fadeInUp');
	    }
	</script>
</body>
</html>