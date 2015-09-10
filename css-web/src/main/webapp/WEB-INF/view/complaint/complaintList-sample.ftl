<#import "/spring.ftl" as s />
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>angularjs select 三级联动</title>
    
     <script src="<@s.url '/assets/scripts/angularjs/angular.js' />"></script>
    
    <style type="text/css">
    label {
        padding: 5px 10px;
        border: 1px solid #fff;
    }
    .error {        
        border-color: #f00;
    }
    </style>
    
</head>
<body>

<div ng-controller='cityCtrl'>
    <label >
        省份：
        <select ng-model="selected" ng-options="s.name for s in d1" ng-change="c()" >
            <option value="">--请选择--</option>
        </select>
    </label>
    <label>
        市/区：
        <select ng-model="selected2" ng-options="sh.name for sh in d2" ng-change="c2()" >
             <option value="">--请选择--</option>
        </select>
    </label>
    <input type="submit" value="subimt" ng-click="submit()" />
</div>


<script type="text/javascript">
var app = angular.module('app', []);
app.controller('cityCtrl', ['$scope', function ($scope) {
    $scope.error = {};
    $scope.d1 = [
    	{"id":0,
    	 "name":"北京"  	 
    	},
    	{
    	 "id":1,
    	 "name":"上海"	
    	}
    ];
    $scope.c = function () {
        $scope.selected2 = "";
		var city = $scope.selected.id;
		if(city==0){
			$scope.d2 = [
			    	{"id":0,
			    	 "name":"被1"  	 
			    	},
			    	{
			    	 "id":1,
			    	 "name":"被2"	
			    	}
		    ];
		}else{
			$scope.d2 = [
			    	{"id":0,
			    	 "name":"是1"  	 
			    	},
			    	{
			    	 "id":1,
			    	 "name":"是2"	
			    	}
		    ];
		}      
    };
    
    $scope.c2 = function () {       
        $scope.error.city = false;
        $scope.error.area = false;
        $scope.selected3 = "";
    };
    
    $scope.c3 = function () {
        $scope.error.area = false;
    };
    
    $scope.submit = function () {
        $scope.error.province = $scope.selected ? false : true;
        $scope.error.city = $scope.selected2 ? false : true;
        $scope.error.area = $scope.selected3 ? false : true;
    };
    
    
}]);



angular.bootstrap(document, ['app']);



</script>


    
</body>
</html>