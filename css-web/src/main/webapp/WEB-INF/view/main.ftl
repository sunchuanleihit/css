<#import "spring.ftl" as s />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>楼口客服系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/css/cloud-admin.css' />">
	<link rel="stylesheet" type="text/css"  href="<@s.url '/assets/main/css/themes/default.css' />" id="skin-switcher" >
	<link rel="stylesheet" type="text/css"  href="<@s.url '/assets/main/css/responsive.css' />" >
	<!-- STYLESHEETS --><!--[if lt IE 9]><script src="<@s.url '/assets/main/js/flot/excanvas.min.js' />"></script>
	<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
	<link href="<@s.url '/assets/main/font-awesome/css/font-awesome.min.css' />" rel="stylesheet">
	<!-- ANIMATE -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/css/animatecss/animate.min.css' />" />
	<!-- DATE RANGE PICKER -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/bootstrap-daterangepicker/daterangepicker-bs3.css' />" />
	<!-- TODO -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/jquery-todo/css/styles.css' />" />
	<!-- FULL CALENDAR -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/fullcalendar/fullcalendar.min.css' />" />
	<!-- GRITTER -->
	<link rel="stylesheet" type="text/css" href="<@s.url '/assets/main/js/gritter/css/jquery.gritter.css' />" />
    
    <link href="<@s.url '/assets/scripts/bootstrap/bootstrap-responsive.min.css' />" rel="stylesheet" type="text/css"/>
     <link href="<@s.url '/assets/scripts/stone/theme/default/style.css' />" rel="stylesheet" />
     <link href="<@s.url '/assets/scripts/jquery.confirm/jquery.confirm.css' />" rel="stylesheet"/>
    <style type="text/css">
	#gritter-notice-wrapper{display: none;}
	</style>
	<!-- FONTS -->
</head>
<body ng-app="myapp"  ng-controller="HomeController">
	<!-- HEADER -->
	<header class="navbar clearfix" id="header">
		<div class="container" id="gusuqi">
				<div class="navbar-brand">
					<!-- COMPANY LOGO -->
					<a href="/main">
						<img src="<@s.url '/assets/main/img/2_login_logo2.png' />" alt="Cloud Admin Logo" class="img-responsive" height="60" />
					</a>
					<!-- /COMPANY LOGO -->
	 
					<!-- SIDEBAR COLLAPSE -->
					<div id="sidebar-collapse" class="sidebar-collapse btn" ng-click="SetPageWidth()">
						<i class="fa fa-bars" data-icon1="fa fa-bars" data-icon2="fa fa-bars" ></i>
					</div>
				</div>
				
				<!--
				<ul class="nav navbar-nav pull-left">
					<li ng-click="menuClick($event,$index,menu)" ng-class=" {'dropdown open gusuqi':true,'dropdown open gusuqi active':selectedMenuID==menu.menuId}" ng-repeat-start="menu in Menus">
						<a href="#" class="dropdown-toggle">
							<i class="{{menu.remarks}}"></i>
							<span class="name">{{menu.menuName}}</span>
						</a>
					</li>
                    <span ng-repeat-end ng-show>&nbsp;</span>
				</ul>
				-->

				<!-- BEGIN TOP NAVIGATION MENU -->					
				<ul class="nav navbar-nav pull-right">
				<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user pull-right" id="header-user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<img alt="" src="<@s.url '/assets/main/img/avatars/default.png' />" />
							<span class="username">{{OperatorName}}</span>
							<i class="fa fa-angle-down"></i>
						</a>
                        <ul class="dropdown-menu">
                            <li><a href=""  ng-click="ModifyUser()"><i class="fa fa-pencil"></i> 修改信息</a></li>
                            <li><a href="/login" ng-click="logout($event)"><i class="fa fa-power-off"></i> 注销</a></li>
                        </ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->					
				</ul>
				<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- TEAM STATUS -->
		
		<!-- /TEAM STATUS -->
	</header>
	<!--/HEADER -->
	
	<!-- PAGE -->
	<section id="page" height="100%">
				<!-- SIDEBAR -->
				<div id="sidebar" class="sidebar">
                    <div class="sidebar-menu nav-collapse">
                        <!-- SIDEBAR MENU -->
                        <ul>
                            <li class="has-sub open">
                                <a href="javascript:;" class="">
                                    <i class="{{selectedMenuNameClass}}"></i> <span class="menu-text">{{selectedMenuName}}</span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub" style="display:block">
                                    <li ng-click="menuItemClick($event,$index,menuitem)" ng-href="{{menuitem.menuUrl}}" ng-class="{on:menuitem.menuId==selectedMenuItemID}" target="right" ng-repeat-start="menuitem in MenuItems">
                                        <a class="" href="#">
                                            <span class="sub-menu-text">{{menuitem.menuName}}</span>
                                        </a>
                                    </li><span ng-repeat-end ng-show>&nbsp;</span>
                                </ul>
                            </li> 
                        </ul>
                        <!-- /SIDEBAR MENU -->
                    </div>
				</div>
				<!-- /SIDEBAR -->
		<div id="main-content" style="height: 100%">
			<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
			
			<!-- /SAMPLE BOX CONFIGURATION MODAL FORM-->
            <div class="container" id="containerh"  style="border-left: 1px solid #CDD2D2; height: 90%;" >
                <div class="box border green"style="margin-top: 10px; height: 97%; width: 100%;">
                    
                    <div id="layout">
                        <div region='center'>
                            <div id="tab"  fit="true" >
                              
                            </div>   
                        </div>   
                    </div>
                </div>
                <!--<div class="footer-tools">
                    <span class="go-top">
                        <i class="fa fa-chevron-up"></i> 回到顶部
                    </span>
                </div>-->
			</div>
		</div>
	</section>
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY -->
	<script src="<@s.url '/assets/main/js/jquery/jquery-2.0.3.min.js' />"></script>
	<!-- JQUERY UI-->
	<script src="<@s.url '/assets/main/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js' />"></script>
	<!-- BOOTSTRAP -->
	<script src="<@s.url '/assets/main/bootstrap-dist/js/bootstrap.min.js' />"></script>
	<!-- DATE RANGE PICKER -->
	<script src="<@s.url '/assets/main/js/bootstrap-daterangepicker/moment.min.js' />"></script>
	<script src="<@s.url '/assets/main/js/bootstrap-daterangepicker/daterangepicker.min.js' />"></script>
	<!-- SLIMSCROLL -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js' />"></script>
	<!-- SLIMSCROLL -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js' />"></script><script type="text/javascript" src="<@s.url '/assets/main/js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js' />"></script>
	<!-- BLOCK UI -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/jQuery-BlockUI/jquery.blockUI.min.js' />"></script>
	<!-- SPARKLINES -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/sparklines/jquery.sparkline.min.js' />"></script>
	<!-- EASY PIE CHART -->
	<script src="<@s.url '/assets/main/js/jquery-easing/jquery.easing.min.js' />"></script>
	<script type="text/javascript" src="<@s.url '/assets/main/js/easypiechart/jquery.easypiechart.min.js' />"></script>
	<!-- FLOT CHARTS -->
	<script src="<@s.url '/assets/main/js/flot/jquery.flot.min.js' />"></script>
	<script src="<@s.url '/assets/main/js/flot/jquery.flot.time.min.js' />"></script>
    <script src="<@s.url '/assets/main/js/flot/jquery.flot.selection.min.js' />"></script>
	<script src="<@s.url '/assets/main/js/flot/jquery.flot.resize.min.js' />"></script>
    <script src="<@s.url '/assets/main/js/flot/jquery.flot.pie.min.js' />"></script>
    <script src="<@s.url '/assets/main/js/flot/jquery.flot.stack.min.js' />"></script>
    <script src="<@s.url '/assets/main/js/flot/jquery.flot.crosshair.min.js' />"></script>
	<!-- TODO -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/jquery-todo/js/paddystodolist.js' />"></script>
	<!-- TIMEAGO -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/timeago/jquery.timeago.min.js' />"></script>
	<!-- FULL CALENDAR -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/fullcalendar/fullcalendar.min.js' />"></script>
	<!-- COOKIE -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/jQuery-Cookie/jquery.cookie.min.js' />"></script>
	<!-- GRITTER -->
	<script type="text/javascript" src="<@s.url '/assets/main/js/gritter/js/jquery.gritter.min.js' />"></script>
	<script src="<@s.url '/assets/main/js/script.js' />"></script>
	<script>
	    jQuery(document).ready(function () {
	        //App.setPage("index");  //Set current page
	        App.init(); //Initialise plugins and elements
	    });
	</script>
    <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
    <script src="<@s.url '/assets/scripts/jalert/jquery.ui.draggable.js' />"></script>
    <script src="<@s.url '/assets/scripts/jalert/jquery.alerts.js' />"></script>
    <link href="<@s.url '/assets/scripts/jalert/jquery.alerts.css' />" rel="stylesheet"/>
    <script src="<@s.url '/assets/scripts/jquery.confirm/jquery.confirm.js' />"></script>
    <script type="text/javascript">
        var app = angular.module("myapp", []);
        var CurrentUser={"UserId":"${userId}","UserName":"${userName}"};
        document.getElementById("containerh").style.height = window.screen.availHeight*0.89 + 'px';
        </script>
     
        <script src="<@s.url '/assets/scripts/Home.js' />"></script>
        <script src="<@s.url '/assets/scripts/stone/core.js' />"></script>
        <script src="<@s.url '/assets/scripts/stone/resizable.js' />"></script>
        <script src="<@s.url '/assets/scripts/stone/panel.js' />"></script>
        <script src="<@s.url '/assets/scripts/stone/layout.js' />"></script>
        <script src="<@s.url '/assets/scripts/stone/tab.js' />"></script>
        <script src="<@s.url '/assets/scripts/ColorBox/jquery.colorbox-min.js' />"></script>
        <link href="<@s.url '/assets/scripts/ColorBox/colorbox.css' />" rel="stylesheet"/>
        <!-- CUSTOM SCRIPT -->
    <!-- /JAVASCRIPTS -->
</body>
</html>