<#import "/spring.ftl" as s />
<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta charset="utf-8" />
        <title></title>
        <link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/bootstrap/bootstrap.min.css' />">
        <link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/bootstrap/bootstrap-responsive.min.css' />">
        <link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/default/easyui.css' />">
        <link rel="stylesheet" type="text/css" href="<@s.url '/assets/scripts/jquery.easyui/themes/icon.css' />">
        <meta name="viewport" content="width=device-width" />
        <style>
            button{
        		margin: 10px;
        	}
        	.sq-pg {
        		margin: 30px;
        	}
        	.formcontrol{width: 100%;height: 34px;padding: 6px 12px;font-size: 14px;line-height: 1.42857143;color: #555;background-color: #fff;background-image: none;border: 1px solid #ccc;border-radius: 4px;}
        </style>
    </head>
    <body ng-app="myapp">
    <div style="margin-top:30px;">