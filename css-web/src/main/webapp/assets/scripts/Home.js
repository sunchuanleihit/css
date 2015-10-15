
$(function () {
    var layout = new stone.Layout($('#layout'), {
        fit: true
    });

    tab = new stone.Tab($('#tab'));
});


function HomeController($scope, $http) {
    $http.get('/menu/getMenus', { params: {  parentId: 0} }).success(function (data) {
        $scope.Menus = data;
        $scope.selectedMenuID = data[0].menuId;
        $scope.selectedMenuName = data[0].menuName;
        $scope.selectedMenuNameClass = data[0].remarks;
    }).error(function (data) {
    });

    function InitMenuItem(parentId, parentMenu) {        

        if (parentMenu && parentMenu.Children && parentMenu.Children.length > 0) {
            if (!parentMenu.ParentID) {
                $scope.MenuItems = parentMenu.Children;
            }
            return;
        }
        $http.get('/menu/getMenus', { params: { parentId: parentId} }).success(function (data) {
            parentMenu.Children = data;

            if (parentMenu && !parentMenu.ParentID) {
                $scope.MenuItems = data;
            }
        }).error(function (data) {
        });
    }

    function GetMenuChildren() {
        if ($scope.Menus && $scope.Menus.length > 0) {
            InitMenuItem($scope.Menus[0].menuId, $scope.Menus[0]);
        }
    }
    
    $scope.menuClick = function ($event, index, menu) {
        var id = menu.menuId;
        InitMenuItem(id, menu);
        $scope.selectedMenuID = id;
        $scope.selectedMenuName = menu.menuName;
        $scope.selectedMenuNameClass = menu.remarks;
        $event.preventDefault();
    }

    $scope.menuItemClick = function ($event, index, menu) {
        //alert(menuID);
        $scope.selectedMenuItemID = menu.menuId;
        menu.showChildren = !menu.showChildren;
        $event.preventDefault();
        if (menu.Url == "#") {
            InitMenuItem(menu.menuId, menu);
        } else {
            addTab(menu.menuId, menu.menuUrl, menu.menuName, true);
        }
    }

    $scope.$watch('Menus', GetMenuChildren);

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