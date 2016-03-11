
var app = angular.module('letsmeetApp', []);

app.controller('searchCtrl', function($scope, $http) {
	$http.get("url of get categories").then(function(response) {
        $scope.categories = response.data;
    });
	$scope.getSubCat = function() {
        //do get for sub categories
    };
});