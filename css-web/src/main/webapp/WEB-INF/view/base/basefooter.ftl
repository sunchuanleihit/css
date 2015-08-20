</div>
</body>

        
    <script src="${request.getContextPath()}/assets/scripts/jquery-1.10.2.js"></script>
    <script src="${request.getContextPath()}/assets/scripts/jalert/jquery.ui.draggable.js"></script>
    <script src="${request.getContextPath()}/assets/scripts/jalert/jquery.alerts.js"></script>
    <link href="${request.getContextPath()}/assets/scripts/jalert/jquery.alerts.css" rel="stylesheet"/>
    <script src="${request.getContextPath()}/assets/scripts/angularjs/angular.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/assets/scripts/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/assets/scripts/bootstrap/bootstrap.js"></script>
    <script src="${request.getContextPath()}/assets/scripts/CustomizeAngularjs.js"></script>
    <script src="${request.getContextPath()}/assets/scripts/ColorBox/jquery.colorbox-min.js"></script>
    <link href="${request.getContextPath()}/assets/scripts/ColorBox/colorbox.css" rel="stylesheet"/>
    <script src="${request.getContextPath()}/assets/scripts/My97DatePicker/WdatePicker.js"></script>
 
        <script>
            //详情页作为tab
			function GetDetailTab(id,url,tabTxt){
		        window.parent.addTab(id, url, tabTxt, true);
			}
        </script>
</html>
