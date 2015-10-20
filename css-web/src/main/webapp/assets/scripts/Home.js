
$(function () {
    var layout = new stone.Layout($('#layout'), {
        fit: true
    });
    tab = new stone.Tab($('#tab'));
});

function HomeController($scope, $http) {
    $http.get('/menu/getAllMenus').success(function (data) {
    	for(var i=0; i<data.length; i++){
    		data[i].open = true;
    	}
        $scope.Menus = data;
    });

    $scope.menuParentClick = function ($event, index, menu) {
    	menu.open = !menu.open;
        $event.preventDefault();
    }

    $scope.menuItemClick = function ($event, index, menu) {
        $event.preventDefault();
        addTab(menu.menuId, menu.menuUrl, menu.menuName, true);
    }
    $scope.OperatorWareHouse = top.CurrentUser.WareHouseName;
    $scope.OperatorName = top.CurrentUser.UserName;

    $scope.logout = function (event) {
        var ctr = this;
        jConfirm("确认退出？", "退出", function (r) {
            if (r) {
                $.get("/login/logout").success(function (restr) {
                    window.location.href = "/login";
                }).error(function (data) {
                });
            }
        });
        event.preventDefault();
    }
    $scope.SetPageWidth = function () {
        document.getElementById("containerh").style.width = document.getElementById("main-content").style.width;
    };
    //修改密码
    $scope.ModifyUser = function () {
        $.colorbox({
            href: "/user/updateUser?userId="+top.CurrentUser.UserId+"&userName="+top.CurrentUser.UserName,
            iframe: true,
            width: "480px",
            height: "400px",
            top: "100px",
            opacity: 0,
            overlayClose: false,
            scrolling: false,
            onClosed: function () {
            	window.location.href = "/main";
            }
        });
    };
}

var tab;
function addTab(code, url, text, update) {
    if (tab.exists(code)) {
        tab.closeTab(code);
    }
    tab.addTab({ tabId: code, title: text, href: url });
}
function closeTab(tabId){
	tab._closeTab(tabId);
}