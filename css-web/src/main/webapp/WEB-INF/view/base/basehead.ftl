<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta charset="utf-8" />
        <title></title>
        <link href="${request.getContextPath()}/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${request.getContextPath()}/assets/scripts/bootstrap/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
        <meta name="viewport" content="width=device-width" />
        <script type="text/javascript">
            document.onkeydown = function() {
                // 如果按下的是退格键
                if (event.keyCode == 8) {
                    // 如果是在textarea内不执行任何操作
                    if (event.srcElement.tagName.toLowerCase() != "input" && event.srcElement.tagName.toLowerCase() != "textarea" && event.srcElement.tagName.toLowerCase() != "password")
                        event.returnValue = false;
                    // 如果是readOnly或者disable不执行任何操作
                    if (event.srcElement.readOnly == true || event.srcElement.disabled == true)
                        event.returnValue = false;
                }
            };
            function onKeyDown() {
                if ((event.altKey) || ((event.keyCode == 8) &&
                    (event.srcElement.type != "text" &&
                        event.srcElement.type != "textarea" &&
                            event.srcElement.type != "password")) ||
                                ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82))) ||
                                    (event.keyCode == 116)) {
                    event.keyCode = 0;
                    event.returnValue = false;
                }
            };
            function GetQueryString(sProp) {
			    var re = new RegExp("[&,?]" + sProp + "=([^//&]*)", "i");
			    var a = re.exec(window.location.search);
			    if (a == null) {
			        return "";
			    }
			    else {
			        return decodeURIComponent(a[1]);
			    }
			}
			
        </script>
        
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