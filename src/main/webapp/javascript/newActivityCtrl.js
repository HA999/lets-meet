/* global letsmeetApp */

letsmeetApp.controller('newActivityController', function($scope, $filter, 
        CountryNameService, CityNameService, CategoriesService, 
        SubCategoriesService) {
    
    
    $scope.countries = CountryNameService.query();
    
    $scope.getCities = function(country){
        //Get cities by country
        $scope.cities = CityNameService.query({country: country});
    };
    
    $scope.categories = CategoriesService.query();
    
    $scope.getSubCategories = function(category) {
        $scope.subcategories = SubCategoriesService.query({category: category});
    };
//	$scope.newActivity = [];
//	
//	$scope.cities = ["tsls", "shsh", "shshsh"];
//	$scope.countries = ["sdhsh", "shsssss", "dd"];
//	$scope.categories = ["sdg", "wwr", "www"];
//	$scope.subcategories = ["sdg", "sg"];
//	
//	$scope.addActivity = function(){
//		$scope.newActivity.datetime = $filter('date')($scope.newActivity.datetime, 'yyyy-MM-dd HH:mm:ss');
//		
//	}
});