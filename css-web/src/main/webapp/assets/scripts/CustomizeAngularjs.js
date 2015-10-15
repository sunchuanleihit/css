
var app = angular.module("myapp", []);

//布尔值显示是否
app.filter("booltext", function () {
    return function (inputValue) {
        return inputValue ? "是" : "否";
    }
});

//输入时正则表达式校验指令
app.directive("inputPattern", function () {
    return {
        restrict: "A",
        require: "?ngModel",
        scope: {
            pattern: "@inputPattern"
        },
        link: function (scope, ielement, iattr, ngModelCtr) {
            ielement.keydown(function ($event) {
                if ($event.keyCode == 13) {
                    if (ngModelCtr) {
                        ngModelCtr.$setViewValue($(this).val());
                    }
                    
                }
            });
        }
    };
});

//扫描框指令
app.directive("scanInput", function () {
    return {
        restrict: "A",
        require: "?ngModel",
        scope: {
            scanfn: "&scanInput"
        },
        link: function (scope, ielement, iattr, ngModelCtr) {
            ielement.keydown(function ($event) {
                if ($event.keyCode == 13) {
                    if (ngModelCtr) {
                        ngModelCtr.$setViewValue($(this).val());
                    }
                    scope.scanfn();
                    return false;
                }
            });
        }
    };
});

//长按事件
app.directive("longPress", function () {
    return {
        restrict: "A",
        scope: {
            longPress: "&"
        },
        link: function (scope, iElement, iAttrs) {
            var timer;
            var count = 0;
            $(iElement).mousedown(function () {
                timer = setTimeout(function () {
                    count++;
                    if (count > 0) {
                        count = 0;
                        clearTimeout(timer);
                        scope.longPress();
                    }
                }, 1000);
            })
            .mouseup(function () {
                count = 0;
                clearTimeout(timer);
            });
        }
    }
});

//获得焦点
app.directive("getFocus", function () {
    return {
        restrict: "A",
        scope: {
            focusField: "=getFocus"
        },
        link: function (scope, element, attrs) {
            scope.$watch("focusField", function () {
                $(scope.focusField).focus();
                scope.focusField = null;
            });

        }
    }
});

$(function () {
    var firstClickTime = new Date();
    var clickCount = 0;
    $("body").click(function ($event) {
        if ($event.target.nodeName == "A") {
            if ((new Date() - firstClickTime) < 500) {
                alert("系统太累了，想要歇歇了，放过他吧！三秒后再点！");
            }
            firstClickTime = new Date();
        }
    });
});

function addTab(code, url, text) {
    top.addTab(code, url, text );
}
function closeTab(code){
	top.closeTab(code);
}
