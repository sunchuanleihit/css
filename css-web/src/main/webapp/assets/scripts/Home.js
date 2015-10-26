
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

    $scope.logout = function (event) {
        var ctr = this;
        jConfirm("确认退出？", "退出", function (r) {
            if (r) {
                $.get("/dologout").success(function (restr) {
                    window.location.href = "/main";
                }).error(function (data) {
                });
            }
        });
        event.preventDefault();
    }
    $scope.SetPageWidth = function () {
        document.getElementById("containerh").style.width = document.getElementById("main-content").style.width;
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